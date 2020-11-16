package seg2105.project50.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteUsers extends AppCompatActivity {

    DatabaseReference databaseEmployees; //just made another variable, so i dont break the code that was already there when i added new things
    private EditText emailAddy;
    FirebaseAuth fb;
    FirebaseDatabase mDatabase;
    ListView listviewUsers; //added
    DatabaseReference databaseUsers;
    List<String> users;
    //StorageReference listRef; //added

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_users);

        databaseEmployees = FirebaseDatabase.getInstance().getReference("Branch");
        fb = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("Citizens");
        emailAddy = findViewById(R.id.UserToDelete);
       listviewUsers = findViewById(R.id.allUsers); //added

        users = new ArrayList<>(); //added



       // DatabaseReference dR ;

        Button backb = (Button)findViewById(R.id.back_from_delete);
        backb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), Admin.class));
            }
        });

        Button delete = (Button)findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string_emailAddy;
                DatabaseReference dR;
                DatabaseReference mR;
                try {

                    string_emailAddy = emailAddy.getText().toString().trim();
                    if(!(string_emailAddy.isEmpty())) { // otherwise empty value erases the whole database!
                        dR = FirebaseDatabase.getInstance().getReference("Citizens").child(string_emailAddy); //attempt to remove the info from customer section.
                        dR.removeValue();
                        mR = FirebaseDatabase.getInstance().getReference("Branch").child("Branch "+string_emailAddy);
                        mR.removeValue(); // attempt to remove the user if it exists in employee section.
                                          // If it doesn't exist there, nothing actually happens in database.

                        Toast.makeText(DeleteUsers.this, string_emailAddy+" has been deleted", Toast.LENGTH_SHORT).show();



                    }



                    else{
                        Toast.makeText(DeleteUsers.this, "Input User to delete", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    finish();
                    Toast.makeText(DeleteUsers.this,  "No account has been deleted.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),DeleteUsers.class));
                }

            }
        });

    }

    protected void onStart(){
        super.onStart();
        users.clear();

    //--------------------------------------------------------------------------
         //I have to iterate through branch and show all employees there. add it to list
        // Then iterate through citizens and show all the customers there. add it to list
        //after doing both, i show what the content of the list is.
        databaseEmployees.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    BranchInfo userID = postSnapshot.child("Branch Info").getValue(BranchInfo.class);


                    if(userID!=null) { //incase we are iterating through other children of branches, ie serviceRequest
                        users.add(userID.getEmail()+"  ("+userID.getRole()+")");

                    }
                }
               // ArrayAdapter<String> namesOfUsers = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1 ,users);
              // listviewUsers.setAdapter(namesOfUsers);
    //-----------------------------------------------------
                databaseUsers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //users.clear();

                        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            Person userID = postSnapshot.getValue(Person.class);
                            users.add(userID.getEmail()+"  ("+userID.getRole()+")");
                        }
                        ArrayAdapter<String> namesOfUsers = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1 ,users);
                        listviewUsers.setAdapter(namesOfUsers);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    //-----------------------------------------------------
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


      //-------------  ------------------------------------------
        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Person userID = postSnapshot.getValue(Person.class);
                   users.add(userID.getEmail()+"  ("+userID.getRole()+")");
                }
                //ArrayAdapter<String> namesOfUsers = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1 ,users);
               // listviewUsers.setAdapter(namesOfUsers);
    //---------------------------------------
                databaseEmployees.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //users.clear();

                        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            BranchInfo userID = postSnapshot.child("Branch Info").getValue(BranchInfo.class);


                            if(userID!=null) { //incase we are iterating through other children of branches, ie serviceRequest
                                users.add(userID.getEmail()+"  ("+userID.getRole()+")");

                            }
                        }
                         ArrayAdapter<String> namesOfUsers = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1 ,users);
                         listviewUsers.setAdapter(namesOfUsers);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    //-------------------------------------------
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}