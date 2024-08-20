package org.fireup.spark.configuration;


import java.io.Serializable;

public class FireUpRunningContext implements Serializable {

    private final boolean isRunningOnDataproc;

    public FireUpRunningContext() {
        this.isRunningOnDataproc = System.getenv("DATAPROC_VERSION") != null;
    }

    public boolean isRunningOnDataproc() {
        return isRunningOnDataproc;
    }

}
