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

public class NotificationDisplay extends ArrayAdapter<Notification> {

    private Context context;
    private DatabaseReference database;
    private FirebaseAuth auth;
    private Hours hours;
    private int resource;

    public NotificationDisplay(@NonNull Context context, int resource, ArrayList<Notification> list) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String notif = getItem(position).getNotification();
        String date = getItem(position).getDate();


        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView text_notif = (TextView)convertView.findViewById(R.id.notif_text);
        TextView date_notif = (TextView)convertView.findViewById(R.id.notif_date);

        text_notif.setText(notif);
        date_notif.setText(date);

        return convertView;
    }

}
