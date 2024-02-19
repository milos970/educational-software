package com.milos.numeric.converters;

import com.milos.numeric.entities.Person;
import com.milos.numeric.entities.UnregisteredPerson;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

public class CSVConverterUnregisteredPerson extends CSVConverter<UnregisteredPerson>
{

    @Override
    public List<UnregisteredPerson> convert(MultipartFile file)
    {
        List<UnregisteredPerson> list = new LinkedList<>();

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT))
        {

            for (CSVRecord csvRecord : csvParser)
            {
                UnregisteredPerson unregisteredPerson = new UnregisteredPerson();

                String surname = csvRecord.get(1);
                String name = csvRecord.get(2);
                String personalNumber = csvRecord.get(3);

                unregisteredPerson.setSurname(surname);
                unregisteredPerson.setName(name);
                unregisteredPerson.setPersonalNumber(personalNumber);

                list.add(unregisteredPerson);
            }
        } catch (IOException e) {

            throw new RuntimeException(e);
        }


        return list;
    }
}
