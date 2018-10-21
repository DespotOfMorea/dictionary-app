import com.gzs.main.DBMethods;
import org.testng.annotations.*;
import org.testng.Assert;

public class TestDBMethods {

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
    public void testGetTermID(String st, int num)
    {
        Assert.assertEquals(DBMethods.getTermID(st),num);
    }
}