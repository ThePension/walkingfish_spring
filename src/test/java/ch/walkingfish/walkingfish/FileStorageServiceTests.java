package ch.walkingfish.walkingfish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import ch.walkingfish.walkingfish.service.FileStorageService;

@SpringBootTest
public class FileStorageServiceTests {

  @Autowired
  private FileStorageService fileStorageService;

  // @Test
  // void testInjectedComponentsAreNotNull(){
  //   assertThat(fileStorageService).isNotNull();
  // }

  @Test
  public void testSaveFile() {
    MultipartFile file = generateMultipartFile();

    // fileStorageService.save(file, "testFileName");
  }

  private static MultipartFile generateMultipartFile()
  {
    return new MultipartFile() {
      @Override
      public String getName() {
        return "test";
      }

      @Override
      public String getOriginalFilename() {
        return "test";
      }

      @Override
      public String getContentType() {
        return "image/jpeg";
      }

      @Override
      public boolean isEmpty() {
        return false;
      }

      @Override
      public long getSize() {
        return 0;
      }

      @Override
      public byte[] getBytes() throws IOException {
        return new byte[0];
      }

      @Override
      public InputStream getInputStream() throws IOException {
        return null;
      }

      @Override
      public void transferTo(File dest) throws IOException, IllegalStateException {

      }
    };
  }
}