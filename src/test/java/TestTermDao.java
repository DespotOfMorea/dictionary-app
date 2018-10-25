import com.gzs.daos.TermDao;
import com.gzs.model.Term;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;


public class TestTermDao {

    @Mock
    private TermDao dao;

    private List<Term> terms = new ArrayList<>();


    @BeforeClass
    private void setUp() {
        MockitoAnnotations.initMocks(this);
        terms=getTermsList();
    }

    @DataProvider(name="EqualsAssertionsProviderForID")
    private Object[][] createConcatenationAssertionSetForID(){
        return new Object[][]{
                new Object []{17, terms.get(2)},
                new Object []{10, terms.get(1)},
                new Object []{0, new Term()},
                new Object []{45, new Term()},
                new Object []{2, terms.get(0)},
        };
    }

    @DataProvider(name="EqualsAssertionsProviderForTerm")
    private Object[][] createConcatenationAssertionSetForTerm(){
        return new Object[][]{
                new Object []{"Pukotina", terms.get(2)},
                new Object []{"Fold", terms.get(0)},
                new Object []{"Cave", new Term()},
                new Object []{null, new Term()},
                new Object []{"Rased", terms.get(1)},
        };
    }


    @Test(dataProvider="EqualsAssertionsProviderForID")
    public void testGetById(int num, Term term) {
        when(dao.getById(anyInt())).thenReturn(new Term());
        when(dao.getById(terms.get(0).getId())).thenReturn(terms.get(0));
        when(dao.getById(terms.get(1).getId())).thenReturn(terms.get(1));
        when(dao.getById(terms.get(2).getId())).thenReturn(terms.get(2));

        assertEquals(dao.getById(num), term);
    }

    @Test(dataProvider="EqualsAssertionsProviderForTerm")
    public void testGetByTerm(String st, Term term) {
        when(dao.getByTerm(anyString())).thenReturn(new Term());
        when(dao.getByTerm(null)).thenReturn(new Term());
        when(dao.getByTerm(terms.get(0).getTerm())).thenReturn(terms.get(0));
        when(dao.getByTerm(terms.get(1).getTerm())).thenReturn(terms.get(1));
        when(dao.getByTerm(terms.get(2).getTerm())).thenReturn(terms.get(2));

        assertEquals(dao.getByTerm(st), term);
    }

    private List<Term> getTermsList() {
        Term t1 = new Term(2,"Fold","...",1);
        Term t2 = new Term(10,"Rased","...",2);
        Term t3 = new Term(17,"Pukotina","...",2);

        terms.add(t1);
        terms.add(t2);
        terms.add(t3);
        return terms;
    }
}
