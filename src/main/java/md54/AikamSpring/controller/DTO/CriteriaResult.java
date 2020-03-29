package md54.AikamSpring.controller.DTO;

import com.fasterxml.jackson.databind.JsonNode;
import md54.AikamSpring.entity.Customer;

import java.util.List;

public class CriteriaResult {
    private JsonNode criteria;
    private List<Customer> results;

    public CriteriaResult(JsonNode criteria, List<Customer> results) {
        this.criteria = criteria;
        this.results = results;
    }

    public JsonNode getCriteria() {
        return criteria;
    }

    public List<Customer> getResults() {
        return results;
    }
}
