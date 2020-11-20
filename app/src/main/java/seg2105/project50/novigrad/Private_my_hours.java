package seg2105.project50.novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Private_my_hours extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseUser user;
    private TextView monText,tueText,wedText,thurText,friText,satText,sunText;
    private String  bare_email = "";
    DatabaseReference databaseEmployees;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_my_hours);

        monText = findViewById(R.id.mondayText);
        tueText = findViewById(R.id.tuesdayText);
        wedText = findViewById(R.id.wednesdayText);
        thurText = findViewById(R.id.thursdayText);
        friText = findViewById(R.id.fridayText);
        satText = findViewById(R.id.saturdayText);
        sunText = findViewById(R.id.sundayText);

        databaseEmployees = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();



        String first_email = user.getEmail();

        for (int i = 0; i < first_email.length(); i++) {
            if (first_email.charAt(i) != '@' && first_email.charAt(i) != '.') {
                bare_email += first_email.charAt(i);
            }
        }

        Button done = findViewById(R.id.doneBTN);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String monday;
                String tuesday;
                String wednesday;
                String thursday;
                String friday;
                String saturday;
                String sunday;
                if(!isgood()){ //if the text fields are empty this method ends abruptly!
                    return;
                }
                try {
                    monday = monText.getText().toString().trim();
                    tuesday = tueText.getText().toString().trim();
                    wednesday = wedText.getText().toString().trim();
                    thursday = thurText.getText().toString().trim();
                    friday = friText.getText().toString().trim();
                    saturday = satText.getText().toString().trim();
                    sunday = sunText.getText().toString().trim();

                    Hours updateHours = new Hours(monday,tuesday,wednesday,thursday,friday,saturday,sunday);
                    databaseEmployees.child("Branch").child("Branch "+bare_email).child("Hours").setValue(updateHours);

                    Toast.makeText(Private_my_hours.this, "Click view to see changes", Toast.LENGTH_SHORT).show();


                }
                catch (Exception e){
                    finish();
                    startActivity(new Intent(getApplicationContext(),Private_my_hours.class));
                }

            }
        });

    }



    public void logoff(View v){
        auth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    public void home2(View v){
        finish();
        startActivity(new Intent(getApplicationContext(),Employee_homePage.class));
    }

    public void taketoviewhours(View v){
        finish();
        startActivity(new Intent(getApplicationContext(), View_hours_page.class));
    }

    private boolean goodMonday(){
        if (monText.getText().toString().isEmpty()){
            monText.setError("Text can't be empty");
            return false;
        }
        return true;
    }

    private boolean goodTuesday(){
        if(tueText.getText().toString().isEmpty()){
            tueText.setError("Text can't be empty");
            return false;
        }
        return true;
    }

    private boolean goodWednesday(){
        if(wedText.getText().toString().isEmpty()){
            wedText.setError("Text can't be empty");
            return false;
        }
        return true;
    }

    private boolean goodThursday(){
        if(thurText.getText().toString().isEmpty()){
            thurText.setError("Text can't be empty");
            return false;
        }
        return true;
    }

    private boolean goodFriday(){
        if(friText.getText().toString().isEmpty()){
            friText.setError("Text can't be empty");
            return false;
        }
        return true;
    }

    private boolean goodSaturday(){
        if(satText.getText().toString().isEmpty()){
            satText.setError("Text can't be empty");
            return false;
        }
        return true;
    }

    private  boolean goodSunday(){
        if(sunText.getText().toString().isEmpty()){
            sunText.setError("Text can't be empty");
            return false;
        }
        return true;
    }

    private boolean isgood(){
        return (goodMonday()&&goodTuesday()&&goodWednesday()&&goodThursday()&&goodFriday()&&goodSaturday()&&goodSunday());
    }

}