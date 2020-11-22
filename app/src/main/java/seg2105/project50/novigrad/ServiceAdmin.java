package seg2105.project50.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ServiceAdmin extends AppCompatActivity {

    private DatabaseReference database;

    private ListView listview;

    EditText newService;
    ServicesSettings service;
    private boolean[] activeService = new boolean[30];

    private boolean noServ = false;

    ArrayList<String> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_available_admin);

        listview = (ListView)findViewById(R.id.services_listview);

        database = FirebaseDatabase.getInstance().getReference();

        newService = (EditText)findViewById(R.id.new_service);

        refresh();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String service_name = (String) parent.getAdapter().getItem(position);
                goEditService(service_name);
            }
        });


    }

    public void addNewService(View view){
        String name = newService.getText().toString().trim();
        if(!name.equals("")) {
            database = FirebaseDatabase.getInstance().getReference();
            ServicesSettings newService = new ServicesSettings(name);

            database.child("Services").child(name).setValue(newService);
            Toast.makeText(getApplicationContext(), "Service Updated!",
                    Toast.LENGTH_LONG).show();
            refresh();
        }else{
            Toast.makeText(getApplicationContext(), "Can't make a service with no name!",
                    Toast.LENGTH_LONG).show();
        }

    }

    public void goEditService(String service_name){
        finish();
        Intent intent = new Intent(getApplicationContext(), ServicesEdit.class);
        intent.putExtra("ser_name", service_name);
        startActivity(intent);
    }

    public void adminHome(View view){
        finish();
        startActivity(new Intent(getApplicationContext(), Admin.class));
        listview.deferNotifyDataSetChanged();
    }

    private void refresh(){
        final ArrayList<String> list = new ArrayList<>();

        database.child("Services")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot ServiceSnapshot) {

                        int numService = (int)ServiceSnapshot.getChildrenCount();
                        int count = 0;
                        activeService = new boolean[numService];
                        if (numService<1) {
                            noServ = true;
                            activeService = new boolean[2];
                        }

                        for(DataSnapshot dataSnapshot : ServiceSnapshot.getChildren()) {
                            service = dataSnapshot.getValue(ServicesSettings.class);
                            if (service != null && !noServ) {

                                activeService[count]=service.isActive();
                                String name = service.getName();
                                list.add(name);
                                listview.requestLayout();

                                count++;

                            } else {
                                startActivity(new Intent(getApplicationContext(), HomePage.class)); //added
                                finish();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });



        ArrayAdapter listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        listview.setAdapter(listAdapter);

        if(!noServ){
            int t =0;
            for (boolean i : activeService){
                listview.setItemChecked(t, i);
                t++;
            }
        }
    }
}