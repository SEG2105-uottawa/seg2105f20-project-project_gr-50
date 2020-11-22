package seg2105.project50.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class View_active_personal_service extends AppCompatActivity {

    ListView listviewMyServices;
    List<String> list;
    private String  bare_email = "";
    private FirebaseAuth auth;
    private FirebaseUser user;
    private ServicesSettings service2;
    DatabaseReference databaseEmployees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_active_personal_service);

        listviewMyServices = findViewById(R.id.view_my_active_services);
        list = new ArrayList<>();

        databaseEmployees = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        String first_email = user.getEmail();

        for (int i = 0; i < first_email.length(); i++) {
            if (first_email.charAt(i) != '@' && first_email.charAt(i) != '.') {
                bare_email += first_email.charAt(i);
            }
        }
    }

    protected void onStart() {
        super.onStart();
        list.clear();

        databaseEmployees.child("Branch").child("Branch "+bare_email).child("Services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                list.clear();

                if(dataSnapshot1!=null) {
                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                        service2 = dataSnapshot2.getValue(ServicesSettings.class);
                        if (service2.isActive()) {
                            list.add(service2.getName());
                            listviewMyServices.requestLayout();
                        }
                    }
                }


                if(list.size()==0){
                    list.add("None of your services are active yet. Click them to change that.");
                }
                ArrayAdapter<String> hourOfUser = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1 ,list);
                listviewMyServices.setAdapter(hourOfUser);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void take_to_go_back(View view){
        finish();
        startActivity(new Intent(getApplicationContext(),Private_my_services.class));
    }
}