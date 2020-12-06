package seg2105.project50.novigrad;

public class Rate {
    private String comment="empty";
    private String rating="empty";

    public Rate(){}
    public Rate(String comment, String rating){
        this.comment=comment;
        this.rating=rating;
    }

    public String getComment() {
        return comment;
    }

    public String getRating() {
        return rating;
    }
}
