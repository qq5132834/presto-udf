package com.facebook.presto.udf.utils;

import java.io.Serializable;

public class ConfidenceQueryCredibilityResponse implements Serializable {

    private String columnName;

    private int credibility;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getCredibility() {
        return credibility;
    }

    public void setCredibility(int credibility) {
        this.credibility = credibility;
    }
}
