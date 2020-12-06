package seg2105.project50.novigrad;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UnitTestSend_rating {
    private Send_rating sendRatingTest;

    @Before
    public void setUp(){
        sendRatingTest = new Send_rating();
    }

    @Test
    public void testGenerateUserID(){
        String outputEmail = sendRatingTest.generateUserID("testEmail@uottawa.ca");
        String expectedOutput = "Branch testEmailuottawaca";
        assertEquals(expectedOutput, outputEmail);
    }

    @Test
    public void testValidComment(){
        String comment = "non-empty comment";
        assertTrue(sendRatingTest.validComment(comment));
    }

    @Test
    public void testValidRating(){
        String rating = "4";
        assertTrue(sendRatingTest.validRating(rating));
    }
}
