package seg2105.project50.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class View_hours_page extends AppCompatActivity {

    ListView listviewHours;
    List<String> hours;
    private String  bare_email = "";
    private FirebaseAuth auth;
    private FirebaseUser user;
    DatabaseReference databaseEmployees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hours_page);

        listviewHours = findViewById(R.id.hoursDB);
        hours = new ArrayList<>();

        databaseEmployees = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        String first_email = user.getEmail();

        for (int i = 0; i < first_email.length(); i++) {
            if (first_email.charAt(i) != '@' && first_email.charAt(i) != '.') {
                bare_email += first_email.charAt(i);
            }
        }


    }

    protected void onStart() {
        super.onStart();
        hours.clear();

        databaseEmployees.child("Branch").child("Branch "+bare_email).child("Hours").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hours.clear();


                Hours usersHours = dataSnapshot.getValue(Hours.class);


                if(usersHours!=null) { //incase we are iterating through other children of branches, ie serviceRequest
                    hours.add("monday: "+usersHours.getMonday());
                    hours.add("tuesday: " +usersHours.getTuesday());
                    hours.add("wednesday: "+usersHours.getWednesday());
                    hours.add("thursday: "+usersHours.getThursday());
                    hours.add("friday: "+usersHours.getFriday());
                    hours.add("saturday: "+usersHours.getSaturday());
                    hours.add("sunday: "+usersHours.getSunday());

                }
                ArrayAdapter<String> hourOfUser = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1 ,hours);
                listviewHours.setAdapter(hourOfUser);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void takeback(View view){
        finish();
        startActivity(new Intent(getApplicationContext(),Private_my_hours.class));
    }
}