package seg2105.project50.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class View_Ratings extends AppCompatActivity {

    ListView listviewRatings;
    List<String> ratings;
    DatabaseReference databaseEmployees;
    private String ser_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__ratings);

        listviewRatings = findViewById(R.id.list_of_ratings);
        ratings = new ArrayList<>();
        databaseEmployees = FirebaseDatabase.getInstance().getReference();
        Bundle extras = getIntent().getExtras();
        ser_num = extras.getString("branch_name").trim();

    }

    protected void onStart() {
        super.onStart();
        ratings.clear();

        databaseEmployees.child("Branch").child(ser_num).child("Ratings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ratings.clear();

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Rate temp = dataSnapshot1.getValue(Rate.class);
                    ratings.add(temp.getComment()+": "+temp.getRating()+" out of 5");
                }

                ArrayAdapter<String> ratingOfCustomer = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1 ,ratings);
                listviewRatings.setAdapter(ratingOfCustomer);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void take_go_back(View view){
        finish();
        startActivity(new Intent(getApplicationContext(),CustomerBranchChoice.class));
    }
}