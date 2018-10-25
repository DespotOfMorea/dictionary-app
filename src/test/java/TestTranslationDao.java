import com.gzs.daos.TranslationDao;
import com.gzs.model.Term;
import com.gzs.model.Translation;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class TestTranslationDao {

    @Mock
    private TranslationDao dao;

    private List<Term> terms = new ArrayList<>();
    private List<Translation> translations = new ArrayList<>();

    @BeforeClass
    private void setUp() {
        MockitoAnnotations.initMocks(this);
        terms=getTermsList();
        translations=getTranslationsList();
    }

    @DataProvider(name="EqualsAssertionsProviderForID")
    private Object[][] createConcatenationAssertionSetForID(){
        return new Object[][]{
                new Object []{0, new Translation()},
                new Object []{45, new Translation()},
                new Object []{1, translations.get(0)},
                new Object []{2, translations.get(1)},
                new Object []{3, translations.get(2)},
                new Object []{4, translations.get(3)},
                new Object []{10, translations.get(4)},
                new Object []{11, translations.get(5)},
                new Object []{12, translations.get(6)},
        };
    }

    @Test(dataProvider="EqualsAssertionsProviderForID")
    public void testGetByTerm1Id(int id, Translation translation) {
        when(dao.getByTerm1Id(anyInt())).thenReturn(new Translation());
        when(dao.getByTerm1Id(0)).thenReturn(new Translation());
        when(dao.getByTerm1Id(terms.get(0).getId())).thenReturn(translations.get(0));
        when(dao.getByTerm1Id(terms.get(1).getId())).thenReturn(translations.get(1));
        when(dao.getByTerm1Id(terms.get(2).getId())).thenReturn(translations.get(2));
        when(dao.getByTerm1Id(terms.get(3).getId())).thenReturn(translations.get(3));
        when(dao.getByTerm1Id(terms.get(4).getId())).thenReturn(translations.get(4));
        when(dao.getByTerm1Id(terms.get(5).getId())).thenReturn(translations.get(5));
        when(dao.getByTerm1Id(terms.get(6).getId())).thenReturn(translations.get(6));

        assertEquals(dao.getByTerm1Id(id), translation);
    }

    private List<Term> getTermsList() {
        Term term1 = new Term(1,"Fault","...",1);
        Term term2 = new Term(2,"Fold","...",1);
        Term term3 = new Term(3,"Dyke","...",1);
        Term term4 = new Term(4,"Dike","...",1);
        Term term5 = new Term(10,"Rased","...",2);
        Term term6 = new Term(11,"Nabor","...",2);
        Term term7 = new Term(12,"Dajk","...",2);

        terms.add(term1);
        terms.add(term2);
        terms.add(term3);
        terms.add(term4);
        terms.add(term5);
        terms.add(term6);
        terms.add(term7);
        return terms;
    }

    private List<Translation> getTranslationsList() {

        Translation t1 = new Translation(1,terms.get(0),terms.get(4),1);
        Translation t2 = new Translation(2,terms.get(1),terms.get(5),1);
        Translation t3 = new Translation(3,terms.get(2),terms.get(6),1);
        Translation t4 = new Translation(4,terms.get(3),terms.get(6),1);
        Translation t5 = new Translation(5,terms.get(4),terms.get(1),1);
        Translation t6 = new Translation(6,terms.get(5),terms.get(2),1);
        Translation t7 = new Translation(7,terms.get(6),terms.get(3),1);
        Translation t8 = new Translation(8,terms.get(6),terms.get(4),2);

        translations.add(t1);
        translations.add(t2);
        translations.add(t3);
        translations.add(t4);
        translations.add(t5);
        translations.add(t6);
        translations.add(t7);
        translations.add(t8);
        return translations;
    }
}
