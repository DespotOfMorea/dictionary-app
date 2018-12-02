package com.gzs.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gzs.daos.DaoFactory;
import com.gzs.daos.TermDao;
import com.gzs.daos.TranslationDao;
import com.gzs.log.CustomInfo;
import com.gzs.model.Term;
import com.gzs.model.Translation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class RestMethods {
    private ObjectMapper mapper;
    private TermDao termDao;
    private TranslationDao translationDao;

    public RestMethods() {
        mapper = new ObjectMapper();
        Configuration config = new Configuration();
        DaoFactory daoFactory = DaoFactory.getDAOFactory(config.getDataType());
        termDao = daoFactory.getTermDao();
        translationDao = daoFactory.getTranslationDao();
    }

    @GET
    @Path("/tst")
    @Produces(MediaType.TEXT_PLAIN)
    public String create() {
        return "test success";
    }

    @GET
    @Path("/get")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String getTermsTranslation(@QueryParam("term") String termName) throws JsonProcessingException {
        int termId = termDao.getByTerm(termName).getId();
        if (termId != 0) {
            int translationId = translationDao.getByTerm1Id(termId).getId();
            System.out.println("SISTEMMMMMMMM                      "+translationId);
            if (translationId!=0){


                Translation trans = translationDao.getByTerm1Id(termId);
                System.out.println("SISTEMMMMMMMM                      "+trans);
                Term ttt = trans.getTerm2ID();
                System.out.println("SISTEMMMMMMMM                      "+ttt);
                int id = ttt.getId();
                System.out.println("SISTEMMMMMMMM                      "+id);
                int translatedTermId = translationDao.getByTerm1Id(termId).getTerm2ID().getId();
                Term term = termDao.get(translatedTermId);
                ObjectMapper mapper = new ObjectMapper();
                return mapper.writeValueAsString(term);
            } else {
                CustomInfo infoMsg = new CustomInfo('i',"There is no translation for " + termName + " in dictionary.");
                return mapper.writeValueAsString(infoMsg);
            }
        } else {
            CustomInfo infoMsg = new CustomInfo('i',"There is no term " + termName + " in dictionary.");
            return mapper.writeValueAsString(infoMsg);
        }
    }
}