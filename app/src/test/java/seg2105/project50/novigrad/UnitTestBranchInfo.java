package seg2105.project50.novigrad;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UnitTestBranchInfo {
    private BranchInfo testBranch;
    private final String Address = "50 novigrad xxxx", Number = "000001", Name = "Branch No.1", Email = "group50@uottawa.ca", PW = "001002", Role = "employer";

    @Before
    public void setUp(){
        testBranch = new BranchInfo(Address,Number,Name,Email,PW,Role);
    }

    @Test
    public void infoTest(){
        assertEquals(testBranch.getAddress(),Address);
        assertEquals(testBranch.getEmail(),Email);
        assertEquals(testBranch.getNumber(),Number);
        assertEquals(testBranch.getPassword(),PW);
        assertEquals(testBranch.getRole(),Role);
        assertEquals(testBranch.getTheName(),Name);
    }
}
