package com.gzs.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gzs.daos.TermDao;
import com.gzs.daos.TermDaoImpl;
import com.gzs.daos.TranslationDao;
import com.gzs.daos.TranslationDaoImpl;
import com.gzs.model.Term;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class RestMethods {
    private TermDao termDao;
    private TranslationDao translationDao;

    public RestMethods() {
        termDao = new TermDaoImpl();
        translationDao = new TranslationDaoImpl();
    }

    @GET
    @Path("/tst")
    @Produces(MediaType.TEXT_PLAIN)
    public String create() {
        return "test success";
    }

    @GET
    @Path("/post")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String getTerm(@QueryParam("term") String termName) throws JsonProcessingException {


        int term1ID = termDao.getByTerm(termName).getId();
        if (term1ID != 0) {
            int term2ID = translationDao.getByTerm1Id(term1ID).getTerm2ID().getId();
            if (term2ID != 0) {
                Term term = termDao.getById(term2ID);
                ObjectMapper mapper = new ObjectMapper();
                return mapper.writeValueAsString(term);
            } else {
                return "<h2>Hello, there is no translation for " + termName + " in dictionary.</h2>";
            }
        } else {
            return "<h2>Hello, there is no term " + termName + " in dictionary.</h2>";
        }
    }

}