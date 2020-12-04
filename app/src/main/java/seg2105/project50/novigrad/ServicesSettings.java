package seg2105.project50.novigrad;

/**
 * Hamidou Cisse
 */
class ServicesSettings {

    private static final String BASIC_PRICE = "10";

    private boolean firstname;
    private boolean lastname;
    private boolean dateofbirth;
    private boolean adress ;
    private boolean proofofresidence;
    private boolean proofofstatus;
    private boolean idnumber;
    private boolean active;
    private boolean licensetype;
    private String name;
    private String price;

    private boolean employeeEnable;


    ServicesSettings(){}

    public boolean isLicensetype() {
        return licensetype;
    }

    public void setLicensetype(boolean licensetype) {
        this.licensetype = licensetype;
    }

    public boolean isActive() {
        return active;
    }


    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    ServicesSettings(String name){
        firstname = true;
        lastname = true;
        dateofbirth = false;
        adress = false;
        proofofresidence = false;
        proofofstatus = false;
        idnumber = false;
        active = false;
        licensetype = false;
        this.name = name;
        this.price = BASIC_PRICE;

        employeeEnable = false;
    }

    ServicesSettings(boolean[] selected, String name, String price){
        firstname = selected[0];
        lastname = selected[1];
        dateofbirth = selected[2];
        adress = selected[3];
        proofofresidence = selected[4];
        proofofstatus = selected[5];
        idnumber = selected[6];
        active = selected[7];
        licensetype = selected[8];
        this.name = name;
        this.price = price;

        employeeEnable = false;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public boolean isEmployeeEnable() {
        return employeeEnable;
    }

    public void setEmployeeEnable(boolean employeeEnable) {
        this.employeeEnable = employeeEnable;
    }

    public boolean isFirstname() {
        return firstname;
    }

    public void setFirstname(boolean firstname) {
        this.firstname = firstname;
    }

    public boolean isLastname() {
        return lastname;
    }

    public void setLastname(boolean lastname) {
        this.lastname = lastname;
    }

    public boolean isDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(boolean dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public boolean isAdress() {
        return adress;
    }

    public void setAdress(boolean adress) {
        this.adress = adress;
    }

    public boolean isProofofresidence() {
        return proofofresidence;
    }

    public void setProofofresidence(boolean proofofresidence) {
        this.proofofresidence = proofofresidence;
    }

    public boolean isProofofstatus() {
        return proofofstatus;
    }

    public void setProofofstatus(boolean proofofstatus) {
        this.proofofstatus = proofofstatus;
    }

    public boolean isIdnumber() {
        return idnumber;
    }

    public void setIdnumber(boolean idnumber) {
        this.idnumber = idnumber;
    }
}
