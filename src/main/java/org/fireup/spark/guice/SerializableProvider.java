package org.fireup.spark.guice;

import com.google.inject.Provider;

import java.io.Serializable;

public interface SerializableProvider<T> extends Provider<T>, Serializable {
}
