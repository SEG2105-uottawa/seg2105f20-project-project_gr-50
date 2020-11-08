package seg2105.project50.novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class Employee_homePage extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home_page);
        auth = FirebaseAuth.getInstance();
    }

    public void myservice(View v){
        finish();
        startActivity(new Intent(getApplicationContext(),Private_my_services.class));
    }

    public void myhours(View v){
        finish();
        startActivity(new Intent(getApplicationContext(),Private_my_hours.class));
    }

    public void approverequest(View v){
        finish();
        startActivity(new Intent(getApplicationContext(), Private_approve_requests.class));
    }

    public void logout(View v){
        auth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}