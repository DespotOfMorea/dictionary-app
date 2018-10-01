package com.gzs.main;

import com.gzs.model.Term;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class RestMethods {

    public RestMethods() {}

    @GET
    @Path("/tst")
    @Produces(MediaType.TEXT_PLAIN)
    public String create() {
        return "test success";
    }

    @GET
    @Path("/post")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public String getTerm(@FormParam("term") String termName) {

        int term1ID = DBMethods.getTermID(termName);
        if (term1ID != 0) {
            int term2ID = DBMethods.getTranslatedTermID(term1ID);
            if (term2ID != 0) {
                Term term = DBMethods.getTermByID(term2ID);
                return "<h2>Hello, there is translation for " + termName + " in dictionary.</h2>" +
                        "<b>" + term.getTerm() + "</b></br>" +
                        "<i>" + term.getMeaning() + "</i>";
            } else {
                return "<h2>Hello, there is no translation for " + termName + " in dictionary.</h2>";
            }
        } else {
            return "<h2>Hello, there is no term " + termName + " in dictionary.</h2>";
        }
    }

}