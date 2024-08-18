package org.example.guice;

import com.google.inject.Provider;

import java.io.Serializable;

public interface SerializableProvider<T extends Serializable> extends Provider<T>, Serializable {
}
