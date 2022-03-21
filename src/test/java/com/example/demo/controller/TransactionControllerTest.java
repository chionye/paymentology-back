package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @MockBean
    private TransactionController transactionController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testUploadCSVAndProcessInformation() throws Exception {
        String[] filename = {"ClientMarkoffFile20140113.csv","PaymentologyMarkoffFile20140113.csv"};
        MockMultipartFile file = new MockMultipartFile(
                "fileUpload",
                filename[0],
                "text/csv",
                "TransactionAmount, WalletReference, TransactionType, TransactionDescription, ProfileName, TransactionNarrative, TransactionDate, TransactionID, \n -10000, P_NzI0NDkyMDRfMTM4NjA1ODUxOC40Nzcz,1,DEDUCT,Card Campaign, MAUN BRANCH               MAUN BRANCH   BW, 2014-01-13 01:31:03, 0304012774638303, \n -1000, P_NzI0NDkyMDRfMTM4NjA1ODUxOC40Nzcz,1,DEDUCT,Card Campaign, MAUN BRANCH               MAUN BRANCH   BW, 2014-01-13 01:31:03, 0304012774637303".getBytes(StandardCharsets.UTF_8)
        );
        MockMultipartFile file1 = new MockMultipartFile(
                "fileUpload",
                filename[1],
                "text/csv",
                "TransactionAmount, WalletReference, TransactionType, TransactionDescription, ProfileName, TransactionNarrative, TransactionDate, TransactionID, \n -10000, P_NzI0NDkyMDRfMTM4NjA1ODUxOC40Nzcz,1,DEDUCT,Card Campaign, MAUN BRANCH               MAUN BRANCH   BW, 2014-01-13 01:31:03, 0304012774638303, \n -1000, P_NzI0NDkyMDRfMTM4NjA1ODUxOC40Nzcz,1,DEDUCT,Card Campaign, MAUN BRANCH               MAUN BRANCH   BW, 2014-01-13 01:31:03, 03040127746379303,\n -10000, P_NzI0NDkyMDRfMTM4NjA1ODUxOC40Nzcz,1,DEDUCT,Card Campaign, MAUN BRANCH               MAUN BRANCH   BW, 2014-01-13 01:31:03, 0584012572453318".getBytes(StandardCharsets.UTF_8)
        );

        MockMultipartHttpServletRequestBuilder multipartRequest =
                MockMvcRequestBuilders.multipart("https://paymentology-back.herokuapp.com/api/v1/transaction/upload-csv");
        mockMvc.perform(multipartRequest.file(file).file(file1))
                .andExpect(status().isOk());
    }
}