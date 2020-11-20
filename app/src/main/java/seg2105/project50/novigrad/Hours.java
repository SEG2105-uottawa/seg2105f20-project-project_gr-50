package seg2105.project50.novigrad;

public class Hours {
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String sunday;

    public Hours(){}
    public Hours(String Monday,String Tuesday, String Wednesday, String Thursday, String Friday, String Saturday, String Sunday){
        monday = Monday;
        tuesday = Tuesday;
        wednesday = Wednesday;
        thursday = Thursday;
        friday = Friday;
        saturday = Saturday;
        sunday = Sunday;

    }

    public String getMonday(){return monday;}
    public String getTuesday(){return tuesday;}
    public String getWednesday(){return wednesday;}
    public String getThursday(){return thursday;}
    public String getFriday(){return friday;}
    public String getSaturday(){return saturday;}
    public String getSunday(){return sunday;}

}
