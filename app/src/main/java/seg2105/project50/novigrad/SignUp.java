package seg2105.project50.novigrad;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

public class SignUp extends AppCompatActivity {

    private Button btnsignup2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

            btnsignup2 = (Button)findViewById(R.id.btnsignup);
            btnsignup2.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    startActivity(new Intent(SignUp.this, HomePage.class));
                }
            });
    }
}