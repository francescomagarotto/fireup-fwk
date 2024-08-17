package org.example.parser;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

public class BaseCommandLineParser implements CommandLineParser {
    @Override
    public Map<String, String> parse(String[] args) {
        Preconditions.checkArgument(args.length % 2 == 0, "Arguments must be key-values pairs");
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i <= args.length / 2; i = i + 2) {
            if (args[i].startsWith("-")) {
                map.put(args[i].substring(1), args[i + 1]);
            }
        }
        return map;
    }
}
