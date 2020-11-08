package seg2105.project50.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private String email, password;
    private EditText emailTextView, passwordTextView;
    private FirebaseUser User;
    private DatabaseReference database;
    private Person citizen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailTextView = findViewById(R.id.editTextTextEmailAddress);
        passwordTextView = findViewById(R.id.editTextTextPassword);
        auth= FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference(); // added


        Button btnsignin = (Button) findViewById(R.id.btnsignin);
        btnsignin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                email = emailTextView.getText().toString();
                password = passwordTextView.getText().toString();

                if(email.equals("admin")&&password.equals("admin")){
                    finish();
                    startActivity(new Intent(getApplicationContext(),Admin.class));
                    return;
                }
                try {
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        User = FirebaseAuth.getInstance().getCurrentUser();
                                        String raw_email = User.getEmail();
                                        String string_email = "";
                                        for (int i = 0; i < raw_email.length(); i++) {
                                            if (raw_email.charAt(i) != '@' && raw_email.charAt(i) != '.') {
                                                string_email += raw_email.charAt(i);
                                            }
                                        }
                                        database.child("Citizens")
                                                .child(string_email)
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        citizen = dataSnapshot.getValue(Person.class);
                                                        if (citizen != null) {
                                                            String role = citizen.getRole();
                                                            if (role.equals("employee")) {
                                                                finish();
                                                                startActivity(new Intent(getApplicationContext(),
                                                                        Employee_homePage.class));
                                                            } else {
                                                                finish();
                                                                startActivity(new Intent(getApplicationContext(),
                                                                        HomePage.class));
                                                            }
                                                        }
                                                        else{
                                                            finish(); //added
                                                            auth.signOut(); // added
                                                            Toast.makeText(MainActivity.this, "Account disabled", Toast.LENGTH_SHORT).show(); //added
                                                            Toast.makeText(MainActivity.this, "Account disabled", Toast.LENGTH_SHORT).show();

                                                            startActivity(new Intent(MainActivity.this, MainActivity.class)); //added
                                                            finish();
                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Login failed...", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                catch(Exception e){
                    finish();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }

            }
        });
        Button btnsignup = (Button) findViewById(R.id.btnsignup1);
        btnsignup.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();
                startActivity(new Intent(getApplicationContext(),
                        SignUp.class));
                //finish();
            }
        });


    }


}