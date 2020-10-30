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
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.regex.Pattern;

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

            Button backb = (Button)findViewById(R.id.backbtn);
            backb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            });
            
            
            if(fb.getCurrentUser()!=null){
                startActivity(new Intent(getApplicationContext(),HomePage.class));
                finish();
            }
            btnsignup2.setOnClickListener(new View.OnClickListener(){
                @SuppressLint("WrongViewCast")
                public void onClick(View view){
                    int radioId= radioGroup.getCheckedRadioButtonId();
                    radioButton= findViewById(radioId);
                    final String string_email;
                    String string_password;
                    final String string_name;

                    try {
                        string_email = email.getText().toString().trim();
                        string_password = password.getText().toString().trim();
                        string_name = name.getText().toString().trim();




                        final Person mcitizen = new Person(string_name, radioButton.getText().toString(), string_email, string_password);

                        if (!confirmInput()) {
                            return;
                        }
                        fb.createUserWithEmailAndPassword(string_email, string_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String email_no_signs="";
                                    for(int i =0;i<string_email.length();i++){
                                        if(string_email.charAt(i)!='@'&&string_email.charAt(i)!='.'){
                                            email_no_signs+=string_email.charAt(i);
                                        }
                                    }
                                    //mDatabase.getReference("Citizens").child(string_name).setValue(mcitizen); // added just now
                                    //mDatabase.getReference("Citiznes").push().child(email_no_signs);
                                   // mDatabase.getReference("Citizens").child(fb.getCurrentUser().getUid().replaceAll(fb.getCurrentUser().getUid(),email_no_signs)); //added
                                    mDatabase.getReference("Citizens").child(email_no_signs).setValue(mcitizen);
                                    //fb.getCurrentUser().getUid().replaceAll(fb.getCurrentUser().getUid().toString(),email_no_signs);
                                         // Toast.makeText(SignUp.this, mDatabase.getReference("Citizens").child(fb.getCurrentUser().getUid()).toString(), Toast.LENGTH_SHORT).show();
                                    //mDatabase.getReference("Citizens").child(fb.getCurrentUser().getUid()).setValue(mcitizen); //original
                                   // mDatabase.getReference("Citizens").child(fb.getCurrentUser().getUid().replaceAll(fb.getCurrentUser().getUid(),email_no_signs));
                                   /// mDatabase.getReference("Citiznes"). // still testin out diff things
                                    Toast.makeText(SignUp.this, radioButton.getText().toString() + " account created", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), HomePage.class));

                                } else {
                                    Toast.makeText(SignUp.this, "Error creating an account" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        // startActivity(new Intent(SignUp.this, HomePage.class));
                    }
                    catch(Exception e){
                        finish();
                        startActivity(new Intent(getApplicationContext(),SignUp.class));
                    }
                }
            });

    }
    public void checkButton(View v){
        int radioId= radioGroup.getCheckedRadioButtonId();

        radioButton= findViewById(radioId);
    }

    public boolean confirmInput(){
        if (!validEmail() | !validateName() | !validatePassword()){
            return false;
        }
        else{
            return true;
        }
    }

    private boolean validEmail(){
        String emailInput = email.getText().toString().trim();

        if(emailInput.isEmpty()){
            email.setError("Field can't be empty");
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            email.setError("Please enter a valid email address");
            return false;
        }
        else{
            email.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
        String passwordInput = password.getText().toString().trim();
        if(passwordInput.isEmpty()){
            password.setError("Field can't be empty");
            return false;
        }
        else if(passwordInput.length() < 5){
            password.setError("password should be at least 5 long");
            return false;
        }
        for(int i=0;i<passwordInput.length();i++){
            if(passwordInput.charAt(i)==' '){
                password.setError("password should not contain spaces");
                return false;
            }
        }
            password.setError(null);
            return true;
    }

    private boolean validateName(){
        String nameInput = name.getText().toString().trim();
        if(nameInput.isEmpty()){
            name.setError("Field can't be empty");
            return false;
        }
        else{
            name.setError(null);
            return true;
        }
    }

}
