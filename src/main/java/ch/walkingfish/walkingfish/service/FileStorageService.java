package ch.walkingfish.walkingfish.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final Path root = Paths.get("src\\main\\resources\\static\\articlesImages");

    /**
     * Save the file to the server
     * @param file the file to save
     * @param fileName the name of the file
     * @throws IOException if the file can't be saved
     */
    public void save(MultipartFile file, String fileName) throws IOException {
        File save = new File("src\\main\\resources\\static\\articlesImages", fileName);

        Path path = Path.of(save.getAbsolutePath());

        file.transferTo(path);
    }

    /**
     * Delete the file from the server
     * @param filename the name of the file to delete
     * @return true if the file was deleted, false if it didn't exist
     * @throws IOException if the file can't be deletedF
     */
    public boolean delete(String filename) throws IOException {
        Path file = root.resolve(filename);
        return Files.deleteIfExists(file);
    }
}
