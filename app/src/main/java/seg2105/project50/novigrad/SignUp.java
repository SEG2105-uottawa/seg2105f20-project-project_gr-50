package seg2105.project50.novigrad;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private Button btnsignup2;
    private EditText email, password, name;
    FirebaseAuth fb;
    FirebaseDatabase mDatabase;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

            radioGroup = findViewById(R.id.RadioGroup);


            btnsignup2 = (Button)findViewById(R.id.btnsignup2);
            email = findViewById(R.id.EmailSlot);
            password = findViewById(R.id.PasswordSlot);
            name = findViewById(R.id.NameSlot);
            fb = FirebaseAuth.getInstance();
            mDatabase = FirebaseDatabase.getInstance();
            
            
            if(fb.getCurrentUser()!=null){
                startActivity(new Intent(getApplicationContext(),HomePage.class));
                finish();
            }
            btnsignup2.setOnClickListener(new View.OnClickListener(){
                @SuppressLint("WrongViewCast")
                public void onClick(View view){
                    int radioId= radioGroup.getCheckedRadioButtonId();
                    radioButton= findViewById(radioId);
                    String string_email;
                    String string_password;
                    String string_name;

                    try {
                        string_email = email.getText().toString().trim();
                         string_password = password.getText().toString().trim();
                         string_name = name.getText().toString().trim();
                    }
                    catch(Exception e){
                        finish();
                        return;
                    }
                    final Person mcitizen = new Person (string_name,radioButton.getText().toString(),string_email,string_password);

                    if(string_email==null){return;}
                    if(string_password==null){return;}
                    if(string_name==null){return;}
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
                                mDatabase.getReference("Citizens").child(fb.getCurrentUser().getUid()).setValue(mcitizen);
                                Toast.makeText(SignUp.this, radioButton.getText().toString()+" account created", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),HomePage.class));
                            }
                            else{
                                Toast.makeText(SignUp.this, "Error creating an account"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                   // startActivity(new Intent(SignUp.this, HomePage.class));
                }
            });

    }
    public void checkButton(View v){
        int radioId= radioGroup.getCheckedRadioButtonId();

        radioButton= findViewById(radioId);
    }
}
