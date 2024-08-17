package org.example.parser;

import java.util.Map;

public interface CommandLineParser {
    Map<String, String> parse(String[] args);
}
