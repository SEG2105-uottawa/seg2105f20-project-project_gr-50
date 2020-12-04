package seg2105.project50.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.Calendar;

public class CustomerBranchChoice extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;

    private ListView listview;
    private ListView available_service;

    private ArrayList<String> autoCompleteKey;

    private Hours hours;
    private String workHours;

    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;

    private ServicesSettings settings;


    private BranchInfo service;
    private ServicesSettings service_settings;

    private boolean ActivatedService = false;
    private AutoCompleteTextView search;
    private Button searchBtn;

    ArrayList<BranchInfo> list = new ArrayList<>();
    ArrayList<String> list2 = new ArrayList<>();


    @Override
    protected void onCreate(final Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_branch_choice);

        listview = (ListView)findViewById(R.id.branch_listview);
        available_service = (ListView)findViewById(R.id.available_ser);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        search = (AutoCompleteTextView) findViewById(R.id.branch_search);
        searchBtn = (Button)findViewById(R.id.search_btn3);

        radioGroup = (RadioGroup)findViewById(R.id.radiogroup3);

        radioButton1 = (RadioButton)findViewById(R.id.radioButton);
        radioButton2 = (RadioButton)findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton)findViewById(R.id.radioButton3);

        allBranches();

        refreshAvailableServices();


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String service_name = search.getText().toString().trim();


                if(radioButton1.isChecked()) {
                    if (service_name.equals("")) {
                        allBranches();
                    } else {
                        database.child("Services")
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot ServiceSnapshot) {
                                        for (DataSnapshot dataSnapshot : ServiceSnapshot.getChildren()) {

                                            if (dataSnapshot != null) {
                                                if (dataSnapshot.getKey().equals(service_name)) {
                                                    Toast.makeText(getApplicationContext(), "button working", Toast.LENGTH_LONG).show();
                                                    refreshBranch_service(service_name);
                                                    break;
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                    }
                }
                else if(radioButton2.isChecked()){
                    list.clear();
                    database.child("Branch")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange( DataSnapshot ServiceSnapshot) {
                                    // Hours r = ServiceSnapshot.getValue()
                                    for(DataSnapshot dataSnapshot : ServiceSnapshot.getChildren()) {
                                        hours = dataSnapshot.child("Hours").getValue(Hours.class);

                                        Calendar calendar = Calendar.getInstance();
                                        int days = calendar.get(Calendar.DAY_OF_WEEK);
                                        if (hours != null) {
                                            switch (days) {
                                                case 6:
                                                    workHours = hours.getSunday();
                                                    break;
                                                case 0:
                                                    workHours = hours.getMonday();
                                                    break;
                                                case 1:
                                                    workHours = hours.getTuesday();
                                                    break;
                                                case 2:
                                                    workHours = hours.getWednesday();
                                                    break;
                                                case 3:
                                                    workHours = hours.getThursday();
                                                    break;
                                                case 4:
                                                    workHours = hours.getFriday();
                                                    break;
                                                case 5:
                                                    workHours = hours.getSaturday();
                                                    break;
                                            }
                                            if(workHours.equals("CLOSED")){

                                            }else {
                                                String[] startEnd = workHours.split("-");

                                                String time1 = startEnd[0];
                                                String time2 = startEnd[1];

                                                int customerTime;

                                                int[] intTime = new int[2];
                                                String temp = "";
                                                String temp2 = "";
                                                String tempCustomer = "";


                                                for (int x = 0; x < time1.length(); x++) {
                                                    if (Character.isDigit(time1.charAt(x))) {
                                                        temp += time1.charAt(x);
                                                    }
                                                }
                                                for (int xx = 0; xx < time2.length(); xx++) {
                                                    if (Character.isDigit(time2.charAt(xx))) {
                                                        temp2 += time2.charAt(xx);
                                                    }
                                                }
                                                for (int xxx = 0; xxx < service_name.length(); xxx++) {
                                                    if (Character.isDigit(service_name.charAt(xxx))) {
                                                        tempCustomer += service_name.charAt(xxx);
                                                    }
                                                }


                                                    intTime[0] = Integer.parseInt(temp);
                                                    intTime[1] = Integer.parseInt(temp2);
                                                    customerTime = Integer.parseInt(tempCustomer);

                                                    if(time1.charAt(time1.length()-2) == 'p'){
                                                        intTime[0] += 12;
                                                    }
                                                if(time2.charAt(time2.length()-2) == 'p'){
                                                    intTime[1] += 12;
                                                }

                                                if(service_name.charAt(service_name.length()-2) == 'p'){
                                                    customerTime += 12;
                                                }

                                                if(customerTime >= intTime[0] && customerTime <= intTime[1]){

                                                    service = dataSnapshot.child("Branch Info").getValue(BranchInfo.class);
                                                    list.add(service);
                                                    listview.requestLayout();
                                                }
                                            }
                                        }
                                    }

                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });

                        setListview();

                }
                else if(radioButton3.isSelected()){

                }


            }
        });


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                BranchInfo branch = (BranchInfo) parent.getAdapter().getItem(position);
                String name = generateBranchName(branch.getEmail());
                finish();
                Intent intent = new Intent(getApplicationContext(), ServicesAvailable.class);
                intent.putExtra("ser_num", name);
                startActivity(intent);
            }
        });

    }


    public void Home(View view){
        finish();
        startActivity(new Intent(getApplicationContext(), HomePage.class));
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

    private void setListview(){
        BranchDisplay listAdapter = new BranchDisplay(this, R.layout.branch_display_list, list);
        listview.setAdapter(listAdapter);
    }

    public void refreshAvailableServices(){
        list2 = new ArrayList<>();

        database.child("Services")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot ServiceSnapshot) {

                        for(DataSnapshot dataSnapshot : ServiceSnapshot.getChildren()) {
                            service_settings = dataSnapshot.getValue(ServicesSettings.class);
                            if (service_settings.isActive()) {
                                String name_service = service_settings.getName();
                                list2.add(name_service);
                                available_service.requestLayout();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

        ArrayAdapter listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list2);
        available_service.setAdapter(listAdapter);

    }

    public void refreshAutocompleteService(){
        autoCompleteKey.clear();
        database.child("Services")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot ServiceSnapshot) {

                        for(DataSnapshot dataSnapshot : ServiceSnapshot.getChildren()) {
                            settings = dataSnapshot.getValue(ServicesSettings.class);
                            if (settings.isActive()) {
                                autoCompleteKey.add(settings.getName());
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,autoCompleteKey);
        search.setAdapter(adapter);
    }

    public void refreshAutocompleteAddress(){
        autoCompleteKey.clear();
        database.child("Branch")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot ServiceSnapshot) {
                        for(DataSnapshot dataSnapshotUp : ServiceSnapshot.getChildren()) {
                            DataSnapshot dataSnapshot = dataSnapshotUp.child("Branch Info");
                            service = dataSnapshot.getValue(BranchInfo.class);
                            if (service != null) {
                               autoCompleteKey.add(service.getAddress());
                            } else {
                                //TODO make a toast , "something went wrong"
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,autoCompleteKey);
        search.setAdapter(adapter);
    }

    public void allBranches(){
        list.clear();

        database.child("Branch")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot ServiceSnapshot) {

                        for(DataSnapshot dataSnapshotUp : ServiceSnapshot.getChildren()) {
                            DataSnapshot dataSnapshot = dataSnapshotUp.child("Branch Info");
                            service = dataSnapshot.getValue(BranchInfo.class);
                            if (service != null) {

                                list.add(service);
                                listview.requestLayout();

                            } else {
                                //startActivity(new Intent(getApplicationContext(), HomePage.class)); //added
                                //finish();
                                //TODO make a toast , "something went wrong"
                            }

                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });



        BranchDisplay listAdapter = new BranchDisplay(this, R.layout.branch_display_list, list);
        listview.setAdapter(listAdapter);

    }


    public void refreshBranch_service(final String serviceN){

        list.clear();

        database.child("Branch")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot ServiceSnapshot) {
                        for(DataSnapshot dataSnapshotUp : ServiceSnapshot.getChildren()) {
                            DataSnapshot dataSnapshot = dataSnapshotUp.child("Branch Info");
                            service = dataSnapshot.getValue(BranchInfo.class);
                            if (service != null) {
                                if( dataSnapshotUp.child("Services").child(serviceN).exists()){
                                    list.add(service);
                                    listview.requestLayout();
                                }
                            } else {
                                //startActivity(new Intent(getApplicationContext(), HomePage.class)); //added
                                //finish();
                                //TODO make a toast , "something went wrong"
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });



        BranchDisplay listAdapter = new BranchDisplay(this, R.layout.branch_display_list, list);
        listview.setAdapter(listAdapter);


    }


}

