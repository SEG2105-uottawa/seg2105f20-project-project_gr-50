package seg2105.project50.novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Send_rating extends AppCompatActivity {


    private String branch_name;
    private TextView comment;
    private TextView rating;
    private Button done;
    private FirebaseUser user;

    private FirebaseAuth auth;

    private DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_rating);

        comment = findViewById(R.id.commentLine);
        rating = findViewById(R.id.RatingLine);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        database = FirebaseDatabase.getInstance().getReference();

        Bundle extras = getIntent().getExtras();
        branch_name = extras.getString("branch_name").trim();

    }

    public void Done(View view){

    if(validComment()&&validRating()){
        String valid_comment = comment.getText().toString().trim();
        String valid_rating = rating.getText().toString().trim();

         Rate newOne = new Rate(valid_comment,valid_rating);
         database.child("Branch").child(branch_name).child("Ratings").child(generateUserID()).setValue(newOne);
        finish();
        startActivity(new Intent(getApplicationContext(),CustomerBranchChoice.class));
    }

    }

    public void TakeBack(View view){
        finish();
        startActivity(new Intent(getApplicationContext(),CustomerBranchChoice.class));
    }

   public String generateUserID(){

           String first_email= user.getEmail();
           String bare_email= "Branch ";
           for(int i=0;i<first_email.length();i++){
               if(first_email.charAt(i)!='@'&&first_email.charAt(i)!='.'){
                   bare_email+=first_email.charAt(i);
               }
           }

           return bare_email;
       }


    public boolean validComment(){
        String comment_string = comment.getText().toString().trim();

        if(comment_string.isEmpty()){
            comment.setError("Comment cannot be empty");
            return false;
        }
        return true;
    }

    public boolean validRating(){
        String rating_String = rating.getText().toString().trim();

        if(rating_String.length()>1){
            rating.setError("number should be between 1-5");
            return false;
        }
       try{
           int a=Integer.parseInt(rating_String);
           if(a>=1&&a<=5){
               return true;
           }
           else{
               rating.setError("number should be between 1 and 5");
               return false;
           }

       }
       catch(Exception e){
           rating.setError("Input a number between 1 and 5");
           return false;
       }


    }


}