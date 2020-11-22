package seg2105.project50.novigrad;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnitTestPrivate_approve_requests {
    private Private_approve_requests testPrivate_approve_requests;

    @Before
    public void setUp(){
        testPrivate_approve_requests = new Private_approve_requests();
    }

    @Test
    public void testGenerateBranchName(){
        String result = testPrivate_approve_requests.generateBranchName("example@email.com");
        assertEquals(result, "Branch exampleemailcom");
    }
}
