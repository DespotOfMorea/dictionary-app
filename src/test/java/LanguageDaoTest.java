import com.gzs.daos.DaoFactory;
import com.gzs.daos.LanguageDao;
import com.gzs.model.Language;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class LanguageDaoTest {

    @Test
    public void multipleThreadInsertionTest() throws InterruptedException {

        final int NUMBER_OF_THREADS = 10;
        final int NUMBER_OF_LANGUAGES = 1_000;

        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            final int threadId = i;
            executorService.execute(() -> generateLanguages(NUMBER_OF_LANGUAGES, threadId, threadId * NUMBER_OF_LANGUAGES));
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        DaoFactory daoFactory = DaoFactory.getDAOFactory(DaoFactory.INMEMORY);
        LanguageDao languageDao = daoFactory.getLanguageDao();

        log.info("Number of languages entered: {}", languageDao.getAll().size());

        Assert.assertEquals(languageDao.getAll().size(), NUMBER_OF_LANGUAGES * NUMBER_OF_THREADS);
    }

    private void generateLanguages(int numberOfLanguages, int threadId, int startingId) {
        DaoFactory daoFactory = DaoFactory.getDAOFactory(DaoFactory.INMEMORY);
        LanguageDao languageDao = daoFactory.getLanguageDao();

        log.info("Thread {}, startingId: {}", threadId, startingId);

        System.out.println(languageDao);

        for (int i = startingId; i < startingId + numberOfLanguages; i++) {
            Language language = new Language("english" + i, "english" + i, "iso + " + i);
            language.setId(i);

            languageDao.insert(language);
        }

        log.info("Thread {} finished. Number of languages: {}", threadId, languageDao.getAll().size());
    }
}
