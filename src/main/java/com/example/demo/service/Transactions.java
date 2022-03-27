package com.example.demo.service;


import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Transactions {

    private static final List<String> contentTypes = List.of("text/csv", "application/vnd.ms-excel");

    public HashMap<String, List<Map<String, String>>> generateTransactionRecordReport(
            List<Map<String, String>> firstFile,
            List<Map<String, String>> secondFile,
            String filename,
            int firstFileSize){
        HashMap<String, List<Map<String, String>>> result = new HashMap<>();
        List<Map<String, String>> closeMatch = new ArrayList<>();
        List<Map<String, String>> noMatch = new ArrayList<>();
        for (Map<String, String> stringMap : firstFile) {
            int k = 1;
            String transactionAmountFile1 = stringMap.get("TransactionAmount") != null ? stringMap.get("TransactionAmount") : "";
            String walletRefFile1 = stringMap.get("WalletReference") != null ? stringMap.get("WalletReference") : "";
            for (Map<String, String> stringStringMap : secondFile) {
                String transactionAmountFile2 = stringStringMap.get("TransactionAmount")  != null ? stringMap.get("TransactionAmount") : "";
                String walletRefFile2 = stringStringMap.get("WalletReference") != null ? stringStringMap.get("WalletReference") : "";
                boolean b = walletRefFile2.equals(walletRefFile1) && transactionAmountFile2.equals(transactionAmountFile1);
                if(b)break;
                else if (k == secondFile.size()) {
                    Map<String, String> matches;
                    matches = stringMap;
                    noMatch.add(matches);
                }
                k++;
            }
        }
        result.put("noMatch", noMatch);
        result.put("closeMatch", closeMatch(noMatch, secondFile));
        result.put("len", List.of(Map.of("size", String.valueOf(firstFileSize))));
        result.put("name", List.of(Map.of("name", filename)));
        return result;
    }

    public List<Map<String, String>> closeMatch(List<Map<String, String>> noMatch, List<Map<String, String>> file){
        List<Map<String, String>> closeMatch = new ArrayList<>();
        for (Map<String, String> match : noMatch) {
            String transactionAmountFile1 = match.get("TransactionAmount") != null ? match.get("TransactionAmount") : "";
            String transactionDateFile1 = match.get("TransactionDate") != null ? match.get("TransactionDate") : "";
            String transactionDescriptionFile1 = match.get("TransactionDescription") != null ? match.get("TransactionDescription") : "";
            for (int j = 0; j < file.size(); j++) {
                String transactionAmountFile2 = file.get(j).get("TransactionAmount") != null ? file.get(j).get("TransactionAmount") : "";
                String transactionDateFile2 = file.get(j).get("TransactionDate") != null ? file.get(j).get("TransactionDate") : "";
                String transactionDescriptionFile2 = file.get(j).get("TransactionDescription") != null ? file.get(j).get("TransactionDescription") : "";
                boolean b = transactionAmountFile2.equals(transactionAmountFile1) && transactionDateFile2.equals(transactionDateFile1) && transactionDescriptionFile2.equals(transactionDescriptionFile1);
                if (b) {
                    closeMatch.add(file.get(j));
                    break;
                } else if (j == file.size() - 1) {
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