package seg2105.project50.novigrad;

import android.content.Context;
import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UnitTestSignUpInput {
    private SignUp signUpTest;


    @Before
    public void setUp() {
        signUpTest = new SignUp();
    }
    @Test
    public void testNameInput(){

        boolean result = signUpTest.validateName("validName");
        assertEquals(true, result);
    }
    @Test
    public void testPwInput(){
        boolean result = signUpTest.validatePassword("validPassword");
        assertEquals(true, result);
    }

    @Test
    public void testEmailInput(){
        boolean result = signUpTest.validEmail("validEmail@Input.com");
        assertEquals(true, result);
    }

}
