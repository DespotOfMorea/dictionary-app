package com.gzs.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gzs.log.CustomInfo;
import com.gzs.model.Term;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class RestMethods {

    private ObjectMapper mapper;

    public RestMethods() {
        mapper = new ObjectMapper();
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
    public String getTerm(@QueryParam("term") String termName) throws JsonProcessingException {
        int term1ID = DBMethods.getTermID(termName);
        if (term1ID != 0) {
            int term2ID = DBMethods.getTranslatedTermID(term1ID);
            if (term2ID != 0) {
                Term term = DBMethods.getTermByID(term2ID);
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