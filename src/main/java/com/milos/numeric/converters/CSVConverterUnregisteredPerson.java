package com.milos.numeric.converters;

import com.milos.numeric.PersonToValidate;
import com.milos.numeric.entities.Person;
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
public class CSVConverterUnregisteredPerson extends CSVConverter<PersonToValidate>
{

    @Override
    public List<PersonToValidate> convert(MultipartFile file) throws IOException {

        List<PersonToValidate> list = new LinkedList<>();

        Reader reader = new InputStreamReader(file.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();

        String[] values = null;
        while ((values = csvReader.readNext()) != null)
        {
            PersonToValidate person = new PersonToValidate();

            String[] rec = values[0].split(";");

            String personalNumber = rec[0];

            String name = rec[1];

            String surname = rec[2];

            String email = rec[3];

            System.out.println(personalNumber + " " + name + " " + surname + " " + email);

            person.setPersonalNumber(personalNumber);
            person.setName(name);
            person.setSurname(surname);
            person.setUsername(personalNumber);
            person.setEmail(email);
            list.add(person);
        }

        return list;


    }
}
