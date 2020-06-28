package com.facebook.presto.udf;

import java.util.Set;
import com.facebook.presto.spi.Plugin;
import com.facebook.presto.udf.confidence.F_COL;
import com.facebook.presto.udf.confidence.F_EXPR;
import com.facebook.presto.udf.confidence.F_X;
import com.google.common.collect.ImmutableSet;


public class UDF_Plugin implements Plugin{

    @Override
    public Set<Class<?>> getFunctions(){

        return ImmutableSet.<Class<?>>builder()
                .add(F_X.class)
                .add(F_EXPR.class)
                .add(F_COL.class)
                .build();
    }
}