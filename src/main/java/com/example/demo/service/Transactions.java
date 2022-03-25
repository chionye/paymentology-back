package com.example.demo.service;


import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Transactions {

    private static final List<String> contentTypes = List.of("text/csv", "application/vnd.ms-excel");


    public HashMap<String, List<Map<String, String>>> generateTransactionRecordReport(List<Map<String, String>> firstFile, List<Map<String, String>> secondFile, String filename1, String comparisonKey) {
        HashMap<String, List<Map<String, String>>> result = new HashMap<>();
        List<Map<String, String>> closeMatch = new ArrayList<>();
        List<Map<String, String>> matchedAgainst = new ArrayList<>();
        List<Map<String, String>> noMatch = new ArrayList<>();
        for (int i = 0; i < firstFile.size(); i++) {
            for (int j = 0; j < secondFile.size(); j++) {
                Map<String, String> matches;
                if (firstFile.get(i).get(comparisonKey).replace(" ", "").equals(secondFile.get(j).get(comparisonKey))) {
                    break;
                } else if (firstFile.size() - 1 == j && !(secondFile.get(i).get(comparisonKey).equals(firstFile.get(j).get(comparisonKey)))) {
                    matches = secondFile.get(i);
                    noMatch.add(matches);
                }
            }
        }

        result.put("noMatch", noMatch);
        result.put("closeMatch", closeMatch(noMatch, secondFile, comparisonKey));
        result.put("closeMatchedTo", matchedAgainst);
        result.put("len", List.of(Map.of("size", String.valueOf(firstFile.size())), Map.of("size1", String.valueOf(secondFile.size()))));
        result.put("name", List.of(Map.of("name", filename1)));
        return result;
    }

    public List<Map<String, String>> closeMatch(List<Map<String, String>> noMatch, List<Map<String, String>> file, String key){
        List<Map<String, String>> closeMatch = new ArrayList<>();
        for (int i = 0; i < noMatch.size(); i++) {
            for (int j = 0; j < file.size(); j++) {
                if(noMatch.get(i).get(key).regionMatches(0, file.get(j).get(key), 0, (noMatch.get(i).get(key).length()/2) + 2) && !(noMatch.get(i).get(key).equals(file.get(j).get(key)))){
                    closeMatch.add(file.get(j));
                    break;
                }
                if (j == file.size() - 1 && !(noMatch.get(i).get(key).equals(file.get(j).get(key)))){
                    closeMatch.add(Map.of());
                }
            }
        }
        return closeMatch;
    }


    public List<String> getContentTypes() {
        return  contentTypes;
    }
}