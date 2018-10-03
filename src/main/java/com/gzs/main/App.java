package com.gzs.main;

import com.gzs.model.Language;
import com.gzs.model.Term;
import com.gzs.model.Translation;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

@Slf4j
public class App {
    public static void main(String[] args) throws Exception {
//        createDataBase();
//        generateDBData();

        log.info("Starting application");
        
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(2222);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                EntryPoint.class.getCanonicalName());

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }

    public static void createDataBase() {
        DBMethods.createDB();
        DBMethods.createTableLanguages();
        DBMethods.createTableTerms();
        DBMethods.createTableTranslations();
    }

    public static void generateDBData() {
        DBMethods.addLanguage(new Language("english", "english", "eng"));
        DBMethods.addLanguage(new Language("serbian", "srpski", "srp"));

        DBMethods.addTerm(new Term("Fault", "A fault is a fracture, or break, in the Earth's crust (lithosphere). Some faults are active. Here, sections of rock move past each other. This sometimes makes earthquakes.", 1));
        DBMethods.addTerm(new Term("Fold", "The term fold is used in geology when one or a stack of originally flat, level surfaces, such as sedimentary strata, are bent or curved as a result of pressure and high temperature. The basic cause is likely to be some aspect of plate tectonics.", 1));
        DBMethods.addTerm(new Term("Dyke", "A dyke in geology is a type of later vertical rock between older layers of rock. Technically, it is any geologic body which cuts across: flat wall rock structures, such as bedding or massive rock formations, usually igneous in origin.Dikes can therefore be either pushed in between (intrusive) or laid down (sedimentary) in origin.", 1));
        DBMethods.addTerm(new Term("Dike", "A dyke in geology is a type of later vertical rock between older layers of rock. Technically, it is any geologic body which cuts across: flat wall rock structures, such as bedding or massive rock formations, usually igneous in origin.Dikes can therefore be either pushed in between (intrusive) or laid down (sedimentary) in origin.", 1));
        DBMethods.addTerm(new Term("Foliation", "Foliation in geology refers to repetitive layering in metamorphic rocks. Each layer can be as thin as a sheet of paper, or over a meter in thickness.", 1));
        DBMethods.addTerm(new Term("Cleavage", "Cleavage, in structural geology and petrology, describes a type of planar rock feature that develops as a result of deformation and metamorphism. The degree of deformation and metamorphism along with rock type determines the kind of cleavage feature that develops. Generally these structures are formed in fine grained rocks composed of minerals affected by pressure solution.", 1));
        DBMethods.addTerm(new Term("Anticline", "In structural geology, an anticline is a type of fold that is an arch-like shape and has its oldest beds at its core. A typical anticline is convex up in which the hinge or crest is the location where the curvature is greatest, and the limbs are the sides of the fold that dip away from the hinge.", 1));
        DBMethods.addTerm(new Term("Syncline", "In structural geology, a syncline is a fold with younger layers closer to the center of the structure. A synclinorium (plural synclinoriums or synclinoria) is a large syncline with superimposed smaller folds. Synclines are typically a downward fold (synform), termed a synformal syncline (i.e. a trough); but synclines that point upwards can be found when strata have been overturned and folded (an antiformal syncline).", 1));
        DBMethods.addTerm(new Term("Joint", "In geology, a joint is a fracture dividing rock into two sections that moved away from each other. A joint does not involve shear displacement, and forms when tensile stress breaches its threshold. In other kinds of fracturing, like in a fault, the rock is parted by a visible crack that forms a gap in the rock.", 1));

        DBMethods.addTerm(new Term("Rased", "Rasedi su mehanički diskontinuiteti stenske mase, po kojima se odigralo kretanje. Nastaju usled naprezanja u stenskoj masi.", 2));
        DBMethods.addTerm(new Term("Nabor", "Nabor je oblik koji nastaje kontinuiranim deformacijama stena u Zemljinoj kori. Nastaju pod dejstvom orogenih kretanja i mogu se uočiti u svim stenama koje imaju slojevitost ili planaran raspored minerala, dakle u sedimentnim, metamorfnim i trakastim ili škriljavim magmatskim stenama.", 2));
        DBMethods.addTerm(new Term("Dajk", "Dajk je intruziv, koji diskordantno preseca: planarne stenske strukture, kao što su slojevitost ili folijacija; ili masivne stenske formacije, kao što to čine magmatske intruzije ili dijapiri halita. Stoga, dajkovi mogu imati magmatsko ili sedimentno poreklo.", 2));
        DBMethods.addTerm(new Term("Folijacija", "Folijacija je sistem penetrativnih pukotina na stenskoj masi. Obično se javlja u stenama koje su bile izložene kompresiji u toku regionalnog metamorfizma, što je tipično kod orogenih pojaseva. Stene, u kojima se obično javlja folijacija, su argilošist, filit, škriljci i gnajs.", 2));
        DBMethods.addTerm(new Term("Klivaž", "Klivaž, u strukturnoj geologiji i petrologiji, označava tip planarne strukture stene, koja nastaje kao rezultat deformacija i metamorfizma. Koji će se tip klivaža razviti u steni zavisi od stepena deformacija i metamorfizma, kao i od tipa stene koja je podvrgnuta promenama. U opštem slučaju, ove strukture se formiraju u finozrnim stenama, koje izgrađuju minerali koji se mogu stapati pod pritiskom.", 2));
        DBMethods.addTerm(new Term("Antiklinala", "Antiklinala je naborni oblik koji u jezgru ima najstarije slojeve. Za naborni oblik konveksan naviše upotrebljava se termin antiforma. Antiklinala je obično (sem kada nabor nije prevrnut), konveksna naviše. Ali, za pouzdanu determinaciju karaktera nabornog oblika, moraju se istražiti odnos relativne starosti jedinica koje ga izgrađuju.", 2));
        DBMethods.addTerm(new Term("Sinklinala", "Sinklinala je termin strukturne geologije za naborni oblik koji u jezgru ima najmlađe slojeve.", 2));
        DBMethods.addTerm(new Term("Pukotina", "Pukotine su mehanički diskontinuiteti stenske mase, po kojima je kretanje toliko malo da se ono može zanemariti u datom veličinskom području posmatranja. Dakle, za razliku od raseda, pukotine su razlomi, po kojima „nije došlo“ do kretanja blokova stenske mase.", 2));

        DBMethods.addTranslation(new Translation(1, 10, 1));
        DBMethods.addTranslation(new Translation(2, 11, 1));
        DBMethods.addTranslation(new Translation(3, 12, 1));
        DBMethods.addTranslation(new Translation(4, 12, 1));//
        DBMethods.addTranslation(new Translation(5, 13, 1));
        DBMethods.addTranslation(new Translation(6, 14, 1));
        DBMethods.addTranslation(new Translation(7, 15, 1));
        DBMethods.addTranslation(new Translation(8, 16, 1));
        DBMethods.addTranslation(new Translation(9, 17, 1));
        DBMethods.addTranslation(new Translation(10, 1, 1));
        DBMethods.addTranslation(new Translation(11, 2, 1));
        DBMethods.addTranslation(new Translation(12, 3, 1));
        DBMethods.addTranslation(new Translation(12, 4, 2));
        DBMethods.addTranslation(new Translation(13, 5, 1));
        DBMethods.addTranslation(new Translation(14, 6, 1));
        DBMethods.addTranslation(new Translation(15, 7, 1));
        DBMethods.addTranslation(new Translation(16, 8, 1));
        DBMethods.addTranslation(new Translation(17, 9, 1));
    }
}
