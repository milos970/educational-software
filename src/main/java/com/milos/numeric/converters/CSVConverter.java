package com.milos.numeric.converters;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
public abstract class CSVConverter<T>
{
    public abstract List<T> convert(MultipartFile file) throws IOException;
}
