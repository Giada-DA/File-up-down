package com.example.fileupdown.service;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {

    //nel caso, su yml fare: C:\\Users\\Utente-PC\\Desktop\\Eh con le doppie slash
    @Value("${fileRepositoryFolder}")
    private String fileRepositoryFolder;

    //@SneakyThrows equivalente di throws IOException
    public String upload(MultipartFile file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString();
        String completeFileName = newFileName + "." + extension;

        File finalFolder = new File(fileRepositoryFolder);
        //se rinomino, o cancello, la cartella destinataria lancia un'eccezione
        if (!finalFolder.exists()) throw new IOException("Final folder does not exists");
        //oppure
        if (!finalFolder.isDirectory()) throw new IOException("Final folder is not a directory");
        File finalDestination = new File(fileRepositoryFolder + "\\" + completeFileName);

        //se esiste già un file con quel nome:
        if (finalDestination.exists()) throw new IOException("File conflict");

        file.transferTo(finalDestination);
        return completeFileName;
    }

    public byte[] download(String fileName) throws IOException {
        File fileFromRepository = new File(fileRepositoryFolder + "\\" + fileName);
        if (!fileFromRepository.exists()) throw new IOException("File does not exist");
        return IOUtils.toByteArray(new FileInputStream(fileFromRepository));
    }

    public void remove(String fileName) throws Exception {
        File fileFromRepository = new File(fileRepositoryFolder + "\\" + fileName);
        if (!fileFromRepository.exists()) return;
        boolean deleteResult = fileFromRepository.delete();
        if (deleteResult == false) throw new Exception("Cannot delete file");
    }
}
