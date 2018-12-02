package com.gzs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Translation extends DatabaseEntity{
    private Term term1ID;
    private Term term2ID;
    private int priority;

    public Translation(int id, Term term1ID, Term term2ID, int priority) {
        super(id);
        this.term1ID = term1ID;
        this.term2ID = term2ID;
        this.priority = priority;
    }

    public Translation(int id, int term1ID, int term2ID, int priority) {
        this.id = id;
        //    this.term1ID = termDao.get(term1ID);
        //    this.term2ID = termDao.get(term2ID);
        this.priority = priority;
    }
}