package md54.AikamSpring.controller.DTO;

import java.util.List;

public class AnswerSearchDTO extends AnswerTemplate {
    private String type = "search";
    private List<CriteriaResult> results;

    public AnswerSearchDTO(List<CriteriaResult> results) {
        this.results = results;
    }

    public List<CriteriaResult> getResults() {
        return results;
    }

    public String getType() {
        return type;
    }
}
