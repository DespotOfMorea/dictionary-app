import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gzs.daos.DaoFactory;
import com.gzs.daos.TermDao;
import com.gzs.daos.TranslationDao;
import com.gzs.log.CustomInfo;
import com.gzs.main.RestMethods;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class RestMethodsTest {
    private RestMethods restMethods;
    private TermDao termDao;

    @BeforeClass
    private void setUp() {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(DaoFactory.INMEMORY);
        termDao = daoFactory.getTermDao();
        TranslationDao translationDao = daoFactory.getTranslationDao();
        restMethods = new RestMethods(termDao,translationDao);
    }

    @DataProvider(name="EqualsAssertionsProviderForTerm")
    private Object[][] createConcatenationAssertionSetForTerm(){
        return new Object[][]{
                new Object []{"Fault", termDao.getByTerm("Rased")},
                new Object []{"Fold", termDao.getByTerm("Nabor")},
                new Object []{"Folder", new CustomInfo('i',"There is no translation for Folder in dictionary.")},
                new Object []{"Folderer", new CustomInfo('i',"There is no term Folderer in dictionary.")},
                new Object []{"Dyke", termDao.getByTerm("Dajk")},
                new Object []{"Rased", termDao.getByTerm("Fault")},
                new Object []{"Nabor", termDao.getByTerm("Fold")},
                new Object []{"", new CustomInfo('i',"There is no term  in dictionary.")},
        };
    }

    @Test(dataProvider="EqualsAssertionsProviderForTerm")
    public <T> void testGetTermsTranslation(String termName, T expectedTerm) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String expected = mapper.writeValueAsString(expectedTerm);

        assertEquals(expected, restMethods.getTermsTranslation(termName));
    }
}