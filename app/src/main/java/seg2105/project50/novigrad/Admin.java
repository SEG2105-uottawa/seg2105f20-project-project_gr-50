package seg2105.project50.novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Admin extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

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
}