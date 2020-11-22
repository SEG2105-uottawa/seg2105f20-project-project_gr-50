package seg2105.project50.novigrad;

import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UnitTestHour {
    private Hours testHolidayHours;

    @Before
    public void setUp(){
        testHolidayHours = new Hours("10:00am - 4:00pm","10:00am - 4:00pm","Closed","10:00am - 8:00pm","10:00am - 8:00pm","Closed","Closed");
    }

    @Test
    public void testMondayOpenHour(){
        final String MondayOpenHour = "10:00am - 4:00pm";
        assertEquals(testHolidayHours.getMonday(),MondayOpenHour);
    }

    @Test
    public void testTuesdayOpenHour(){
        final String TuesdayOpenHour = "10:00am - 4:00pm";
        assertEquals(testHolidayHours.getTuesday(),TuesdayOpenHour);
    }

    @Test
    public void testWednesdayOpenHour(){
        final String WednesdayOpenHour = "Closed";
        assertEquals(testHolidayHours.getWednesday(),WednesdayOpenHour);
    }

    @Test
    public void testThursdayOpenHour(){
        final String ThursdayOpenHour = "10:00am - 8:00pm";
        assertEquals(testHolidayHours.getThursday(),ThursdayOpenHour);
    }

    @Test
    public void testFridayOpenHour(){
        final String FridayOpenHour = "10:00am - 8:00pm";
        assertEquals(testHolidayHours.getFriday(),FridayOpenHour);
    }

    @Test
    public void testSaturdayOpenHour(){
        final String SaturdayOpenHour = "Closed";
        assertEquals(testHolidayHours.getSaturday(),SaturdayOpenHour);
    }

    @Test
    public void testSundayOpenHour(){
        final String SundayOpenHour = "Closed";
        assertEquals(testHolidayHours.getSunday(),SundayOpenHour);
    }
}
