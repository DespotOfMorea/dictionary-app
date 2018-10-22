package com.gzs.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gzs.model.Term;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class RestMethods {

    private ObjectMapper mapper;
    private Term term;

    public RestMethods() {
        mapper = new ObjectMapper();
        term = null;
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
        int term1ID = DBMethods.getTermID(termName);
        if (term1ID != 0) {
            int term2ID = DBMethods.getTranslatedTermID(term1ID);
            if (term2ID != 0) {
                term = DBMethods.getTermByID(term2ID);
                return mapper.writeValueAsString(term);
            } else {
                term = new Term();
                term.setTerm("There is no translation for " + termName + " in dictionary.");
                return mapper.writeValueAsString(term);
            }
        } else {
            term = new Term();
            term.setTerm("There is no term " + termName + " in dictionary.");
            return mapper.writeValueAsString(term);
        }
    }

}