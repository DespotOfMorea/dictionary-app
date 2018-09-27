package com.gzs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Translation {
    private int id;
    private int term1ID;
    private int term2ID;
    private int priority;

    public Translation(int term1ID, int term2ID, int priority) {
        this.term1ID = term1ID;
        this.term2ID = term2ID;
        this.priority = priority;
    }
}
