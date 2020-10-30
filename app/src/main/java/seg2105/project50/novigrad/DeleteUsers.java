package seg2105.project50.novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteUsers extends AppCompatActivity {


    private EditText emailAddy;
    FirebaseAuth fb;
    FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_users);

        fb = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        emailAddy = findViewById(R.id.UserToDelete);

       // DatabaseReference dR ;

        Button backb = (Button)findViewById(R.id.back_from_delete);
        backb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), Admin.class));
            }
        });

        Button delete = (Button)findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string_emailAddy;
                DatabaseReference dR;
                try {

                    string_emailAddy = emailAddy.getText().toString().trim();
                    if(!(string_emailAddy.isEmpty())) { // otherwise empty value erases the whole database!
                        dR = FirebaseDatabase.getInstance().getReference("Citizens").child(string_emailAddy);
                        dR.removeValue();

                        Toast.makeText(DeleteUsers.this, string_emailAddy+" has been deleted", Toast.LENGTH_SHORT).show();



                        //if(dR.removeValue().isSuccessful()){
                          //  Toast.makeText(DeleteUsers.this, string_emailAddy + " has been deleted.", Toast.LENGTH_SHORT).show();
                       // }
                         //else{
                         //   Toast.makeText(DeleteUsers.this, "Alert "+string_emailAddy + " has not been deleted !!.", Toast.LENGTH_SHORT).show();
                       // }

                                    //mDatabase.getReference("Citizens").child(fb.getCurrentUser(string_emailAddy).getUid()).removeValue();
                                    // mDatabase.getReference("Citizens").child().equalTo(string_emailAddy).removeValue();
                                   // Toast.makeText(DeleteUsers.this, string_emailAddy + " has been deleted.", Toast.LENGTH_SHORT).show();
                    }



                    else{
                        Toast.makeText(DeleteUsers.this, "Input User to delete", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    finish();
                    Toast.makeText(DeleteUsers.this,  "No account has been deleted.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),DeleteUsers.class));
                }

            }
        });

    }
}