package md54.AikamSpring;

import md54.AikamSpring.controller.InputOutput.Reader;
import md54.AikamSpring.controller.InputOutput.Writer;
import md54.AikamSpring.controller.MainLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "md54.AikamSpring.data")
public class AikamSpringApplication implements CommandLineRunner {
  private static Logger logger = LoggerFactory.getLogger(AikamSpringApplication.class);
  @Autowired
  private Reader reader;
  @Autowired
  private Writer writer;
  @Autowired
  private MainLogic mainLogic;

  public static void main(String[] args) {
    logger.info("STARTING THE APPLICATION");
    SpringApplication.run(AikamSpringApplication.class, args);
    logger.info("APPLICATION FINISHED");
  }

  @Override
  public void run(String... args) {
    reader.setSource(args[1]);
    writer.setDest(args[2]);
    logger.debug("Arguments = {}", Arrays.toString(args));
    switch (args[0]) {
      case "search":
        mainLogic.executeSearch();
        break;
      case "stat":
        mainLogic.executeStat();
        break;
      default:
        logger.error("Invalid arguments = {}", Arrays.toString(args));
        System.out.println("Не правильно заданы аргументы коммандной строки");
    }
  }
}
