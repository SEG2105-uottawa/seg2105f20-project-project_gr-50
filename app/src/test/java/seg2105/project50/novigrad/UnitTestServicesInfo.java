package seg2105.project50.novigrad;

import androidx.recyclerview.widget.ItemTouchUIUtil;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UnitTestServicesInfo {
    private ServicesInfo testService;
    private final String ServiceName = "LicenseTypeService", FirstName = "Milly", LastName = "White", ID = "00010002", License = "G2", DOB = "1999-01-01";
    @Before
    public void setUp(){
        testService = new ServicesInfo();
        testService.setServiceName(ServiceName);
        testService.setFirstname(FirstName);
        testService.setLastname(LastName);
        testService.setIdnumber(ID);
        testService.setLicensetype(License);
        testService.setDateofbirth(DOB);
    }

    @Test
    public void testLicenseTypeService(){
        assertEquals(testService.getServiceName(),ServiceName);
        assertEquals(testService.getFirstname(),FirstName);
        assertEquals(testService.getLastname(),LastName);
        assertEquals(testService.getDateofbirth(),DOB);
        assertEquals(testService.getLicensetype(),License);
        assertEquals(testService.getIdnumber(), ID);
        assertEquals(testService.getAdress(),"");
        assertEquals(testService.getProofofresidence(),"");
        assertEquals(testService.getProofofstatus(),"");
    }
}
