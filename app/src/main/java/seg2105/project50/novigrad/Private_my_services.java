package seg2105.project50.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Private_my_services extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference database;
    private ServicesSettings service;
    private ListView listview;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_my_services);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        listview = (ListView)findViewById(R.id.employee_services);
        list = new ArrayList<>();

        database.child("Services")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot ServiceSnapshot) {

                        for(DataSnapshot dataSnapshot : ServiceSnapshot.getChildren()) {
                            service = dataSnapshot.getValue(ServicesSettings.class);
                            if (service.isActive()) {
                                list.add(service.getName());
                                listview.requestLayout();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

        ArrayAdapter listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        listview.setAdapter(listAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) parent.getAdapter().getItem(position);
                finish();
                Intent intent = new Intent(getApplicationContext(), EditServiceEmployee.class);
                intent.putExtra("ser_num", name);
                startActivity(intent);
            }
        });


    }

    public void logO(View v){ // this logs them out
        auth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    public void home1(View v){
        finish();
        startActivity(new Intent(getApplicationContext(),Employee_homePage.class));
    }

    public void take_to_view_my_activatedServices(View view){
        finish();
        startActivity(new Intent(getApplicationContext(),View_active_personal_service.class));
    }
}