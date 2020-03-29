package md54.AikamSpring.controller;

import com.fasterxml.jackson.databind.JsonNode;
import md54.AikamSpring.data.CustomerRepository;
import md54.AikamSpring.data.GoodsRepository;
import md54.AikamSpring.data.PurchasesRepository;
import md54.AikamSpring.entity.Customer;
import md54.AikamSpring.entity.Purchases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class QueryController {

  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private GoodsRepository goodsRepository;
  @Autowired
  private PurchasesRepository purchasesRepository;

  public QueryController() {
  }

//  public QueryController(
//      CustomerRepository customerRepository,
//      GoodsRepository goodsRepository,
//      PurchasesRepository purchasesRepository) {
//    this.customerRepository = customerRepository;
//    this.goodsRepository = goodsRepository;
//    this.purchasesRepository = purchasesRepository;
//  }

  private static List<Customer> queryCustomersByCostRange(long minExpenses, long maxExpenses) {
    final String SQL_QUERY =
            " SELECT \"CUSTOMER\".\"NAME\",\"LASTNAME\", totalCost FROM \"CUSTOMER\" join"
                    + " (SELECT \"CUSTOMER_ID\", sum(\"GOODS\".\"PRICE\") as totalCost FROM \"PURCHASES\",\"GOODS\" where \"GOODS\".\"ID\" = \"PURCHASES\".\"GOODS_ID\" GROUP BY \"CUSTOMER_ID\")"
                    + "AS results on \"CUSTOMER\".\"ID\" = results.\"CUSTOMER_ID\""
                    + "where totalCost > ? AND totalCost< ?";
    return null;
  }

  private static List<Customer> queryPassiveCustomers(long badCustomers) {

    final String SQL_QUERY =
            "SELECT \"NAME\",\"LASTNAME\" FROM \"CUSTOMER\" "
                    + "join (SELECT \"CUSTOMER_ID\" FROM \"PURCHASES\" GROUP BY \"CUSTOMER_ID\""
                    + "ORDER by COUNT(*) LIMIT ?) AS results on \"CUSTOMER\".\"ID\" = results.\"CUSTOMER_ID\"";
    return null;
  }

  public List<Customer> get(JsonNode criteria) {
    if (criteria.get("lastName") != null) {
      return queryCustomersByLastname(criteria.get("lastName").asText());
    }
    if (criteria.get("productName") != null) {
      return queryCustomerByProduct(
              criteria.get("productName").asText(), criteria.get("minTimes").asLong());
    }
    if (criteria.get("minExpenses") != null) {
      return queryCustomersByCostRange(
              criteria.get("minExpenses").asLong(), criteria.get("maxExpenses").asLong());
    }
    if (criteria.get("badCustomers") != null) {
      return queryPassiveCustomers(criteria.get("badCustomers").asLong());
    }
    throw new RuntimeException("Неправильно задан критерий для поиска.");
  }

  private List<Customer> queryCustomersByLastname(String lastname) {
    return customerRepository.getCustomersByLastName(lastname);
  }

  private List<Customer> queryCustomerByProduct(String name, long count) {
    long id = goodsRepository.getGoodsByName(name).getId();
    List<Purchases> purchases = purchasesRepository.getPurchasesByGoodsId(id);
    return purchases.stream()
            .collect(Collectors.groupingBy(Purchases::getCustomer))
            .entrySet()
            .stream()
            .filter(x -> x.getValue().size() >= count)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
  }
}
