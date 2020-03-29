package md54.AikamSpring.controller;

import com.fasterxml.jackson.databind.JsonNode;
import md54.AikamSpring.controller.DTO.*;
import md54.AikamSpring.controller.InputOutput.Reader;
import md54.AikamSpring.controller.InputOutput.Writer;
import md54.AikamSpring.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MainLogic {

    @Autowired
    private QueryController controller;

    @Autowired
    private QueryExecutorStatistics statistics;

    @Autowired
    private Reader reader;

    @Autowired
    private Writer writer;

    private Logger logger;

    private AnswerTemplate answer;


    public MainLogic(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
        this.controller = new QueryController();
        logger = LoggerFactory.getLogger(MainLogic.class);
    }

  // логика исполнения с ключем search
  public void executeSearch() {
    try {
      JsonNode conditions = reader.getCriteria();
      List<CriteriaResult> results = new ArrayList<>();
      if (conditions != null) {
        for (int i = 0; i < conditions.size(); i++) {
          JsonNode criterion = conditions.get(i);
          List<Customer> customers = controller.get(criterion);
          results.add(new CriteriaResult(criterion, customers));
        }
      }
      answer = new AnswerSearchDTO(results);
    } catch (IOException e) {
      logger.error("Search execute error: ", e);
      answer = new AnswerErrorDTO(e.getMessage());
    }
    writer.write(answer);
  }

  // логика исполнения с ключем stat
  public void executeStat() {
    try {
        Map<String, LocalDate> dates = reader.getDates();
        QueryExecutorStatistics.setDates(dates);
        answer = new AnswerStatisticsDTO(dates, statistics.execute());
    } catch (IOException | SQLException | NullPointerException e) {
      logger.error("Statistics execute error: ", e);
      answer = new AnswerErrorDTO(e.getMessage());
    }
    writer.write(answer);
  }
}
