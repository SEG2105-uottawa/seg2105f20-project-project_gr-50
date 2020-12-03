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

public class ServiceDisplay extends ArrayAdapter<ServicesSettings> {

    private Context context;
    private DatabaseReference database;
    private FirebaseAuth auth;
    private Hours hours;
    private int resource;

    public ServiceDisplay(@NonNull Context context, int resource, ArrayList<ServicesSettings> list) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        boolean state = getItem(position).isActive();
        String stateService = "";

        if(state){
            stateService = "Activated";
        }else{
            stateService = "Deactivated";
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView nameService = (TextView)convertView.findViewById(R.id.textView9);
        TextView stateView = (TextView)convertView.findViewById(R.id.textView10);

        nameService.setText(name);
        stateView.setText(stateService);

        return convertView;
    }

}
