package com.example.demo.service;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TransactionsTest extends Transactions {

    @Test
    void generateTransactionRecordReport() {
        List<Map<String, String>> test1 = new ArrayList<>(List.of(
                Map.ofEntries(
                        Map.entry("TransactionAmount","-31500"),
                        Map.entry("WalletReference","P_NzIxMjk1MzVfMTM4MDcxNDExMy44NjE1"),
                        Map.entry("TransactionType","0"),
                        Map.entry("TransactionDescription","DEDUCT"),
                        Map.entry("ProfileName","Card Campaign"),
                        Map.entry("TransactionNarrative","347145 J B SPORTS KANYE   BOTSWANA      BW"),
                        Map.entry("TransactionDate","2014-01-12 11:45:36"),
                        Map.entry("TransactionID","0084012279367434"))));
        List<Map<String, String>> test2 = new ArrayList<>(List.of(
                Map.ofEntries(
                        Map.entry("TransactionAmount","-31500"),
                        Map.entry("WalletReference","P_NzIxMjk1MzVfMTM4MDcxNDExMy44NjE1"),
                        Map.entry("TransactionType","0"),
                        Map.entry("TransactionDescription","DEDUCT"),
                        Map.entry("ProfileName","Card Campaign"),
                        Map.entry("TransactionNarrative","347145 J B SPORTS KANYE   BOTSWANA      BW"),
                        Map.entry("TransactionDate","2014-01-12 11:45:36"),
                        Map.entry("TransactionID","0084012279367434"))));
        HashMap<String, List<Map<String, String>>> expectedResult = new HashMap<>(
                Map.ofEntries(
                        Map.entry(
                                "closeMatch",
                                List.of()),
                        Map.entry(
                                "len",
                                List.of(Map.ofEntries(
                                        Map.entry("size", "1")))),
                        Map.entry("noMatch", List.of()),
                        Map.entry("name", List.of(Map.ofEntries(Map.entry("name", "PaymentologyMarkoffFile20140113.csv")))),
                        Map.entry("closeMatchedTo", List.of()))
        );
        assertEquals(expectedResult, generateTransactionRecordReport(test1, test2, "PaymentologyMarkoffFile20140113.csv", "TransactionID"));
    }
}