package seg2105.project50.novigrad;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UnitTestEmployee_welcome_page {
    private Employee_welcome_page testWelcomePage;

    @Before
    public void setUp(){
        testWelcomePage = new Employee_welcome_page();
    }

    @Test
    public void testValidNumber(){
        boolean result = testWelcomePage.validNumber("001002003");
        assertTrue(result);
    }

    @Test
    public void testValidAddress(){
        boolean result = testWelcomePage.validAddress("Novigrad 01");
        assertTrue(result);
    }
}
