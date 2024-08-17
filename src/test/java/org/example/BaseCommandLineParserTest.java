package org.example;

import org.example.parser.BaseCommandLineParser;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BaseCommandLineParserTest {

    @Test
    void baseCommandLineParser_arguments() {
        BaseCommandLineParser parser = new BaseCommandLineParser();
        Map<String, String> parse = parser.parse(new String[]{"-connection", "postgres", "-freeSpace", "true"});
        assertEquals("postgres", parse.get("connection"));
        assertEquals("true", parse.get("freeSpace"));
    }

    @Test
    void baseCommandLineParser_mismatch_arguments() {
        BaseCommandLineParser parser = new BaseCommandLineParser();
        assertThrows(IllegalArgumentException.class, () -> parser.parse(new String[]{"-connection", "postgres", "-freeSpace"}));
    }
}