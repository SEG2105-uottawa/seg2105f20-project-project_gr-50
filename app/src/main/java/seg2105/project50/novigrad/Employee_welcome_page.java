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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Employee_welcome_page extends AppCompatActivity {

    DatabaseReference mDatabase;
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
 //-----------------------------//
   // private BranchInfo oldBranchStuff;
    private String name;
    private String email;
    private String password;
    private String role;

//-----------------------------//
    // private Instances for error messages setting;
    private String numberErrorText;
    private String addressErrorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_welcome_page);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        donebtn = findViewById(R.id.done);
        dispBname = findViewById(R.id.dispBName);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        address = findViewById(R.id.branchAddress);
        number = findViewById(R.id.branchNumber);
        first_email = user.getEmail();
        branchName="";
        for(int i=0;i<first_email.length();i++){  // their branch name is "Branch "+ their email address without the @ and . in it
            if(first_email.charAt(i)!='@'&&first_email.charAt(i)!='.'){
                branchName+=first_email.charAt(i);
            }
        }


        dispBname.setText("Your Branch name is:Branch "+branchName);

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
                    // I am trying to retrieve the Branch info already stored in database, then update it with the branch information. :D
 //  --------------------------------------------------------------------------------------
                   // mDatabase.child("Branch").child("Branch jinkagmailcom").child("Branch Info").addListenerForSingleValueEvent(new ValueEventListener(){
                     //   @Override
                       // public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         //   BranchInfo citizen = dataSnapshot.getValue(BranchInfo.class);
                           // Toast.makeText(Employee_welcome_page.this,citizen.getNumber(), Toast.LENGTH_SHORT).show();
                        //}

                        //@Override
                        //public void onCancelled(@NonNull DatabaseError databaseError) {

                        //}
                    //});



                    mDatabase.child("Branch").child("Branch "+branchName).child("Branch Info")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                   BranchInfo oldBranchStuff = dataSnapshot.getValue(BranchInfo.class);
                                  //  Toast.makeText(Employee_welcome_page.this,"name 2:"+oldBranchStuff.getTheName(), Toast.LENGTH_SHORT).show();
                                    if (oldBranchStuff != null) { // to prevent app crash but i don't think this would ever be Null..but you never know.

                                         name = oldBranchStuff.getTheName();
                                         email = oldBranchStuff.getEmail();
                                         password = oldBranchStuff.getPassword();
                                         role = oldBranchStuff.getRole();

                                         //the hours part has nothing to do with this data change, but i just coded it here,
                                        //because it is part of the set up process for employee accounts
                                        // I give all new employees hours of CLOSED...they can change this later on when they sign in
                                        //the changes will be reflected up in the database as well.
                                         Hours defaultHours = new Hours("CLOSED","CLOSED","CLOSED","CLOSED","CLOSED","CLOSED","CLOSED");
                                        BranchInfo newInfo = new BranchInfo(string_address,string_number,name,email,password,role); // :D happy!..updated!!
                                        mDatabase.child("Branch").child("Branch "+branchName).child("Branch Info").setValue(newInfo); // branchname in database is just "Branch "+their email converted
                                        mDatabase.child("Branch").child("Branch "+branchName).child("Hours").setValue(defaultHours);
                                        finish();
                                        startActivity(new Intent(getApplicationContext(),Employee_homePage.class));
                                        }
                                    else {

                                            // hehe :D no instruction found here for you compiler.
                                        }
                                    }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

  // --------------------------------------------------------------------------------------------------

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

    public boolean validNumber(String string_number){
        //string_number = number.getText().toString().trim();
        if(string_number.isEmpty()){
            numberErrorText = "number cant be Empty";
            return false;
        }
      //  Toast.makeText(Employee_welcome_page.this, string_number, Toast.LENGTH_SHORT).show();

        for(int i=0; i<string_number.length(); i++){
           if(Character.isDigit(string_number.charAt(i))==false){
               numberErrorText = "number should contain only digits and no spaces";
               return false;
           }
        }
        numberErrorText = null;
       return true;  // it passes the 2 tests given so far.
    }

    public boolean validAddress(String string_address){
        //string_address = address.getText().toString().trim();
        if(string_address.isEmpty()){
            addressErrorText = "The address can't be Empty";
            return false;
        }
        addressErrorText = null;
        return true;  // we don't care what the input is, as long as it's not empty, it's valid enough.
    }

    private boolean confirmInput(){
        if(!validAddress(getAddress()) | !validNumber(getNumber())){
            number.setError(numberErrorText);
            address.setError(addressErrorText);
            return false; // at least one of them is not valid
        }
        return true;
    }

    public String getAddress(){
        return address.getText().toString().trim();
    }

    public String getNumber(){
        return number.getText().toString().trim();
    }

}