package seg2105.project50.novigrad;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class UnitTestServicesEdit {
    private ServicesEdit testServiceEdit;

    @Before
    public void setUp(){
     testServiceEdit = new ServicesEdit();
    }

    @Test
    public void testGenerateBranchName(){
        String result = testServiceEdit.generateBranchName("example@email.com");
        assertEquals(result, "Branch exampleemailcom");
    }
}
