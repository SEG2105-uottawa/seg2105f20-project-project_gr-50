package seg2105.project50.novigrad;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
    private ArrayList<Hours> hoursSet;

    //private ArrayList<String> list = new ArrayList<>();
    String workHours;

    private String temp_hours = "Loading...";

    private Hours hours;

    private int resource;

    public BranchDisplay(@NonNull Context context, int resource, ArrayList<BranchInfo> list, ArrayList<Hours> hoursSet) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.hoursSet = hoursSet;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String email = getItem(position).getEmail();
        String branchName = generateBranchName(email);
        String address = getItem(position).getAddress();

        //hours = new Hours("mond","tue","wds","thy","fr","sat","sun");


        String workH = getWorkHours(hoursSet.get(position));

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView branc_name = (TextView)convertView.findViewById(R.id.branch_name_display);
        TextView branc_hours = (TextView)convertView.findViewById(R.id.branch_hours_display);
        TextView branc_address = (TextView)convertView.findViewById(R.id.branch_address_display);

        branc_address.setText(address);
        branc_hours.setText(workH);
        branc_name.setText(branchName);

        return convertView;
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

    private String getWorkHours(Hours hr){
        Calendar calendar = Calendar.getInstance();
        int days = calendar.get(Calendar.DAY_OF_WEEK);
        String wor = "";
        switch (days) {
            case Calendar.SUNDAY:
                wor = hr.getSunday();
                break;
            case Calendar.MONDAY:
                wor = hr.getMonday();
                break;
            case Calendar.TUESDAY:
                wor = hr.getTuesday();
                break;
            case Calendar.WEDNESDAY:
                wor = hr.getWednesday();
                break;
            case Calendar.THURSDAY:
                wor = hr.getThursday();
                break;
            case Calendar.FRIDAY:
                wor = hr.getFriday();
                break;
            case Calendar.SATURDAY:
                wor = hr.getSaturday();
                break;
        }
        return wor;
    }
}
