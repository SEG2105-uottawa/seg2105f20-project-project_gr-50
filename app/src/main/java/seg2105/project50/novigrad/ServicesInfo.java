package seg2105.project50.novigrad;

/**
 * Hamidou Cisse
 */
class ServicesInfo {

    private String firstname ;
    private String lastname;
    private String dateofbirth;
    private String adress ;
    private String proofofresidence;
    private String proofofstatus;
    private String idnumber;
    private String licensetype;

    private String serviceName;


    public String getLicensetype() {
        return licensetype;
    }

    public void setLicensetype(String licensetype) {
        this.licensetype = licensetype;
    }

    ServicesInfo(){
        firstname = "";
        lastname = "";
        dateofbirth = "";
        adress = "";
        proofofresidence = "";
        proofofstatus = "";
        idnumber = "";
        licensetype = "";

        serviceName = "";
    }

    ServicesInfo(String[] info, String serviceName){
        firstname = info[0];
        lastname = info[1];
        dateofbirth = info[2];
        adress = info[3];
        proofofresidence = info[4];
        proofofstatus = info[5];
        idnumber = info[6];
        licensetype = info[7];

        this.serviceName = serviceName;

    }

    public String getServiceName(){
        return serviceName;
    }
    public void setServiceName(String serviceName){
        this.serviceName = serviceName;
    }
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getProofofresidence() {
        return proofofresidence;
    }

    public void setProofofresidence(String proofofresidence) {
        this.proofofresidence = proofofresidence;
    }

    public String getProofofstatus() {
        return proofofstatus;
    }

    public void setProofofstatus(String proofofstatus) {
        this.proofofstatus = proofofstatus;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }
}
