package md54.AikamSpring.data;

import md54.AikamSpring.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RawSqlRepository extends JpaRepository<Customer, Long> {
    @Query(
            value =
                    "SELECT \"CUSTOMER\".\"NAME\",\"LASTNAME\", \"CUSTOMER\".\"ID\", totalCost FROM \"CUSTOMER\" join"
                            + " (SELECT \"CUSTOMER_ID\", sum(\"GOODS\".\"PRICE\") as totalCost FROM \"PURCHASES\",\"GOODS\" "
                            + "where \"GOODS\".\"ID\" = \"PURCHASES\".\"GOODS_ID\" GROUP BY \"CUSTOMER_ID\")"
                            + "AS results on \"CUSTOMER\".\"ID\" = results.\"CUSTOMER_ID\""
                            + "where totalCost > :minExpenses AND totalCost < :maxExpenses",
            nativeQuery = true)
    List<Customer> queryCustomersByCostRange(
            @Param("minExpenses") long minExpenses, @Param("maxExpenses") long maxExpenses);

    @Query(
            value =
                    "SELECT \"NAME\",\"LASTNAME\",\"CUSTOMER\".\"ID\" FROM \"CUSTOMER\" "
                            + "join (SELECT \"CUSTOMER_ID\" FROM \"PURCHASES\" GROUP BY \"CUSTOMER_ID\""
                            + "ORDER by COUNT(*) LIMIT :tooBad) AS results on \"CUSTOMER\".\"ID\" = results.\"CUSTOMER_ID\"",
            nativeQuery = true)
    List<Customer> queryPassiveCustomers(@Param("tooBad") long badCustomers);


}
