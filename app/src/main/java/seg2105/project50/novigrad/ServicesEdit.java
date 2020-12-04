package seg2105.project50.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServicesEdit extends AppCompatActivity {


    private FirebaseAuth fb;
    private FirebaseDatabase mDatabase;

    private ServicesSettings service;
    private String ser_num;
    private BranchInfo infoservice;
    private String global;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_edit);

        fb = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        Bundle extras = getIntent().getExtras();
        ser_num = extras.getString("ser_name").trim();


        final EditText serviceName = (EditText)findViewById(R.id.ser_servicename);
        final EditText servicePrice = (EditText)findViewById(R.id.ser_serviceprice);


        final RadioGroup serviceState = (RadioGroup)findViewById(R.id.servicechoicestate);

        final CheckBox firstname = (CheckBox) findViewById(R.id.ser_firstname);
        final CheckBox lasttname = (CheckBox) findViewById(R.id.ser_lastname);
        final CheckBox dateofbirth = (CheckBox) findViewById(R.id.ser_dateofbirth);
        final CheckBox adress = (CheckBox) findViewById(R.id.ser_adress);
        final CheckBox proofofresidence = (CheckBox) findViewById(R.id.ser_proofofresidence);
        final CheckBox proofofstatus = (CheckBox) findViewById(R.id.ser_proofofstatus);
        final CheckBox idnumber = (CheckBox) findViewById(R.id.ser_idnumber);
        final CheckBox dvlicense = (CheckBox) findViewById(R.id.ser_licensetype);


        final Button edit = (Button) findViewById(R.id.editdone);


        mDatabase.getReference().child("Services")
                .child(ser_num)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                        service = dataSnapshot.getValue(ServicesSettings.class);
                        if(service!= null) {}
                        else{
                            startActivity(new Intent(getApplicationContext(), Admin.class)); //added
                            finish();
                            Toast.makeText(getApplicationContext(), "Something went wrong...", Toast.LENGTH_LONG).show();
                        }

                        serviceName.setText(service.getName());
                        servicePrice.setText(service.getPrice());

                        firstname.setChecked(service.isFirstname());
                        lasttname.setChecked(service.isLastname());
                        dateofbirth.setChecked(service.isDateofbirth());
                        adress.setChecked(service.isAdress());
                        proofofresidence.setChecked(service.isProofofresidence());
                        proofofstatus.setChecked(service.isProofofstatus());
                        idnumber.setChecked(service.isIdnumber());
                        dvlicense.setChecked(service.isLicensetype());

                        if (service.isActive()){
                            serviceState.check(R.id.ser_activate);
                        }
                        else{
                            serviceState.check(R.id.ser_disable);
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });



        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int selecState = serviceState.getCheckedRadioButtonId();

                RadioButton r2 = (RadioButton) findViewById(selecState);

                String selectedState = r2.getText().toString().trim();

                boolean firstnameCheck = firstname.isChecked();
                boolean lastnameCheck = lasttname.isChecked();
                boolean dateofbirthCheck = dateofbirth.isChecked();
                boolean adressCheck = adress.isChecked();
                boolean proofofresidenceCheck = proofofresidence.isChecked();
                boolean proofofstatusCheck = proofofstatus.isChecked();
                boolean idnumberCheck = idnumber.isChecked();
                boolean active = (selectedState.equals("Activate") ? true : false);
                boolean dvlicenseCheck = dvlicense.isChecked();


                EditText name = (EditText) findViewById(R.id.ser_servicename);
                final String serviceName = name.getText().toString().trim();
                String price = servicePrice.getText().toString().trim();

                if(!active){
                    deleteServiceBranch(serviceName);
                }

                final boolean[] info = {firstnameCheck, lastnameCheck, dateofbirthCheck, adressCheck, proofofresidenceCheck
                        , proofofstatusCheck, idnumberCheck, active, dvlicenseCheck};

                ServicesSettings service = new ServicesSettings(info, serviceName, price);

                if(!(service.getName().equals(ser_num)))
                {
                    mDatabase.getReference().child("Services").child(ser_num).removeValue();
                    ser_num = service.getName();

                }

                mDatabase.getReference().child("Services").child(ser_num).setValue(service);
                Toast.makeText(getApplicationContext(), "Service Updated!",
                        Toast.LENGTH_LONG).show();

            }
        });


    }

        public void adminBack(View view){
        finish();
        startActivity(new Intent(getApplicationContext(),ServiceAdmin.class));
    }

    public void serviceDelete(View view){
        mDatabase.getReference().child("Services").child(ser_num).removeValue();
        deleteServiceBranch(ser_num);
        finish();
        startActivity(new Intent(getApplicationContext(), ServiceAdmin.class));

    }

    public void deleteServiceBranch(String servi){

        global = servi;
        mDatabase.getReference().child("Branch").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot ServiceSnapshot) {
                        for(DataSnapshot dataSnapshot : ServiceSnapshot.getChildren()) {
                            DataSnapshot snap = dataSnapshot.child("Branch Info");
                                infoservice = snap.getValue(BranchInfo.class);
                                mDatabase.getReference().child("Branch").child(generateBranchName(infoservice.getEmail()))
                                        .child("Services").child(global).removeValue();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });


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

    public void takeAdminToServiceView(View view){
        finish();
        Intent intent = new Intent(getApplicationContext(), ServicesAvailable.class);
        intent.putExtra("code", "admin");
        startActivity(intent);
    }
}