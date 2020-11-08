package seg2105.project50.novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Employee_welcome_page extends AppCompatActivity {

    FirebaseDatabase mDatabase;
    private Button donebtn;
    private FirebaseAuth auth;

    private TextView dispBname;
    private FirebaseUser user;
    private String branchName; // we will create a unique branch name for every employee
    private String first_email;
    private EditText address;
    private EditText number;
    private  String string_address; // to be used to collect the users input
    private  String string_number; // to be used to collect the users input

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_welcome_page);
        mDatabase = FirebaseDatabase.getInstance();
        donebtn = findViewById(R.id.done);
        dispBname = findViewById(R.id.dispBName);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        address = findViewById(R.id.branchAddress);
        number = findViewById(R.id.branchNumber);
        first_email = user.getEmail();
        branchName="";
        for(int i=0;i<first_email.length();i++){  // their branch name is their email address without the @ and . in it
            if(first_email.charAt(i)!='@'&&first_email.charAt(i)!='.'){
                branchName+=first_email.charAt(i);
            }
        }


        dispBname.setText("Your Branch name is: "+branchName);

        // Now i will want to send given branch information into the database
        donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    string_address = address.toString().trim();
                    string_address = number.toString().trim();

                    if (!confirmInput()) {
                        return;
                    }

                    BranchInfo theInfo = new BranchInfo(string_address,string_number);
                    mDatabase.getReference("Branch").child(branchName).child("Branch Info").setValue(theInfo); // branchName is just their email converted in a unique way
                    startActivity(new Intent(getApplicationContext(),Employee_homePage.class)); // we will need to take to a unique class later to add/ delete services to the profile
                }
                catch (Exception e){
                    finish();
                    startActivity(new Intent(getApplicationContext(),Employee_welcome_page.class));
                }


            }
        });


    }

    public void logOut(View v){ // they shouldn't have the option to log out yet without completing the profile information
        auth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    private boolean validNumber(){
        string_number = number.getText().toString().trim();
        if(string_number.isEmpty()){
            number.setError("number cant be Empty");
            return false;
        }
      //  Toast.makeText(Employee_welcome_page.this, string_number, Toast.LENGTH_SHORT).show();

        for(int i=0; i<string_number.length(); i++){
           if(Character.isDigit(string_number.charAt(i))==false){
               number.setError("number should contain only digits and no spaces");
               return false;
           }
        }
       return true;  // it passes the 2 tests given so far.
    }

    private boolean validAddress(){
        string_address = address.getText().toString().trim();
        if(string_address.isEmpty()){
            address.setError("The address can't be Empty");
            return false;
        }
        return true;  // we don't care what the input is, as long as it's not empty, it's valid enough.
    }

    private boolean confirmInput(){
        if(!validAddress() | !validNumber()){
            return false; // at least one of them is not valid
        }
        return true;
    }

}