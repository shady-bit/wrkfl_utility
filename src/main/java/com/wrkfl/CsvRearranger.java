package com.wrkfl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class CsvRearranger {
    public static void main(String[] args) throws IOException {
        String inputFile = "input.csv";
        String outputFile = "output.csv";

        // Read the CSV file
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("description", "process_id", "code"));

        List<CSVRecord> records = parser.getRecords();
        parser.close();

        // Create a map to store process_id and their associated codes
        Map<String, CSVRecord> processIdMap = new HashMap<>();
        Map<String, List<String>> codeMap = new HashMap<>();

        for (CSVRecord record : records) {
            String processId = record.get("process_id");
            String code = record.get("code");

            processIdMap.put(processId, record);

            if (!codeMap.containsKey(code)) {
                codeMap.put(code, new ArrayList<>());
            }
            codeMap.get(code).add(processId);
        }

        // Create a list to store the sorted records
        List<CSVRecord> sortedRecords = new ArrayList<>();
        Set<String> processedIds = new HashSet<>();

        // Sort records
        for (CSVRecord record : records) {
            String processId = record.get("process_id");
            if (!processedIds.contains(processId)) {
                sortRecords(processId, codeMap, processIdMap, sortedRecords, processedIds);
            }
        }

        // Write the sorted records to a new CSV file
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);

        for (CSVRecord record : sortedRecords) {
            printer.printRecord(record.get("description").trim(), record.get("process_id").trim(), record.get("code").trim());
        }

        printer.close();
        writer.close();
    }

    private static void sortRecords(String processId, Map<String, List<String>> codeMap, Map<String, CSVRecord> processIdMap, List<CSVRecord> sortedRecords, Set<String> processedIds) {
        processedIds.add(processId);
        CSVRecord record = processIdMap.get(processId);
        sortedRecords.add(record);

        if (codeMap.containsKey(processId)) {
            for (String childProcessId : codeMap.get(processId)) {
                if (!processedIds.contains(childProcessId)) {
                    sortRecords(childProcessId, codeMap, processIdMap, sortedRecords, processedIds);
                }
            }
        }
    }
}
