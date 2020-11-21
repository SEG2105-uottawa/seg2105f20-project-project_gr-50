package seg2105.project50.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ServicesAvailable extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;

    ListView listview;
    TextView noService;

    private ServicesSettings service;
    private boolean ActivatedService = false;
    private String ser_num;

    ArrayList<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_available);

        listview = (ListView)findViewById(R.id.services_listview);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        Bundle extras = getIntent().getExtras();
        ser_num = extras.getString("ser_num").trim();

        database.child("Branch").child(ser_num).child("Services")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot ServiceSnapshot) {
                        if(!(ServiceSnapshot.hasChildren())){
                            noService = (TextView)findViewById(R.id.noService);
                            noService.setText("No services available at the moment");
                        }else{
                            noService = (TextView)findViewById(R.id.noService);
                            noService.setVisibility(View.GONE);
                        }
                        for(DataSnapshot dataSnapshot : ServiceSnapshot.getChildren()) {
                            service = dataSnapshot.getValue(ServicesSettings.class);
                            if (service != null) {
                                if (service.isActive()) {
                                    String name = service.getName();
                                    list.add(name);
                                    listview.requestLayout();
                                    ActivatedService = true;
                                }
                            } else {
                                startActivity(new Intent(getApplicationContext(), HomePage.class)); //added
                                finish();
                                //TODO make a toast , "something went wrong"
                            }

                        }

                        if(!ActivatedService){
                            noService = (TextView)findViewById(R.id.noService);
                            noService.setText("No services available at the moment");
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
                Intent intent = new Intent(getApplicationContext(), ServiceInfoGather.class);
                intent.putExtra("ser_num", name);
                intent.putExtra("ser_branch", ser_num);
                startActivity(intent);
            }
        });


    }


    public void Home(View view){
            finish();
            startActivity(new Intent(getApplicationContext(), HomePage.class));
    }


}

