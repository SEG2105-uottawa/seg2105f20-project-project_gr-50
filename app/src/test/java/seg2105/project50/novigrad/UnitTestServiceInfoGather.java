package seg2105.project50.novigrad;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class UnitTestServiceInfoGather {

    private ServiceInfoGather infoGatherTest;
    private ServicesSettings service;
    @Mock
    Context mMockContext;

    @Before
    public void setUp(){
        service = new ServicesSettings("Service Name");
        service.setIdnumber(true);
        service.setLicensetype(true);
        service.setDateofbirth(true);
        infoGatherTest = new ServiceInfoGather(mMockContext,service);
    }

    @Test
    public void testGenerateCustomerName(){
        String result = infoGatherTest.generateCustomerName("milly@gmail.com");
        assertEquals(result, "millygmailcom");
    }

    @Test
    public void testCheckField(){
        boolean result = infoGatherTest.checkFields("Milly","White","1999-01-01","","","","001002","G2");
        assertTrue(result);
    }
}
