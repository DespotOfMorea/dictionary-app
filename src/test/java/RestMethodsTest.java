import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gzs.daos.TermDao;
import com.gzs.daos.TranslationDao;
import com.gzs.log.CustomInfo;
import com.gzs.main.RestMethods;
import com.gzs.model.Language;
import com.gzs.model.Term;
import com.gzs.model.Translation;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertEquals;

public class RestMethodsTest {

    private RestMethods restMethods;
    @Mock
    private TermDao termDao;
    @Mock
    private TranslationDao translationDao;

    @BeforeClass
    private void setUp() {
        MockitoAnnotations.initMocks(this);
        restMethods = new RestMethods();
    }

    @DataProvider(name="EqualsAssertionsProviderForTerm")
    private Object[][] createConcatenationAssertionSetForTerm(){
        Map<String,Term> terms = getTestTermsMap();
        return new Object[][]{
                new Object []{"Fault", terms.get("Rased")},
                new Object []{"Fold", terms.get("Nabor")},
                new Object []{"Folder", new CustomInfo('i',"There is no term Folder in dictionary.")},
                new Object []{"Dyke", new CustomInfo('i',"There is no translation for Dyke in dictionary.")},
                new Object []{"Rased", terms.get("Fault")},
                new Object []{"Nabor", terms.get("Fold")},
                new Object []{"", new CustomInfo('i',"There is no term  in dictionary.")},
        };
    }

    @Test(dataProvider="EqualsAssertionsProviderForTerm")
    public <T> void testGetTermsTranslation(String termName, T expectedTerm) throws JsonProcessingException {
        Map<String,Term> terms = getTestTermsMap();

        Term term1 = terms.get("Fault");
        Term term2 = terms.get("Fold");
        Term term3 = terms.get("Dyke");
        Term term4 = terms.get("Rased");
        Term term5 = terms.get("Nabor");

        Translation translation1 = new Translation(1,term1, term4, 1);
        Translation translation2 = new Translation(2,term2, term5, 1);
        Translation translation4 = new Translation(3,term4, term1, 1);
        Translation translation5 = new Translation(4,term5, term2, 1);

        restMethods.setTermDao(termDao);
        restMethods.setTranslationDao(translationDao);

        when(termDao.getByTerm(anyString())).thenReturn(new Term());
        when(termDao.getByTerm(term1.getTerm())).thenReturn(term1);
        when(termDao.getByTerm(term2.getTerm())).thenReturn(term2);
        when(termDao.getByTerm(term3.getTerm())).thenReturn(term3);
        when(termDao.getByTerm(term4.getTerm())).thenReturn(term4);
        when(termDao.getByTerm(term5.getTerm())).thenReturn(term5);

        when(translationDao.getByTerm1Id(anyInt())).thenReturn(new Translation());
        when(translationDao.getByTerm1Id(term1.getId())).thenReturn(translation1);
        when(translationDao.getByTerm1Id(term2.getId())).thenReturn(translation2);
        when(translationDao.getByTerm1Id(term4.getId())).thenReturn(translation4);
        when(translationDao.getByTerm1Id(term5.getId())).thenReturn(translation5);

        when(termDao.get(anyInt())).thenReturn(new Term());
        when(termDao.get(term1.getId())).thenReturn(term1);
        when(termDao.get(term2.getId())).thenReturn(term2);
        when(termDao.get(term3.getId())).thenReturn(term3);
        when(termDao.get(term4.getId())).thenReturn(term4);
        when(termDao.get(term5.getId())).thenReturn(term5);

        ObjectMapper mapper = new ObjectMapper();
        String expected = mapper.writeValueAsString(expectedTerm);

        assertEquals(expected, restMethods.getTermsTranslation(termName));
    }

    private Map<String, Term> getTestTermsMap () {
        Language english = new Language(1,"english", "english1", "eng");
        Language serbian = new Language(2,"serbian", "srpski", "srp");

        Term term1 = new Term(1,"Fault", "A fault is...", english);
        Term term2 = new Term(2,"Fold", "The term fold...", english);
        Term term3 = new Term(3,"Dyke", "A dyke...", english);
        Term term4 = new Term(4,"Rased", "Rasedi su...", serbian);
        Term term5 = new Term(5,"Nabor", "Nabor...", serbian);

        Map<String, Term> map = new HashMap<>();
        map.put(term1.getTerm(),term1);
        map.put(term2.getTerm(),term2);
        map.put(term3.getTerm(),term3);
        map.put(term4.getTerm(),term4);
        map.put(term5.getTerm(),term5);

        return map;
    }
}