package seg2105.project50.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class AdminSendActivity extends AppCompatActivity {

    private EditText notif;
    private CheckBox notif_to_all;
    private CheckBox notif_to_emp;

    private Button send;

    private Date date;
    private Notification notification;

    private DatabaseReference database;
    private FirebaseAuth auth;

    private String additional_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_send);

        database = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        send = (Button)findViewById(R.id.sendnotification);


        notif = (EditText)findViewById(R.id.notifwrite);
        notif_to_all = (CheckBox)findViewById(R.id.customer);
        notif_to_emp = (CheckBox)findViewById(R.id.notiftoemp);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean finishActivity = false;

                additional_text = notif.getText().toString().trim();

                if(!(additional_text.equals(""))) {
                    if (notif_to_emp.isChecked()) {
                        sendToEmp();
                        Toast.makeText(getApplicationContext(), "Notification sent", Toast.LENGTH_LONG).show();
                        finishActivity = true;
                    }
                    if (notif_to_all.isChecked()) {
                        sendToCust();
                        Toast.makeText(getApplicationContext(), "Notification sent", Toast.LENGTH_LONG).show();
                        finishActivity = true;
                    }
                    if(finishActivity){
                        finish();
                        startActivity(new Intent(getApplicationContext(),Admin.class));
                    }
                    if(!(notif_to_all.isChecked() || notif_to_all.isChecked())){
                        Toast.makeText(getApplicationContext(), "Please select the recipients", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Empty message...", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void sendToCust(){

        date = new Date();

        additional_text = notif.getText().toString().trim();

        notification = new Notification(additional_text, date);

        database.child("Citizens")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot ServiceSnapshot) {

                        for(DataSnapshot dataSnapshot : ServiceSnapshot.getChildren()) {
                            Person person = dataSnapshot.getValue(Person.class);
                            database.child("Public Notifications Customers").child(generateBareName(person.getEmail())).child(date.toString()).setValue(notification);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });



    }
    private void sendToEmp(){

        date = new Date();

        String additional_text = notif.getText().toString().trim();

        notification = new Notification(additional_text, date);

        database.child("Branch")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot ServiceSnapshot) {

                        for(DataSnapshot dataSnapshot : ServiceSnapshot.getChildren()) {
                            BranchInfo branch = dataSnapshot.child("Branch Info").getValue(BranchInfo.class);
                            database.child("Public Notifications Employee").child(generateBareName(branch.getEmail())).child(date.toString()).setValue(notification);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });


    }

    public void back(View view){
        finish();
        startActivity(new Intent(getApplicationContext(),Admin.class));
    }

    public String generateBareName(String string_email){
        String email_no_signs="";

        for(int i =0;i<string_email.length();i++){
            if(string_email.charAt(i)!='@'&&string_email.charAt(i)!='.'){
                email_no_signs+=string_email.charAt(i);
            }
        }
        return email_no_signs;
    }
}