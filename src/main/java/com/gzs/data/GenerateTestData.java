package com.gzs.data;

import com.gzs.daos.LanguageDao;
import com.gzs.daos.TermDao;
import com.gzs.daos.TranslationDao;
import com.gzs.daos.inmemory.LanguageDaoInMemoryImpl;
import com.gzs.daos.inmemory.TermDaoInMemoryImpl;
import com.gzs.daos.inmemory.TranslationDaoInMemoryImpl;
import com.gzs.model.Language;
import com.gzs.model.Term;
import com.gzs.model.Translation;

import java.util.HashMap;
import java.util.Map;

public class GenerateTestData {
    private static volatile GenerateTestData instance;
    private static Map<Integer, Language> languages;
    private static Map<Integer, Term> terms;
    private static Map<Integer, Translation> translations;

    private GenerateTestData() {
        this.languages = new HashMap<>();
        this.terms = new HashMap<>();
        this.translations = new HashMap<>();
//        createTestData();
    }

    public static synchronized GenerateTestData getInstance() {
        if (instance == null) {
            synchronized (GenerateTestData.class) {
                if (instance == null) {
                    instance = new GenerateTestData();
                }
            }
        }
        return instance;
    }

    public static Map<Integer, Language> getLanguages() {
        return languages;
    }
    public static Map<Integer, Term> getTerms() {
        return terms;
    }
    public static Map<Integer, Translation> getTranslations() {
        return translations;
    }

    public static void setLanguages(Map<Integer, Language> languages) {
        GenerateTestData.languages = languages;
    }

    public static void setTerms(Map<Integer, Term> terms) {
        GenerateTestData.terms = terms;
    }

    public static void setTranslations(Map<Integer, Translation> translations) {
        GenerateTestData.translations = translations;
    }

    public static void createTestData() {
        LanguageDao languageDao = new LanguageDaoInMemoryImpl();
        TermDao termDao = new TermDaoInMemoryImpl();
        TranslationDao translationDao = new TranslationDaoInMemoryImpl();

        Language english = new Language(1, "english", "english1", "eng");
        Language serbian = new Language(2, "serbian", "srpski", "srp");
        languageDao.insert(english);
        languageDao.insert(serbian);

        Term term1 = new Term(1, "Fault", "A fault is a fracture, or break, in the Earth's crust (lithosphere). Some faults are active. Here, sections of rock move past each other. This sometimes makes earthquakes.", english);
        Term term2 = new Term(2, "Fold", "The term fold is used in geology when one or a stack of originally flat, level surfaces, such as sedimentary strata, are bent or curved as a result of pressure and high temperature. The basic cause is likely to be some aspect of plate tectonics.", english);
        Term term3 = new Term(3, "Dyke", "A dyke in geology is a type of later vertical rock between older layers of rock. Technically, it is any geologic body which cuts across: flat wall rock structures, such as bedding or massive rock formations, usually igneous in origin.Dikes can therefore be either pushed in between (intrusive) or laid down (sedimentary) in origin.", english);
        Term term4 = new Term(4, "Dike", "A dyke in geology is a type of later vertical rock between older layers of rock. Technically, it is any geologic body which cuts across: flat wall rock structures, such as bedding or massive rock formations, usually igneous in origin.Dikes can therefore be either pushed in between (intrusive) or laid down (sedimentary) in origin.", english);
        Term term5 = new Term(5, "Foliation", "Foliation in geology refers to repetitive layering in metamorphic rocks. Each layer can be as thin as a sheet of paper, or over a meter in thickness.", english);
        Term term6 = new Term(6, "Cleavage", "Cleavage, in structural geology and petrology, describes a type of planar rock feature that develops as a result of deformation and metamorphism. The degree of deformation and metamorphism along with rock type determines the kind of cleavage feature that develops. Generally these structures are formed in fine grained rocks composed of minerals affected by pressure solution.", english);
        Term term7 = new Term(7, "Anticline", "In structural geology, an anticline is a type of fold that is an arch-like shape and has its oldest beds at its core. A typical anticline is convex up in which the hinge or crest is the location where the curvature is greatest, and the limbs are the sides of the fold that dip away from the hinge.", english);
        Term term8 = new Term(8, "Syncline", "In structural geology, a syncline is a fold with younger layers closer to the center of the structure. A synclinorium (plural synclinoriums or synclinoria) is a large syncline with superimposed smaller folds. Synclines are typically a downward fold (synform), termed a synformal syncline (i.e. a trough); but synclines that point upwards can be found when strata have been overturned and folded (an antiformal syncline).", english);
        Term term9 = new Term(9, "Joint", "In geology, a joint is a fracture dividing rock into two sections that moved away from each other. A joint does not involve shear displacement, and forms when tensile stress breaches its threshold. In other kinds of fracturing, like in a fault, the rock is parted by a visible crack that forms a gap in the rock.", english);

        Term term10 = new Term(10, "Rased", "Rasedi su mehanički diskontinuiteti stenske mase, po kojima se odigralo kretanje. Nastaju usled naprezanja u stenskoj masi.", serbian);
        Term term11 = new Term(11, "Nabor", "Nabor je oblik koji nastaje kontinuiranim deformacijama stena u Zemljinoj kori. Nastaju pod dejstvom orogenih kretanja i mogu se uočiti u svim stenama koje imaju slojevitost ili planaran raspored minerala, dakle u sedimentnim, metamorfnim i trakastim ili škriljavim magmatskim stenama.", serbian);
        Term term12 = new Term(12, "Dajk", "Dajk je intruziv, koji diskordantno preseca: planarne stenske strukture, kao što su slojevitost ili folijacija; ili masivne stenske formacije, kao što to čine magmatske intruzije ili dijapiri halita. Stoga, dajkovi mogu imati magmatsko ili sedimentno poreklo.", serbian);
        Term term13 = new Term(13, "Folijacija", "Folijacija je sistem penetrativnih pukotina na stenskoj masi. Obično se javlja u stenama koje su bile izložene kompresiji u toku regionalnog metamorfizma, što je tipično kod orogenih pojaseva. Stene, u kojima se obično javlja folijacija, su argilošist, filit, škriljci i gnajs.", serbian);
        Term term14 = new Term(14, "Klivaž", "Klivaž, u strukturnoj geologiji i petrologiji, označava tip planarne strukture stene, koja nastaje kao rezultat deformacija i metamorfizma. Koji će se tip klivaža razviti u steni zavisi od stepena deformacija i metamorfizma, kao i od tipa stene koja je podvrgnuta promenama. U opštem slučaju, ove strukture se formiraju u finozrnim stenama, koje izgrađuju minerali koji se mogu stapati pod pritiskom.", serbian);
        Term term15 = new Term(15, "Antiklinala", "Antiklinala je naborni oblik koji u jezgru ima najstarije slojeve. Za naborni oblik konveksan naviše upotrebljava se termin antiforma. Antiklinala je obično (sem kada nabor nije prevrnut), konveksna naviše. Ali, za pouzdanu determinaciju karaktera nabornog oblika, moraju se istražiti odnos relativne starosti jedinica koje ga izgrađuju.", serbian);
        Term term16 = new Term(16, "Sinklinala", "Sinklinala je termin strukturne geologije za naborni oblik koji u jezgru ima najmlađe slojeve.", serbian);
        Term term17 = new Term(17, "Pukotina", "Pukotine su mehanički diskontinuiteti stenske mase, po kojima je kretanje toliko malo da se ono može zanemariti u datom veličinskom području posmatranja. Dakle, za razliku od raseda, pukotine su razlomi, po kojima „nije došlo“ do kretanja blokova stenske mase.", serbian);

        Term term18 = new Term(18, "Folder", "...", english);

        termDao.insert(term1);
        termDao.insert(term2);
        termDao.insert(term3);
        termDao.insert(term4);
        termDao.insert(term5);
        termDao.insert(term6);
        termDao.insert(term7);
        termDao.insert(term8);
        termDao.insert(term9);

        termDao.insert(term10);
        termDao.insert(term11);
        termDao.insert(term12);
        termDao.insert(term13);
        termDao.insert(term14);
        termDao.insert(term15);
        termDao.insert(term16);
        termDao.insert(term17);

        termDao.insert(term18);

        translationDao.insert(new Translation(1, term1, term10, 1));
        translationDao.insert(new Translation(2, term2, term11, 1));
        translationDao.insert(new Translation(3, term3, term12, 1));
        translationDao.insert(new Translation(4, term4, term12, 1));
        translationDao.insert(new Translation(5, term5, term13, 1));
        translationDao.insert(new Translation(6, term6, term14, 1));
        translationDao.insert(new Translation(7, term7, term15, 1));
        translationDao.insert(new Translation(8, term8, term16, 1));
        translationDao.insert(new Translation(9, term9, term17, 1));
        translationDao.insert(new Translation(10, term10, term1, 1));
        translationDao.insert(new Translation(11, term11, term2, 1));
        translationDao.insert(new Translation(12, term12, term3, 1));
        translationDao.insert(new Translation(13, term12, term4, 2));
        translationDao.insert(new Translation(14, term13, term5, 1));
        translationDao.insert(new Translation(15, term14, term6, 1));
        translationDao.insert(new Translation(16, term15, term7, 1));
        translationDao.insert(new Translation(17, term16, term8, 1));
        translationDao.insert(new Translation(18, term17, term9, 1));
    }
}
