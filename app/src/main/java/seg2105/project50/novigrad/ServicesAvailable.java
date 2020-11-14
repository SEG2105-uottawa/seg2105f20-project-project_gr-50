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
    private ServicesSettings service4;
    private ServicesSettings service5;
    private ServicesSettings service6;
    private ServicesSettings service7;
    private ServicesSettings service8;


    Button btn_serv1;
    Button btn_serv2;
    Button btn_serv3;
    Button btn_serv4;
    Button btn_serv5;
    Button btn_serv6;
    Button btn_serv7;
    Button btn_serv8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_available);


        btn_serv1 = (Button)findViewById(R.id.btn_service1);
        btn_serv2 = (Button)findViewById(R.id.btn_service2);
        btn_serv3 = (Button)findViewById(R.id.btn_service3);
        btn_serv4 = (Button)findViewById(R.id.btn_service4);
        btn_serv5 = (Button)findViewById(R.id.btn_service5);
        btn_serv6 = (Button)findViewById(R.id.btn_service6);
        btn_serv7 = (Button)findViewById(R.id.btn_service7);
        btn_serv8 = (Button)findViewById(R.id.btn_service8);



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

        database.child("Services")
                .child("Service 4")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                        service4 = dataSnapshot.getValue(ServicesSettings.class);
                        if(service4!= null) {
                            if (!service4.isActive())
                                btn_serv4.setVisibility(View.GONE);
                            String name = service4.getName();
                            btn_serv4.setText(name);
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

        database.child("Services")
                .child("Service 5")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                        service5 = dataSnapshot.getValue(ServicesSettings.class);
                        if(service5!= null) {
                            if (!service5.isActive())
                                btn_serv5.setVisibility(View.GONE);
                            String name = service5.getName();
                            btn_serv5.setText(name);
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

        database.child("Services")
                .child("Service 6")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                        service6 = dataSnapshot.getValue(ServicesSettings.class);
                        if(service6!= null) {
                            if (!service6.isActive())
                                btn_serv6.setVisibility(View.GONE);
                            String name = service6.getName();
                            btn_serv6.setText(name);
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

        database.child("Services")
                .child("Service 7")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                        service7 = dataSnapshot.getValue(ServicesSettings.class);
                        if(service7!= null) {
                            if (!service7.isActive())
                                btn_serv7.setVisibility(View.GONE);
                            String name = service7.getName();
                            btn_serv7.setText(name);
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

        database.child("Services")
                .child("Service 8")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                        service8 = dataSnapshot.getValue(ServicesSettings.class);
                        if(service8!= null) {
                            if (!service8.isActive())
                                btn_serv8.setVisibility(View.GONE);
                            String name = service8.getName();
                            btn_serv8.setText(name);
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

    public void setService4(View view){
        finish();
        Intent intent = new Intent(getApplicationContext(), ServiceInfoGather.class);
        intent.putExtra("ser_num", "Service 4");
        startActivity(intent);
    }

    public void setService5(View view){
        finish();
        Intent intent = new Intent(getApplicationContext(), ServiceInfoGather.class);
        intent.putExtra("ser_num", "Service 5");
        startActivity(intent);
    }

    public void setService6(View view){
        finish();
        Intent intent = new Intent(getApplicationContext(), ServiceInfoGather.class);
        intent.putExtra("ser_num", "Service 6");
        startActivity(intent);
    }

    public void setService7(View view){
        finish();
        Intent intent = new Intent(getApplicationContext(), ServiceInfoGather.class);
        intent.putExtra("ser_num", "Service 7");
        startActivity(intent);
    }

    public void setService8(View view){
        finish();
        Intent intent = new Intent(getApplicationContext(), ServiceInfoGather.class);
        intent.putExtra("ser_num", "Service 8");
        startActivity(intent);
    }
    public void takeHome(View view){
        Bundle extras = getIntent().getExtras();
        String ser_num = extras.getString("code").trim();

        if(ser_num.equals("admin")){
            finish();
            startActivity(new Intent(getApplicationContext(), Admin.class));
        }else {
            finish();
            startActivity(new Intent(getApplicationContext(), HomePage.class));
        }
    }
}

