package md54.AikamSpring.controller.InputOutput;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Repository
public class FileReaderImpl implements Reader {

    private static String filename;
    private ObjectMapper mapper;

    @Override
    public void setSource(String source) {
        filename = source;
    }

    public FileReaderImpl() {
        mapper = new ObjectMapper();
    }

    @Override
    public JsonNode getCriteria() throws IOException {
        JsonNode res = mapper.readTree(new File(filename));
        return res.get("criterias");
    }

    @Override
    public Map<String, LocalDate> getDates() throws IOException {
        JsonNode res = mapper.readTree(new File(filename));

        LocalDate start = LocalDate.parse(res.get("startDate").asText());
        LocalDate end = LocalDate.parse(res.get("endDate").asText());
        if (!(start.compareTo(end) < 0)) {
            throw new RuntimeException("Даты должны быть заданы в порядке возрастания!");
        }
        Map<String, LocalDate> dates = new HashMap<>();
        dates.put("start", start);
        dates.put("end", end);
        return dates;
    }
}
