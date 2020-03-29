package md54.AikamSpring.data;

import md54.AikamSpring.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;

public interface StatisticRepository extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT \"PURCHASES\".\"CUSTOMER_ID\", CONCAT (\"CUSTOMER\".\"LASTNAME\" ,' ',\"CUSTOMER\".\"NAME\") As \"FullName\" "
            + "FROM \"PURCHASES\" "
            + "join \"CUSTOMER\" on \"PURCHASES\".\"CUSTOMER_ID\" = \"CUSTOMER\".\"ID\""
            + "where \"DATE\" between :startDate and :endDate and EXTRACT(dow FROM \"DATE\") not in (6,0)"
            + "GROUP BY \"CUSTOMER_ID\", \"FullName\"", nativeQuery = true)
    SortedMap<Long, String> getCustomersForStatistic(@Param("startDate") java.sql.Date startDate, @Param("endDate") java.sql.Date endDate);
}
