package com.gzs.model;

import com.gzs.daos.TermDao;
import com.gzs.daos.TermDaoImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Translation {
    private int id;
    private Term term1ID;
    private Term term2ID;
    private int priority;

    public Translation(Term term1ID, Term term2ID, int priority) {
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
