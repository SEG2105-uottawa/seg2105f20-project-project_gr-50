package seg2105.project50.novigrad;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UnitTestRate {
    private Rate testRate;

    @Before
    public void setUp(){
        testRate = new Rate("Really nice", "5");
    }

    @Test
    public void testComment(){
        final String comment = "Really nice";
        assertEquals(comment, testRate.getComment());
    }

    @Test
    public void testRating(){
        final String rating = "5";
        assertEquals(rating, testRate.getRating());
    }
}
