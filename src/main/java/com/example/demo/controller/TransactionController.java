package com.example.demo.controller;

import com.example.demo.service.Transactions;
import com.example.demo.service.Transactions;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping(path = "api/v1/transaction")
@CrossOrigin(origins = "https://paymentology-front.herokuapp.com/")
public class TransactionController{

    @Autowired
    Transactions transactions;

    @PostMapping(path = "/upload-csv")
    public @ResponseBody
    ResponseEntity<HashMap<String, HashMap<String, List<Map<String, String>>>>> UploadCSVAndProcessInformation (@RequestParam("fileUpload") MultipartFile[] file) throws Exception {
        String[] fileContentType = new String[]{file[0].getContentType(), file[1].getContentType()};
        HashMap<String,HashMap<String, List<Map<String, String>>>> result = new HashMap<>();
        if(transactions.getContentTypes().contains(fileContentType[0]) || transactions.getContentTypes().contains(fileContentType[1])) {
            try {
                List<List<Map<String, String>>> allRows = new ArrayList<>();
                //data type 'Record' provided by Univocity dependency
                Record record;
                Map<String, String> orig;
                int i = 0;
                String[] csvName = new String[2];
                for (MultipartFile multipartFile : file) {
                    //initialize csv parser settings
                    CsvParserSettings settings = new CsvParserSettings();
                    //get the headers from our input file
                    settings.setHeaderExtractionEnabled(true);
                    //initialize csv parser
                    CsvParser parser = new CsvParser(settings);
                    InputStream inputStream = multipartFile.getInputStream();
                    csvName[i] = multipartFile.getOriginalFilename();
                    // parse csv on demand.
                    parser.beginParsing(inputStream);
                    List<Map<String, String>> originalValues = new ArrayList<>();
                    while ((record = parser.parseNextRecord()) != null) {
                        //get the original values of selected columns (by name) in a map.
                        orig = record.toFieldMap();
                        originalValues.add(orig);
                    }
                    allRows.add(originalValues);
                    i++;
                }
                //get report when file 1 is checked against file 2
                result.put("file1", transactions.generateTransactionRecordReport(allRows.get(0), allRows.get(1), csvName[0], "TransactionID"));
                // get report when file 2 is checked against file 1
                result.put("file2", transactions.generateTransactionRecordReport(allRows.get(1), allRows.get(0), csvName[1], "TransactionID"));
                return ResponseEntity.ok().body(result);
            } catch (Exception e){
                throw new Exception(e);
            }
        }else {
            return ResponseEntity.noContent().build();
        }
    }
}

