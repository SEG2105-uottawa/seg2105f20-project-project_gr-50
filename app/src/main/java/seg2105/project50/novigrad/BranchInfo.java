package seg2105.project50.novigrad;

public class BranchInfo {
    private String address;
    private String number;

    BranchInfo(String address, String number){
        this.address = address;
        this.number = number;
    }
    public String getAddress(){return address;}
    public String getNumber(){return number;}
}
