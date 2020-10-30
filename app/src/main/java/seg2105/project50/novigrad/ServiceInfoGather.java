package seg2105.project50.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ServiceInfo;
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
    TextView serviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service1);

        Bundle extras = getIntent().getExtras();
        String ser_num = extras.getString("ser_num").trim();


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
                        Log.d("MyApp",service.getName());

                        fn = (EditText)findViewById(R.id.ser1_fn);
                        ln = (EditText)findViewById(R.id.ser1_ln);
                        db = (EditText)findViewById(R.id.ser1_db);
                        ad = (EditText)findViewById(R.id.ser1_ad);
                        pr = (EditText)findViewById(R.id.ser1_pr);
                        ps = (EditText)findViewById(R.id.ser1_ps);
                        id = (EditText)findViewById(R.id.ser1_id);
                        dv = (EditText)findViewById(R.id.ser1_dv);

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



        Button done = (Button)findViewById(R.id.button);

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
                String name = serviceName.getText().toString().trim();

                String dbPath = "service-"+name;

                String[] info = {text_fn, text_ln, text_db, text_ad, text_pr, text_ps
                                ,text_id,text_dv};
                ServicesInfo infoServ = new ServicesInfo(info);

                database = FirebaseDatabase.getInstance().getReference();
                database.child("dbPath").child(name).child(user.getUid()).setValue(infoServ);

                Toast.makeText(getApplicationContext(), "Request sent, you will get an answer within two weeks",
                        Toast.LENGTH_LONG).show();

            }
        });




    }

    public void takeHome(View view){
        finish();
        startActivity(new Intent(getApplicationContext(), HomePage.class));
    }
}