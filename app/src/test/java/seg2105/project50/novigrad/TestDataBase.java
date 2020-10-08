package seg2105.project50.novigrad;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestDataBase {
    @Test
    public void canWriteToDataBase(){
    Person mcitizen = new Person("milly","employee","milly@gmail.com","milly12");
        assertEquals("employee", mcitizen.getRole());
        assertEquals("milly@gmail.com", mcitizen.getEmail());
        assertEquals("milly12", mcitizen.getPassword());

    }
}
