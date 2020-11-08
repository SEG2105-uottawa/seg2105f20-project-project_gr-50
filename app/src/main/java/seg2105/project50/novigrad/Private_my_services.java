package seg2105.project50.novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class Private_my_services extends AppCompatActivity {

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_my_services);
        auth = FirebaseAuth.getInstance();
    }

    public void logO(View v){ // this logs them out
        auth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    public void home1(View v){
        finish();
        startActivity(new Intent(getApplicationContext(),Employee_homePage.class));
    }
}