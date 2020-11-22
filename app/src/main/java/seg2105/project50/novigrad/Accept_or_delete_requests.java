package seg2105.project50.novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Accept_or_delete_requests extends AppCompatActivity {

    TextView showInformation;
    private DatabaseReference database;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String customer_id;
    private String serv_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_or_delete_requests);

        database = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        showInformation = findViewById(R.id.display_serviceType_and_customerEmail);

        Bundle extras = getIntent().getExtras();
        serv_type = extras.getString("service_the_type");
        customer_id = extras.getString("customer_who_sent_it");

        showInformation.setText(" Service of "+serv_type+" From "+customer_id);
    }

    public void acceptClicked(View view){

        //We just accepted the request by deleting it from the list of request the employee has left...No notifications
        // sent to the customer yet.
        database.child("Branch").child(generateBranchName(user.getEmail())).child("Request").child(customer_id).child(serv_type).removeValue();

        finish();
        startActivity(new Intent(getApplicationContext(),Private_approve_requests.class));

    }

    public void declineClicked(View view){
        //same code as the other. it will only differ if we later implement a status for the customer of their request.

        database.child("Branch").child(generateBranchName(user.getEmail())).child("Request").child(customer_id).child(serv_type).removeValue();

        finish();
        startActivity(new Intent(getApplicationContext(),Private_approve_requests.class));
    }


    public String generateBranchName(String string_email){
        String email_no_signs="";
        String data = "Branch ";
        for(int i =0;i<string_email.length();i++){
            if(string_email.charAt(i)!='@'&&string_email.charAt(i)!='.'){
                email_no_signs+=string_email.charAt(i);
            }
        }
        return (data+email_no_signs);
    }
}