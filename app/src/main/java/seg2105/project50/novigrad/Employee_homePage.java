package seg2105.project50.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Employee_homePage extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference database;
    private FirebaseUser user;

    private TextView textV;
    private TextView noNotif;
    private ListView list;
    private Notification notification;
    private Button clear;

    private ArrayList<Notification> notif_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home_page);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        user = auth.getCurrentUser();

        list = (ListView) findViewById(R.id.notif_emp);
        noNotif = (TextView) findViewById(R.id.notif_state_emp);
        clear = (Button)findViewById(R.id.clear_notif_emp);

        loadNotification();




    }

    private void loadNotification(){
        notif_list.clear();

        database.child("Public Notifications Employee").child(bare_email())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot ServiceSnapshot) {
                        if (!(ServiceSnapshot.hasChildren())) {
                            list.setVisibility(View.GONE);
                            clear.setVisibility(View.GONE);
                            noNotif.setVisibility(View.VISIBLE);
                        } else {
                            noNotif.setVisibility(View.GONE);
                            list.setVisibility(View.VISIBLE);
                            clear.setVisibility(View.VISIBLE);

                            for (DataSnapshot dataSnapshot : ServiceSnapshot.getChildren()) {
                                notification = dataSnapshot.getValue(Notification.class);
                                if (notification != null) {
                                    notif_list.add(notification);
                                } else {
                                    startActivity(new Intent(getApplicationContext(), HomePage.class)); //added
                                    finish();
                                    Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT);
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled (@NonNull DatabaseError databaseError){
                    }

                });


        NotificationDisplay listAdapter = new NotificationDisplay(this,R.layout.notification_display, notif_list);
        list.setAdapter(listAdapter);
    }

    public void myservice(View v){
        finish();
        startActivity(new Intent(getApplicationContext(),Private_my_services.class));
    }

    public void myhours(View v){
        finish();
        startActivity(new Intent(getApplicationContext(),Private_my_hours.class));
    }

    public void approverequest(View v){
        finish();
        startActivity(new Intent(getApplicationContext(), Private_approve_requests.class));
    }

    public void logout(View v){
        auth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    public void clear(View view){
        String bare_email = bare_email();
        database.child("Public Notifications Employee").child(bare_email).removeValue();
        loadNotification();
    }

    private String bare_email(){
        String first_email= user.getEmail();
        String bare_email= "";
        for(int i=0;i<first_email.length();i++){
            if(first_email.charAt(i)!='@'&&first_email.charAt(i)!='.'){
                bare_email+=first_email.charAt(i);
            }
        }

        return bare_email;
    }
}