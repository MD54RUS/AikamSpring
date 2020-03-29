package md54.AikamSpring.controller.InputOutput;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public interface Reader {

    void setSource(String source);

    // Получение набора критериев для запросов
    JsonNode getCriteria() throws IOException;

    // Получение пары дат для статистики
    Map<String, LocalDate> getDates() throws IOException;
}
