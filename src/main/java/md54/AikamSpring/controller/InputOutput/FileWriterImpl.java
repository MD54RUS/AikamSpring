package md54.AikamSpring.controller.InputOutput;

import com.fasterxml.jackson.databind.ObjectMapper;
import md54.AikamSpring.controller.DTO.AnswerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;

@Repository
public class FileWriterImpl implements Writer {
  private static File file;

  @Override
  public void setDest(String dest) {
    file = new File(dest);
  }

  public void write(AnswerTemplate answer) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      mapper.writeValue(file, answer);
    } catch (IOException e) {
      Logger logger = LoggerFactory.getLogger(Writer.class);
      logger.error("Cant save result", e);
      System.out.println(String.format("Cant save result in file %s", file.getName()));
    }
  }
}
