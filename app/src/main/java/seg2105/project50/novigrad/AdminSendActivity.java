package seg2105.project50.novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class AdminSendActivity extends AppCompatActivity {

    private EditText notif;
    private RadioButton notif_to_all;
    private RadioButton notif_to_emp;

    private Button send;

    private DatabaseReference database;
    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_send);

        database = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        send = (Button)findViewById(R.id.sendnotification);


        notif = (EditText)findViewById(R.id.notifwrite);
        notif_to_all = (RadioButton)findViewById(R.id.sendtoall);
        notif_to_emp = (RadioButton)findViewById(R.id.notiftoemp);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notif_to_emp.isChecked()){
                    sendToEmp();
                    finish();
                    startActivity(new Intent(getApplicationContext(),Admin.class));
                }else if(notif_to_all.isChecked()){
                    sendToall();
                    finish();
                    startActivity(new Intent(getApplicationContext(),Admin.class));
                }else{
                    Toast.makeText(getApplicationContext(),"Please select the recipients",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void sendToall(){

        Date date = new Date();

        String additional_text = notif.getText().toString().trim();

        Notification notification = new Notification(additional_text, date);
        database.child("Notifications All").child(date.toString()).setValue(notification);

        finish();
        startActivity(new Intent(getApplicationContext(),Private_approve_requests.class));

    }
    private void sendToEmp(){

        Date date = new Date();

        String additional_text = notif.getText().toString().trim();

        Notification notification = new Notification(additional_text, date);
        database.child("Notifications Employee").child(date.toString()).setValue(notification);

        finish();
        startActivity(new Intent(getApplicationContext(),Private_approve_requests.class));

    }

    public void back(View view){
        finish();
        startActivity(new Intent(getApplicationContext(),Admin.class));
    }
}