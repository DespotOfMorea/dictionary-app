import com.gzs.main.DBMethods;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

@PrepareForTest(DBMethods.class)
public class TestMockitoDBMethods extends PowerMockTestCase {

    @DataProvider(name="EqualsAssertionsProvider")
    public Object[][] createConcatenationAssertionSet(){
        return new Object[][]{
                new Object []{"Pukotina", 17},
                new Object []{"Pukotina", 2},
                new Object []{"Cave", 0},
                new Object []{null, 45},
                new Object []{null, 0},
                new Object []{"Fold", 0},
                new Object []{"Rased", "Pukotina", 10},
        };
    }

    @Test (dataProvider="EqualsAssertionsProvider")
    public void testGetTermID(String st, int num) {
        PowerMockito.mockStatic(DBMethods.class);
        when(DBMethods.getTermID("Pukotina")).thenReturn(17);
        when(DBMethods.getTermID("Cave")).thenReturn(0);
        when(DBMethods.getTermID("Fold")).thenReturn(2);
        when(DBMethods.getTermID("Rased")).thenReturn(10);

        assertEquals(DBMethods.getTermID(st),num);
    }
}
