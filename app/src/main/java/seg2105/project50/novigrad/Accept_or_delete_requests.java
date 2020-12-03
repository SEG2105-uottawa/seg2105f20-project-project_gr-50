package seg2105.project50.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.Date;

public class Accept_or_delete_requests extends AppCompatActivity {

    EditText information;
    private DatabaseReference database;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String customer_id;
    private String serv_type;
    private ServicesSettings service;
    private ServicesInfo servicesInfo;

    TextView fn ;
    TextView ln ;
    TextView db ;
    TextView ad ;
    TextView pr ;
    TextView ps ;
    TextView id ;
    TextView dv ;
    TextView serviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_or_delete_requests);

        database = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        information = (EditText) findViewById(R.id.additional_info);

        Bundle extras = getIntent().getExtras();
        serv_type = extras.getString("service_the_type");
        customer_id = extras.getString("customer_who_sent_it");




        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();



        fn = (TextView)findViewById(R.id.ser3_fn);
        ln = (TextView)findViewById(R.id.ser3_ln);
        db = (TextView)findViewById(R.id.ser3_db);
        ad = (TextView)findViewById(R.id.ser3_ad);
        pr = (TextView)findViewById(R.id.ser3_pr);
        ps = (TextView)findViewById(R.id.ser3_ps);
        id = (TextView)findViewById(R.id.ser3_id);
        dv = (TextView)findViewById(R.id.ser3_dv);




        database.child("Services")
                .child(serv_type)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                        service = dataSnapshot.getValue(ServicesSettings.class);
                        if(service!= null) {}
                        else{
                            startActivity(new Intent(getApplicationContext(), HomePage.class)); //added
                            finish();
                            //TODO make a toast , "something went wrong"
                        }

                        if (!service.isFirstname()) {
                            fn.setVisibility(View.GONE);
                        }
                        if (!service.isLastname()) {
                            ln.setVisibility(View.GONE);
                        }
                        if (!service.isDateofbirth()) {
                            db.setVisibility(View.GONE);
                        }
                        if (!service.isAdress()) {
                            ad.setVisibility(View.GONE);
                        }
                        if (!service.isProofofresidence()) {
                            pr.setVisibility(View.GONE);
                        }
                        if (!service.isProofofstatus()) {
                            ps.setVisibility(View.GONE);
                        }
                        if (!service.isIdnumber()) {
                            id.setVisibility(View.GONE);
                        }
                        if (!service.isLicensetype()) {
                            dv.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });




        database.child("Branch")
                .child(generateBranchName(user.getEmail()))
                .child("Request")
                .child(customer_id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                        servicesInfo = dataSnapshot.child(serv_type).getValue(ServicesInfo.class);
                        Log.e("name",servicesInfo.getFirstname());
                        if(servicesInfo!= null) {}
                        else{
                            startActivity(new Intent(getApplicationContext(), HomePage.class)); //added
                            finish();
                            //TODO make a toast , "something went wrong"
                        }

                        serviceName = (TextView)findViewById(R.id.ser3_name);
                        serviceName.setText(serv_type);


                        if (service.isFirstname()) {
                            fn.setText("First Name: "+servicesInfo.getFirstname());
                        }
                        if (service.isLastname()) {
                            ln.setText("Last Name: "+servicesInfo.getLastname());
                        }
                        if (service.isDateofbirth()) {
                            db.setText("Date Of Birth: "+servicesInfo.getDateofbirth());
                        }
                        if (service.isAdress()) {
                            ad.setText("Address: "+servicesInfo.getAdress());
                        }
                        if (service.isProofofresidence()) {
                            pr.setText("Proof Of Residence: "+servicesInfo.getProofofresidence());
                        }
                        if (service.isProofofstatus()) {
                            ps.setText("Proof Of Status: "+servicesInfo.getProofofstatus());
                        }
                        if (service.isIdnumber()) {
                            id.setText("Id Number: "+servicesInfo.getIdnumber());
                        }
                        if (service.isLicensetype()) {
                            dv.setText("License Type: "+servicesInfo.getLicensetype());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });



    }

    public void acceptClicked(View view){

        Date date = new Date();

        //We just accepted the request by deleting it from the list of request the employee has left...No notifications
        // sent to the customer yet.
        database.child("Branch").child(generateBranchName(user.getEmail())).child("Request").child(customer_id).child(serv_type).removeValue();
        String additional_text = information.getText().toString().trim();

        Notification notification = new Notification(("Request accepted: "+serv_type+"\n"+additional_text), date);
        String notif = serv_type + date.toString();
        database.child("Notifications").child(customer_id).child(notif).setValue(notification);

        finish();
        startActivity(new Intent(getApplicationContext(),Private_approve_requests.class));

    }

    public void declineClicked(View view){
        //same code as the other. it will only differ if we later implement a status for the customer of their request.
        Date date = new Date();

        String additional_text = information.getText().toString().trim();

        if(!(additional_text.equals(""))) {
            Notification notification = new Notification(("Request refused: "+serv_type+"\n"+ additional_text), date);

            database.child("Branch").child(generateBranchName(user.getEmail())).child("Request").child(customer_id).child(serv_type).removeValue();
            String notif = serv_type + date.toString();
            database.child("Notifications").child(customer_id).child(notif).setValue(notification);

            finish();
            startActivity(new Intent(getApplicationContext(), Private_approve_requests.class));
        }else{
            Toast.makeText(getApplicationContext(),"You need to add additional information if request need to be declined", Toast.LENGTH_LONG);
        }
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