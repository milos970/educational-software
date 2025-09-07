package com.milos.numeric.exception;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.IOException;

public class FileStorageException extends RuntimeException {
    public FileStorageException(@NotBlank @Size(min = 1, max = 15, message = "Názov materiálu musí byť v rozmedzí 1 až 15 znakov!") String s, IOException e) {
    }
}
