package seg2105.project50.novigrad;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UnitTestServicesSettings {
    private ServicesSettings activedLicenceServices;
    @Before
    public void setUp()
    {
        activedLicenceServices = new ServicesSettings("Licence");
    }

    @Test
    public void testActiveLicenceServiceSetting(){
        activedLicenceServices.setActive(true);
        activedLicenceServices.setLicensetype(true);
        activedLicenceServices.setFirstname(true);
        activedLicenceServices.setLastname(true);
        assertEquals("Licence",activedLicenceServices.getName());
        assertTrue(activedLicenceServices.isLicensetype());
        assertTrue(activedLicenceServices.isActive());
        assertTrue(activedLicenceServices.isFirstname());
        assertTrue(activedLicenceServices.isLastname());
        assertFalse(activedLicenceServices.isIdnumber());
        assertFalse(activedLicenceServices.isAdress());
    }
}
