package seg2105.project50.novigrad;

public class BranchInfo {
    private String address;
    private String number;

    private String email;
    private String  password;
    private String role;

    private String theName;

    //If i were smart I would add setters to make my life easier later but NoooOoOooO
    BranchInfo(){}

    BranchInfo(String address, String number, String name, String email, String password, String role){
        this.address = address;
        this.number = number;
        theName = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public String getAddress(){return address;}
    public String getNumber(){return number;}
    public String getEmail(){return email;}
    //public  String getmyEmpName(){return EmpName;} // had to use a distinct method name here. There is already some method getName used somewhere else.
    public  String getPassword(){return  password;}
    public String getRole(){return role;}
    public String getTheName(){
        return theName;
    }
}
