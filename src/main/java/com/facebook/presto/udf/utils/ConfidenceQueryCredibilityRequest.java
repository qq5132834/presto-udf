package com.facebook.presto.udf.utils;

import java.io.Serializable;

public class ConfidenceQueryCredibilityRequest implements Serializable {

    //数据源ID
    private String dsId;

    //维度ID
    private String dimensionId;

    //原始表名
    private String tableName;

    //行ID
    private String rowId;

    //标准属性名
    private String columnName;

    //可信度查询uri
    private String uri;

    public String getDsId() {
        return dsId;
    }

    public void setDsId(String dsId) {
        this.dsId = dsId;
    }

    public String getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(String dimensionId) {
        this.dimensionId = dimensionId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
