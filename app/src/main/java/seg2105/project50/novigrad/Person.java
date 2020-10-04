package seg2105.project50.novigrad;

public class Person {
    private String name;
    private String role;
    private String email;
    private String password;

    Person(String name, String role, String email, String password){
        this.name = name;
        this.role = role;
        this.email = email;
        this.password = password;
    }
    public String getName(){
        return name;
    }
    public String getRole(){
        return role;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
}
