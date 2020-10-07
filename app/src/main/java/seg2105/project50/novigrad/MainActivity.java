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

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private String email, password;
    private EditText emailTextView, passwordTextView;
    //private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailTextView = findViewById(R.id.editTextTextEmailAddress);
        passwordTextView = findViewById(R.id.editTextTextPassword);
        auth= FirebaseAuth.getInstance();

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

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    finish();
                                    startActivity(new Intent(getApplicationContext(),
                                            HomePage.class));
                                   // finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Login failed...", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

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