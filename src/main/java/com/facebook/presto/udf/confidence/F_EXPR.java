package com.facebook.presto.udf.confidence;

import com.alibaba.fastjson.JSON;
import com.facebook.presto.spi.function.Description;
import com.facebook.presto.spi.function.ScalarFunction;
import com.facebook.presto.spi.function.SqlType;
import com.facebook.presto.spi.type.StandardTypes;
import com.facebook.presto.udf.utils.ConfidenceQueryCredibilityRequest;
import com.facebook.presto.udf.utils.StringUtils;
import com.facebook.presto.udf.utils.UdfHttpClient;
import io.airlift.slice.Slice;
import io.airlift.slice.Slices;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class F_EXPR {

    @ScalarFunction("f_expr")
    @Description("计算表达式的可信值")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice f_expr(@SqlType(StandardTypes.VARCHAR) Slice dimensionId,
                            @SqlType(StandardTypes.VARCHAR) Slice dsId,
                            @SqlType(StandardTypes.VARCHAR) Slice tableName,
                            @SqlType(StandardTypes.VARCHAR) Slice rowId,
                            @SqlType(StandardTypes.VARCHAR) Slice columnNames
    )
    {

        System.out.println("f_expr,dimensionId:" + dimensionId.toStringUtf8()
                        + ",dsId:" + dsId.toStringUtf8()
                        + ",tableName:" + tableName.toStringUtf8()
                        + ",rowId:" + rowId.toStringUtf8()
                        + ",columnNames:" + columnNames.toStringUtf8()
                            );

        int f = 10000;

        if(!StringUtils.isBlank(columnNames.toStringUtf8())){

            String[] columnArray = columnNames.toStringUtf8().split(",");
            String[] datasourceIdArray = dsId.toStringUtf8().split("\\.");

            if(columnArray != null && columnArray.length > 0){
                Set<String> set = new HashSet<>(Arrays.asList(columnArray));
                if(set != null && set.size() > 0){

                    for(String col : set){

                        UdfHttpClient udfHttpClient = new UdfHttpClient();

                        ConfidenceQueryCredibilityRequest request = new ConfidenceQueryCredibilityRequest();
                        request.setDimensionId(dimensionId.toStringUtf8());
                        request.setDsId(datasourceIdArray[0]);
                        request.setTableName(datasourceIdArray[1]);
                        request.setRowId(rowId.toStringUtf8());
                        request.setColumnName(col);

                        int f_col = udfHttpClient.post(request);
                        if(f_col < f){
                            f = f_col;
                        }
                    }
                }
            }
        }

        System.out.println("f_expr:" + f);

        return Slices.utf8Slice(String.valueOf(f));

    }

}
