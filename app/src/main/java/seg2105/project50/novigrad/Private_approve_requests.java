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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Private_approve_requests extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;

    private DatabaseReference database;
    private ServicesInfo service;
    private ListView listview;
    private ArrayList<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_approve_requests);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        listview = (ListView)findViewById(R.id.employee_request);
        list = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        database.child("Branch").child(generateBranchName(user.getEmail()))
                .child("Request")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot ServiceSnapshot) {
                    list.clear();
                        for(DataSnapshot dataSnapshotUp : ServiceSnapshot.getChildren()) {
                            for(DataSnapshot dataSnapshot : dataSnapshotUp.getChildren()) {
                                service = dataSnapshot.getValue(ServicesInfo.class);
                                list.add(service.getServiceName()+ " #From# "+dataSnapshotUp.getKey().trim()); //shows the customers email that requested it.
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
               String name = (String) parent.getAdapter().getItem(position); // this will have ie. (ID #From# johngmailcom) I will split in two parts.
               String service_type = "";
               String customers_id = "";
               int count =0;
               for(int i =0;i<name.length();i++){
                   if(name.charAt(i)!='#'&&count<1){
                       service_type+=name.charAt(i);
                   }
                   if(name.charAt(i)=='#'){
                       count++;
                   }
                   if(name.charAt(i)!='#'&&count>1){
                       customers_id+=name.charAt(i);
                   }

               }
                finish();
                Intent intent = new Intent(getApplicationContext(), Accept_or_delete_requests.class); // put the new activity here to delete/accept.
                intent.putExtra("service_the_type", service_type.trim()); //particular request from list shown needed to approve/decline.
                intent.putExtra("customer_who_sent_it",customers_id.trim()); //change name in both places
                startActivity(intent);





                //TO-DO  FINISH THAT
            }
        });


    }

    public void logaway(View v){
        auth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    public void home(View v){
        finish();
        startActivity(new Intent(getApplicationContext(),Employee_homePage.class));
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
}