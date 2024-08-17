package org.example.configuration;


public class FireUpRunningContext {

    private final boolean isRunningOnDataproc;

    public FireUpRunningContext() {
        this.isRunningOnDataproc = System.getenv("DATAPROC_VERSION") != null;
    }

    public boolean isRunningOnDataproc() {
        return isRunningOnDataproc;
    }

}
