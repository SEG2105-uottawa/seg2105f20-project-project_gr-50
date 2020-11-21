package seg2105.project50.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomePage extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private Person citizen;

    private Button btnLogout;
    private TextView textV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //---------------------------------------------------------


                auth = FirebaseAuth.getInstance();
                user = auth.getCurrentUser();
                database = FirebaseDatabase.getInstance().getReference();

                textV = (TextView) findViewById(R.id.textView);




                if (user == null){
                    finish();
                    startActivity(new Intent(HomePage.this, MainActivity.class));
                }
                else{
                   String first_email= user.getEmail();
                   String bare_email= "";
                   for(int i=0;i<first_email.length();i++){
                       if(first_email.charAt(i)!='@'&&first_email.charAt(i)!='.'){
                           bare_email+=first_email.charAt(i);
                       }
                   }
                   // Toast.makeText(HomePage.this, user.getEmail(), Toast.LENGTH_SHORT).show();
                    database.child("Citizens")
                            .child(bare_email)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange( DataSnapshot dataSnapshot) {

                                    citizen = dataSnapshot.getValue(Person.class);
                                    if(citizen!= null) {
                                        System.out.println(citizen);
                                        String name = citizen.getName();
                                        String role = citizen.getRole();


                                        textV.setText("Welcome " + name + ", " + role);
                                    }
                                    else{
                                        //there wont be anything to do here
                                        //we shouldnt enter here
                                        //MainActivity only sends valid customers to this page
                                        //no disabled customer is able to come to this page
                                        //Also no employee comes to this page.
                                        //All logged in employees go straight to employee_homepage. :D
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                }





        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut(view);
            }
        });



    }

    public void signOut(View v){
        auth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    public void customerBranchChoices(View view){
        finish();
        Intent intent = new Intent(getApplicationContext(), CustomerBranchChoice.class);
        startActivity(intent);
    }


}