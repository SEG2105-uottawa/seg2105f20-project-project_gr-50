package seg2105.project50.novigrad;

import android.annotation.SuppressLint;
import android.content.Context;
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
import androidx.core.util.PatternsCompat;

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

    private String nameErrorText;
    private String pwErrorText;
    private String emailErrorText;

/*
    public SignUp(Context context, String emailInput, String nameInput, String passwordInput){
        email = new EditText(context);
        password = new EditText(context);
        name = new EditText(context);
        this.email.setText(emailInput);
        this.password.setText(passwordInput);
        this.name.setText(nameInput);
    }

 */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        radioGroup = findViewById(R.id.RadioGroup);


        btnsignup2 = (Button) findViewById(R.id.btnsignup2);
        email = findViewById(R.id.EmailSlot);
        password = findViewById(R.id.PasswordSlot);
        name = findViewById(R.id.NameSlot);
        fb = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        Button backb = (Button) findViewById(R.id.backbtn);
        backb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


        if (fb.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), HomePage.class));
            finish();
        }

        btnsignup2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongViewCast")
            public void onClick(View view) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
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
                                String email_no_signs = "";
                                for (int i = 0; i < string_email.length(); i++) {
                                    if (string_email.charAt(i) != '@' && string_email.charAt(i) != '.') {
                                        email_no_signs += string_email.charAt(i);
                                    }
                                }

                                //we should first be checking if the role is customer
                                // if it is then do this setting.
                                // otherwise we want to save all employee information
                                //seperately in Branch child, not Citizens.
                                if (mcitizen.getRole().equals("customer")) {
                                    mDatabase.getReference("Citizens").child(email_no_signs).setValue(mcitizen);
                                } else { // we store the emp data in branch with empty address and number for now. the next activity will update these values.
                                    //Toast.makeText(SignUp.this, "passing name 1st:" + mcitizen.getName(), Toast.LENGTH_SHORT).show();

                                    BranchInfo allEmpInfo = new BranchInfo("", "", mcitizen.getName(), mcitizen.getEmail(), mcitizen.getPassword(), mcitizen.getRole());
                                    mDatabase.getReference("Branch").child("Branch " + email_no_signs).child("Branch Info").setValue(allEmpInfo);
                                }

                                Toast.makeText(SignUp.this, radioButton.getText().toString() + " account created", Toast.LENGTH_SHORT).show();
                                if (mcitizen.getRole().equals("customer")) {
                                    startActivity(new Intent(getApplicationContext(), HomePage.class)); // this one for customers
                                } else {
                                    startActivity(new Intent(getApplicationContext(), Employee_welcome_page.class)); // this new one for employees
                                }

                            } else {
                                Toast.makeText(SignUp.this, "Error creating an account" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    // startActivity(new Intent(SignUp.this, HomePage.class));
                } catch (Exception e) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), SignUp.class));
                }
            }
        });

    }

    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);

    }

    public boolean confirmInput() {
        if (!validEmail(getEmail()) | !validateName(getName()) | !validatePassword(getPw())) {
            name.setError(nameErrorText);
            password.setError(pwErrorText);
            email.setError(emailErrorText);
            return false;
        } else {
            return true;
        }
    }

    public boolean validEmail(String emailInput) {
        //String emailInput = email.getText().toString().trim();

        if (emailInput.isEmpty()) {
            emailErrorText = ("Field can't be empty");
            return false;
        } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            emailErrorText = ("Please enter a valid email address");
            return false;
        } else {
            emailErrorText = (null);
            return true;
        }
    }

    public boolean validatePassword(String passwordInput) {
        //String passwordInput = password.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            pwErrorText = ("Field can't be empty");
            return false;
        } else if (passwordInput.length() < 5) {
            pwErrorText = ("password should be at least 5 long");
            return false;
        }
        for (int i = 0; i < passwordInput.length(); i++) {
            if (passwordInput.charAt(i) == ' ') {
                pwErrorText = ("password should not contain spaces");
                return false;
            }
        }
        pwErrorText = (null);
        return true;
    }

    public boolean validateName(String nameInput) {
        // nameInput = name.getText().toString().trim();
        if (nameInput.isEmpty()) {
            nameErrorText = "Field can't be empty";
            return false;
        } else {
            nameErrorText = null;
            return true;
        }
    }
    public String getEmail(){
        return email.getText().toString().trim();
    }
    public String getPw(){
        return password.getText().toString().trim();
    }
    public String getName(){
        return name.getText().toString().trim();
    }

}

