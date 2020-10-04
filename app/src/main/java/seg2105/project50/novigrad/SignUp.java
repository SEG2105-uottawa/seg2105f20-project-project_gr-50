package seg2105.project50.novigrad;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private Button btnsignup2;
    private EditText email, password, name;
    FirebaseAuth fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

            btnsignup2 = (Button)findViewById(R.id.btnsignup2);
            email = findViewById(R.id.EmailSlot);
            password = findViewById(R.id.PasswordSlot);
            name = findViewById(R.id.NameSlot);
            fb = FirebaseAuth.getInstance();

            if(fb.getCurrentUser()!=null){
                startActivity(new Intent(getApplicationContext(),HomePage.class));
                finish();
            }
            btnsignup2.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){

                    String string_email = email.getText().toString().trim();
                    String string_password = password.getText().toString().trim();
                    String string_name = name.getText().toString().trim();

                    if(TextUtils.isEmpty(string_email)){
                        email.setError("Please enter a valid email");
                        return;
                    }
                    if(TextUtils.isEmpty(string_password)){
                        password.setError("Please enter a valid password");
                        return;
                    }
                    if(string_password.length()<5){
                        password.setError("password should be at least 5 long");
                        return;
                    }
                    fb.createUserWithEmailAndPassword(string_email , string_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignUp.this, "account created", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),HomePage.class));
                            }
                            else{
                                Toast.makeText(SignUp.this, "Error"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                   // startActivity(new Intent(SignUp.this, HomePage.class));
                }
            });
    }
}