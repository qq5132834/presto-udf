package com.facebook.presto.udf.confidence;

import com.facebook.presto.spi.function.Description;
import com.facebook.presto.spi.function.ScalarFunction;
import com.facebook.presto.spi.function.SqlType;
import com.facebook.presto.spi.type.StandardTypes;
import com.facebook.presto.udf.utils.ConfidenceQueryCredibilityRequest;
import com.facebook.presto.udf.utils.UdfHttpClient;
import io.airlift.slice.Slice;
import io.airlift.slice.Slices;

public class F_COL {

    @ScalarFunction("f_col")
    @Description("计算字段可信值")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice f_col(@SqlType(StandardTypes.VARCHAR) Slice dimensionId,
                               @SqlType(StandardTypes.VARCHAR) Slice dsId,
                               @SqlType(StandardTypes.VARCHAR) Slice tableName,
                               @SqlType(StandardTypes.VARCHAR) Slice rowId,
                               @SqlType(StandardTypes.VARCHAR) Slice columnNames
    )
    {
        System.out.println("f_col,dimensionId:" + dimensionId.toStringUtf8()
                + ",dsId:" + dsId.toStringUtf8()
                + ",tableName:" + tableName.toStringUtf8()
                + ",rowId:" + rowId.toStringUtf8()
                + ",columnNames:" + columnNames.toStringUtf8()
        );

        UdfHttpClient udfHttpClient = new UdfHttpClient();

        ConfidenceQueryCredibilityRequest request = new ConfidenceQueryCredibilityRequest();
        request.setDimensionId(dimensionId.toStringUtf8());
        request.setDsId(dsId.toStringUtf8());
        request.setTableName(tableName.toStringUtf8());
        request.setRowId(rowId.toStringUtf8());
        request.setColumnName(columnNames.toStringUtf8());

        int f = udfHttpClient.post(request);

        System.out.println("f_col:" + f);

        return Slices.utf8Slice(String.valueOf(f));
    }

}
