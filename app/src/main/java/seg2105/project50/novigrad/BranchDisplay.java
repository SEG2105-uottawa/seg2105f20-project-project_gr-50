package seg2105.project50.novigrad;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class BranchDisplay extends ArrayAdapter<BranchInfo> {

    private Context context;
    private DatabaseReference database;
    private FirebaseAuth auth;
    private Hours hours;
    private int resource;

    public BranchDisplay(@NonNull Context context, int resource, ArrayList<BranchInfo> list) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String email = getItem(position).getEmail();
        String branchName = generateBranchName(email);
        String address = getItem(position).getAddress();

        Hours hours = getHours(branchName);
        Calendar calendar = Calendar.getInstance();
        int days = calendar.get(Calendar.DAY_OF_WEEK);
        String workH = "";

            /*
        switch (days){
            case 0:
                workH = hours.getSunday();
            case 1:
                workH = hours.getMonday();
            case 2:
                workH = hours.getTuesday();
            case 3:
                workH = hours.getWednesday();
            case 4:
                workH = hours.getThursday();
            case 5:
                workH = hours.getFriday();
            case 6:
                workH = hours.getSaturday();
        }*/

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView branc_name = (TextView)convertView.findViewById(R.id.branch_name_display);
        TextView branc_hours = (TextView)convertView.findViewById(R.id.branch_hours_display);
        TextView branc_address = (TextView)convertView.findViewById(R.id.branch_address_display);

        branc_address.setText(address);
        branc_hours.setText("Hours");
        branc_name.setText(branchName);

        return convertView;
    }

    private Hours getHours (String branch){

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();


        database.child("Branch").child(branch).child("Hours")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot ServiceSnapshot) {
                       // Hours r = ServiceSnapshot.getValue()
                        //for(DataSnapshot dataSnapshotUp : ServiceSnapshot.getChildren()) {
                            hours = ServiceSnapshot.getValue(Hours.class);

                            Log.e("workH", "hours.getFriday()");

                       // }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

        return hours;
    }

    private String generateBranchName(String string_email){
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
