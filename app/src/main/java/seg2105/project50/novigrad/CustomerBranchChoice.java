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

public class CustomerBranchChoice extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;

    ListView listview;

    private BranchInfo service;
    private boolean ActivatedService = false;

    ArrayList<BranchInfo> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_branch_choice);

        listview = (ListView)findViewById(R.id.branch_listview);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();


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


}

