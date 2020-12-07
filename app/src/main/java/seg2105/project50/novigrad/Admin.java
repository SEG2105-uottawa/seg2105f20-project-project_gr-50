package seg2105.project50.novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Admin extends AppCompatActivity {

    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        database = FirebaseDatabase.getInstance().getReference();

    }

    public void sendNotfi(View view){
        finish();
        startActivity(new Intent(getApplicationContext(), AdminSendActivity.class));
    }

    public void adminOut(View view){
        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void manageUsers(View view){
        finish();
        startActivity(new Intent(getApplicationContext(), DeleteUsers.class));
    }


    public void manageServices(View view){
        finish();
        startActivity(new Intent(getApplicationContext(), ServiceAdmin.class));
    }

    public void deleteNotif(View view){
        database.child("Public Notifications Customers").removeValue();
        database.child("Public Notifications Employee").removeValue();

    }
}