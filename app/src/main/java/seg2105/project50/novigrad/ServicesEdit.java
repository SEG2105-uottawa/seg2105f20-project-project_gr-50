package seg2105.project50.novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ServicesEdit extends AppCompatActivity {

    private RadioGroup serviceChoice, serviceState;
    FirebaseAuth fb;
    FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_edit);

        fb = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();







        Button edit = (Button)findViewById(R.id.editdone);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                serviceChoice = (RadioGroup) findViewById(R.id.servicechoice);
                serviceState = (RadioGroup)findViewById(R.id.servicechoicestate);

                int selecChoice = serviceChoice.getCheckedRadioButtonId();
                int selecState = serviceState.getCheckedRadioButtonId();

                RadioButton r1 = (RadioButton)findViewById(selecChoice);
                RadioButton r2 = (RadioButton)findViewById(selecState);

                final String selectedChoise = r1.getText().toString().trim();
                String selectedState = r2.getText().toString().trim();

                CheckBox firstname = (CheckBox)findViewById(R.id.ser_firstname);
                CheckBox lasttname = (CheckBox)findViewById(R.id.ser_lastname);
                CheckBox dateofbirth = (CheckBox)findViewById(R.id.ser_dateofbirth);
                CheckBox adress = (CheckBox)findViewById(R.id.ser_adress);
                CheckBox proofofresidence = (CheckBox)findViewById(R.id.ser_proofofresidence);
                CheckBox proofofstatus = (CheckBox)findViewById(R.id.ser_proofofstatus);
                CheckBox idnumber = (CheckBox)findViewById(R.id.ser_idnumber);
                CheckBox dvlicense = (CheckBox)findViewById(R.id.ser_licensetype);

                boolean firstnameCheck = firstname.isChecked();
                boolean lastnameCheck = lasttname.isChecked();
                boolean dateofbirthCheck = dateofbirth.isChecked();
                boolean adressCheck = adress.isChecked();
                boolean proofofresidenceCheck = proofofresidence.isChecked();
                boolean proofofstatusCheck = proofofstatus.isChecked();
                boolean idnumberCheck = idnumber.isChecked();
                boolean active = (selectedState.equals("Activate")?true:false);
                boolean dvlicenseCheck = dvlicense.isChecked();


                EditText name = (EditText)findViewById(R.id.ser_servicename);
                final String serviceName = name.getText().toString().trim();

                final boolean[] info = {firstnameCheck,lastnameCheck,dateofbirthCheck, adressCheck, proofofresidenceCheck
                        ,proofofstatusCheck, idnumberCheck, active, dvlicenseCheck};

                ServicesSettings service = new ServicesSettings(info, serviceName);

                mDatabase.getReference().child("Services").child(selectedChoise).setValue(service);
                Toast.makeText(getApplicationContext(), "Service Updated!",
                        Toast.LENGTH_LONG).show();

            }
        });


    }

    public void adminHome(View view){
        finish();
        startActivity(new Intent(getApplicationContext(),Admin.class));
    }
}