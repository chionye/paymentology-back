package com.example.demo.service;


import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Transactions {

    private static final List<String> contentTypes = List.of("text/csv", "application/vnd.ms-excel");


    public HashMap<String, List<Map<String, String>>> generateTransactionRecordReport(List<Map<String, String>> row, List<Map<String, String>> row1, String name, String comparisonKey) {
        HashMap<String, List<Map<String, String>>> result = new HashMap<>();
        List<Map<String, String>> closeMatch = new ArrayList<>();
        List<Map<String, String>> matchedAgainst = new ArrayList<>();
        List<Map<String, String>> noMatch = new ArrayList<>();
        for (int i = 0; i < row.size(); i++) {
            for (int j = 0; j < row1.size(); j++) {
                Map<String, String> matches;
                if (row.get(i).get(comparisonKey).replace(" ", "").equals(row1.get(j).get(comparisonKey))) {
                    break;
                } else if (row.size() - 1 == j && !(row1.get(i).get(comparisonKey).equals(row.get(j).get(comparisonKey)))) {
                    matches = row1.get(i);
                    noMatch.add(matches);
                } else if (row.get(i).get(comparisonKey).regionMatches(0, row1.get(j).get(comparisonKey), 0, (row.get(i).get(comparisonKey).length()/2) + 2)) {
                    System.out.print(row1.get(j).get(comparisonKey));
                    matches = row.get(i);
                    Map<String, String> closeMatchedTo = new HashMap<>(row1.get(j));
                    closeMatch.add(matches);
                    matchedAgainst.add(closeMatchedTo);
                }
            }
        }
        result.put("noMatch", noMatch);
        result.put("closeMatch", closeMatch);
        result.put("closeMatchedTo", matchedAgainst);
        result.put("len", List.of(Map.of("size", String.valueOf(row.size()))));
        result.put("name", List.of(Map.of("name", name)));
        return result;
    }

    public List<String> getContentTypes() {
        return  contentTypes;
    }
}