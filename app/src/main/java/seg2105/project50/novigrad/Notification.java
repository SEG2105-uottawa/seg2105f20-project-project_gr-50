package seg2105.project50.novigrad;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Notification {
    private String notification;
    private DateFormat datefor = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    private String date;

    public Notification(String notification, Date date){
        this.notification = notification;
        this.date = datefor.format(date);
    }

    public Notification(){}

    public String getNotification() {
        return notification;
    }

    public String getDate() {
        return date;
    }
}
