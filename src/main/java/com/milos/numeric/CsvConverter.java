package com.milos.numeric;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@Component
public class CsvConverter
{
    public void convert(MultipartFile file)
    {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            for (CSVRecord csvRecord : csvParser)
            {
                for(String item : csvRecord)
                {
                    System.out.print(item + " ");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
