package seg2105.project50.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServicesAvailable extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference database;
    private ServicesSettings service;
    private ServicesSettings service2;
    private ServicesSettings service3;

    Button btn_serv1;
    Button btn_serv2;
    Button btn_serv3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_available);


        btn_serv1 = (Button)findViewById(R.id.btn_service1);
        btn_serv2 = (Button)findViewById(R.id.btn_service2);
        btn_serv3 = (Button)findViewById(R.id.btn_service3);



        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();


        database.child("Services")
                .child("Service 1")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                        service = dataSnapshot.getValue(ServicesSettings.class);
                        if(service!= null) {
                            if (!service.isActive())
                                    btn_serv1.setVisibility(View.GONE);
                            String name = service.getName();
                            btn_serv1.setText(name);
                        }
                        else{
                            startActivity(new Intent(getApplicationContext(), HomePage.class)); //added
                            finish();
                            //TODO make a toast , "something went wrong"
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

        database.child("Services")
                .child("Service 2")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                        service2 = dataSnapshot.getValue(ServicesSettings.class);
                        if(service2!= null) {
                            if (!service2.isActive())
                                btn_serv2.setVisibility(View.GONE);
                            String name = service2.getName();
                            btn_serv2.setText(name);
                        }
                        else{
                            startActivity(new Intent(getApplicationContext(), HomePage.class)); //added
                            finish();
                            //TODO make a toast , "something went wrong"
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

        database.child("Services")
                .child("Service 3")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                        service3 = dataSnapshot.getValue(ServicesSettings.class);
                        if(service3!= null) {
                            if (!service3.isActive())
                                btn_serv3.setVisibility(View.GONE);
                            String name = service3.getName();
                            btn_serv3.setText(name);
                        }
                        else{
                            startActivity(new Intent(getApplicationContext(), HomePage.class)); //added
                            finish();
                            Toast.makeText(getApplicationContext(), "Something went wrong...",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });


    }

    public void setService1(View view){
        finish();
        Intent intent = new Intent(getApplicationContext(), ServiceInfoGather.class);
        intent.putExtra("ser_num", "Service 1");
        startActivity(intent);
    }

    public void setService2(View view){
        finish();
        Intent intent = new Intent(getApplicationContext(), ServiceInfoGather.class);
        intent.putExtra("ser_num", "Service 2");
        startActivity(intent);
    }

    public void setService3(View view){
        finish();
        Intent intent = new Intent(getApplicationContext(), ServiceInfoGather.class);
        intent.putExtra("ser_num", "Service 3");
        startActivity(intent);
    }
    public void takeHome(View view){
        finish();
        startActivity(new Intent(getApplicationContext(), HomePage.class));
    }
}

