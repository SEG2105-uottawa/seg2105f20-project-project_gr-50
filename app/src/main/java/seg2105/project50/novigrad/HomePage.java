package seg2105.project50.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomePage extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;

    private Button btnLogout;
    private TextView textV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();

        textV = (TextView) findViewById(R.id.textView);
        btnLogout = (Button) findViewById(R.id.btnLogout);


        btnLogout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                startActivity(new Intent(HomePage.this,
                        MainActivity.class));
            }
        });

        if (user == null){
            finish();
            startActivity(new Intent(HomePage.this, MainActivity.class));
        }
        else{
            database.child("Citizen")
                    .child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Person citizen = dataSnapshot.getValue(Person.class);

                    String name = citizen.getName();
                    String role = citizen.getRole();

                    textV.setText("Welcome "+name+"\nRole :"+role);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }


}