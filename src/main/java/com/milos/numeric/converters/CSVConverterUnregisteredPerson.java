package com.milos.numeric.converters;

import com.milos.numeric.entities.UnregisteredPerson;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

@Component
public class CSVConverterUnregisteredPerson extends CSVConverter<UnregisteredPerson>
{

    @Override
    public List<UnregisteredPerson> convert(MultipartFile file) throws IOException {

        List<UnregisteredPerson> list = new LinkedList<>();

        Reader reader = new InputStreamReader(file.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();

        String[] values = null;
        while ((values = csvReader.readNext()) != null)
        {
            UnregisteredPerson unregisteredPerson = new UnregisteredPerson();

            String[] rec = values[0].split(";");

            String surname = rec[1];

            String name = rec[2];

            String personalNumber = rec[3];


            unregisteredPerson.setSurname(surname);
            unregisteredPerson.setName(name);
            unregisteredPerson.setPersonalNumber(personalNumber);

            list.add(unregisteredPerson);
        }




        return list;


    }
}
