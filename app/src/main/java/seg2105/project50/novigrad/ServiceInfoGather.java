package seg2105.project50.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class ServiceInfoGather extends AppCompatActivity {


    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private ServicesSettings service;

    EditText fn ;
    EditText ln ;
    EditText db ;
    EditText ad ;
    EditText pr ;
    EditText ps ;
    EditText id ;
    EditText dv ;
    TextView price;

    TextView serviceName;
    public ServiceInfoGather(){} // In case any part of the code used the default constructor as empty constructor
    public ServiceInfoGather(Context context, ServicesSettings testService){
        service = testService;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service1);

        Bundle extras = getIntent().getExtras();
        final String ser_num = extras.getString("ser_num").trim();


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
                            Toast.makeText(getApplicationContext(),"Service's no longer offered", Toast.LENGTH_LONG);
                        }

                        fn = (EditText)findViewById(R.id.ser1_fn);
                        ln = (EditText)findViewById(R.id.ser1_ln);
                        db = (EditText)findViewById(R.id.ser1_db);
                        ad = (EditText)findViewById(R.id.ser1_ad);
                        pr = (EditText)findViewById(R.id.ser1_pr);
                        ps = (EditText)findViewById(R.id.ser1_ps);
                        id = (EditText)findViewById(R.id.ser1_id);
                        dv = (EditText)findViewById(R.id.ser1_dv);
                        price = (TextView)findViewById(R.id.price);

                        serviceName = (TextView)findViewById(R.id.textView3);
                        serviceName.setText(service.getName());
                        price.setText("$ "+service.getPrice());


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



        Button done = (Button)findViewById(R.id.active);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String text_fn = fn.getText().toString().trim();
                String text_ln = ln.getText().toString().trim();
                String text_db = db.getText().toString().trim();
                String text_ad = ad.getText().toString().trim();
                String text_pr = pr.getText().toString().trim();
                String text_ps = ps.getText().toString().trim();
                String text_id = id.getText().toString().trim();
                String text_dv = dv.getText().toString().trim();

                if (!checkFields(text_fn,text_ln,text_db,text_ad,text_pr,text_ps,text_id,text_dv)){
                    Toast.makeText(getApplicationContext(), "All fields should be filled",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                Bundle extras = getIntent().getExtras();
                String ser_branch = extras.getString("ser_branch").trim();

                //String dbPath = "service-"+name;

                String[] info = {text_fn, text_ln, text_db, text_ad, text_pr, text_ps
                                ,text_id,text_dv};
                ServicesInfo infoServ = new ServicesInfo(info, ser_num);

                database.child("Branch").child(ser_branch).child("Request").child(generateCustomerName(user.getEmail())).child(ser_num).setValue(infoServ);
                Log.d("tag",ser_branch);
                Toast.makeText(getApplicationContext(), "Request sent, you will get an answer within two weeks",
                        Toast.LENGTH_LONG).show();

            }
        });




    }

    public String generateCustomerName(String string_email){
        String email_no_signs="";
        for(int i =0;i<string_email.length();i++){
            if(string_email.charAt(i)!='@'&&string_email.charAt(i)!='.'){
                email_no_signs+=string_email.charAt(i);
            }
        }
        return email_no_signs;
    }

    public void takeHome(View view){
        finish();
        startActivity(new Intent(getApplicationContext(), HomePage.class));
    }

    public boolean checkFields(String text_fn,String text_ln,String text_db,String text_ad,String text_pr,
                               String text_ps,String text_id,String text_dv){
        if (text_fn.isEmpty() && service.isFirstname()){
            return false;
        }
        if(text_ln.isEmpty() && service.isLastname()){
            return false;
        }
        if(text_db.isEmpty() && service.isDateofbirth()){
            return false;
        }
        if(text_ad.isEmpty() && service.isAdress()){
            return false;
        }
        if(text_pr.isEmpty() && service.isProofofresidence()){
            return false;
        }
        if(text_ps.isEmpty() && service.isProofofstatus()){
            return false;
        }
        if(text_id.isEmpty() && service.isIdnumber()){
            return false;
        }
        if(text_dv.isEmpty() && service.isLicensetype()){
            return false;
        }
        return true;
    }
}