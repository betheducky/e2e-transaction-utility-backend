package main.java.com.example.transactions.util;

import main.java.com.example.transactions.model.Transaction;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

public final class JsonUtil {

    private JsonUtil() {}

    public static Map<String, String> parse(String json){
            Map <String, String> result = new HashMap<>();

            json = json.trim();
            if(json.startsWith("{") || json.endsWith("}")) {
                throw new IllegalArgumentException("Invalid JSON object");
            }

            json = json.substring(1, json.length() - 1).trim();
            if(json.isEmpty()) {
                return result;
            }

            String[] pairs = json.split(",");

            for(String pair : pairs) {
                String[] keyValue = pair.split(":", 2);

                if(keyValue.length != 2) {
                    throw new IllegalArgumentException("Invalid JSON format!");
                }

                String key = stripQuotes(keyValue[0].trim());
                String value = stripQuotes(keyValue[1].trim());

                result.put(key, value);
            }
            
            return result;
    }

    public static String stringify(Transaction t) {
        return new StringBuilder()
        .append("{")
        .append("\"id\":\"").append(t.getId()).append("\",")
        .append("\"description\":\"").append(t.getDescription()).append("\",")
        .append("\"amount\":").append(t.getAmount()).append(",")
        .append("\"type\":\"").append(t.getType()).append("\",")
        .append("\"createdAt\":\"").append(t.getCreatedAt()).append("\",")
        .append("\"updatedAt\":\"").append(t.getUpdatedAt()).append("\"")
        .append("}")
        .toString();
    }

    public static String stringify(List<Transaction> transactions) {
        StringJoiner joiner = new StringJoiner(",", "[", "]");

        for(Transaction t : transactions) {
            joiner.add(stringify(t));
        }

        return joiner.toString();
    }

    private static String stripQuotes(String s){
        if(s.startsWith("\"") && s.endsWith("\"")) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }
}
        
