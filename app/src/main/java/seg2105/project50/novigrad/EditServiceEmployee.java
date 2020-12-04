package seg2105.project50.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditServiceEmployee extends AppCompatActivity {


    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private ServicesSettings service;
    private ServicesSettings temp;
    private String ser_num;

    TextView fn ;
    TextView ln ;
    TextView db ;
    TextView ad ;
    TextView pr ;
    TextView ps ;
    TextView id ;
    TextView dv ;
    TextView serviceName;
    TextView servicePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service_employee);

        Bundle extras = getIntent().getExtras();
        ser_num = extras.getString("ser_num").trim();


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();



        database.child("Services")
                .child(ser_num)
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

                        fn = (TextView)findViewById(R.id.ser2_fn);
                        ln = (TextView)findViewById(R.id.ser2_ln);
                        db = (TextView)findViewById(R.id.ser2_db);
                        ad = (TextView)findViewById(R.id.ser2_ad);
                        pr = (TextView)findViewById(R.id.ser2_pr);
                        ps = (TextView)findViewById(R.id.ser2_ps);
                        id = (TextView)findViewById(R.id.ser2_id);
                        dv = (TextView)findViewById(R.id.ser2_dv);

                        servicePrice = (TextView)findViewById(R.id.emp_view_price);
                        servicePrice.setText("$ "+service.getPrice());

                        serviceName = (TextView)findViewById(R.id.textView3);
                        serviceName.setText(service.getName());


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



    }

    public void activate(View view){
        database.child("Services")
                .child(ser_num)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                        service = dataSnapshot.getValue(ServicesSettings.class);
                        Toast.makeText(getApplicationContext(), "Service's ON" ,Toast.LENGTH_LONG);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

        service.setEmployeeEnable(true);
        database.child("Branch").child(generateBranchName(user.getEmail())).child("Services").child(service.getName()).setValue(service);
    }

    public void deactivate(View view){
        database.child("Services")
                .child(ser_num)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                        service = dataSnapshot.getValue(ServicesSettings.class);
                        service.setEmployeeEnable(false);
                        Toast.makeText(getApplicationContext(), "Service's OFF" ,Toast.LENGTH_LONG);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });



        database.child("Branch").child(generateBranchName(user.getEmail()))
                .child("Services")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            temp = dataSnapshot1.getValue(ServicesSettings.class);
                            if(temp.getName().equals(service.getName())){
                                database.child("Branch").child(generateBranchName(user.getEmail()))
                                        .child("Services").child(service.getName()).removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void takeBack(View view){
        finish();
        startActivity(new Intent(getApplicationContext(), Private_my_services.class));
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