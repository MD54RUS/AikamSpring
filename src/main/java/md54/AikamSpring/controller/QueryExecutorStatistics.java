package md54.AikamSpring.controller;

import md54.AikamSpring.controller.DTO.AnswerStatisticsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

/**
 * Map<String, LocalDate> dates коллекция с парой дат - сбор статистики за указанный период
 * включительно.
 */
public class QueryExecutorStatistics {

    private java.sql.Date startDate;
    private java.sql.Date endDate;
    private PreparedStatement statement;
    private Connection connection;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public QueryExecutorStatistics(Map<String, LocalDate> dates) throws SQLException {
        this.startDate = java.sql.Date.valueOf(dates.get("start"));
        this.endDate = java.sql.Date.valueOf(dates.get("end"));
//    connection = DatabaseConnection.getInstance().getConnection();
        final String SQL_QUERY_CUSTOMERS =
                "SELECT \"PURCHASES\".\"CUSTOMER_ID\", CONCAT (\"CUSTOMER\".\"LASTNAME\" ,' ',\"CUSTOMER\".\"NAME\") As \"FullName\" "
                        + "FROM \"PURCHASES\" "
                        + "join \"CUSTOMER\" on \"PURCHASES\".\"CUSTOMER_ID\" = \"CUSTOMER\".\"ID\""
                        + "where \"DATE\" between ? and ? and EXTRACT(dow FROM \"DATE\") not in (6,0)"
                        + "GROUP BY \"CUSTOMER_ID\", \"FullName\"";
        statement = connection.prepareStatement(SQL_QUERY_CUSTOMERS);
        statement.setDate(1, this.startDate);
        statement.setDate(2, this.endDate);
    }

    public List<AnswerStatisticsDTO.CustomersDTO> execute() throws SQLException {
        logger.debug("Executing..");
        ResultSet resultSet = statement.executeQuery();
        SortedMap<Long, String> customerList = new TreeMap<>();
        while (resultSet.next()) {
            customerList.put(resultSet.getLong("CUSTOMER_ID"), resultSet.getString("FullName"));
        }
        resultSet.close();
        List<AnswerStatisticsDTO.CustomersDTO> customersDTOList = new ArrayList<>();
        AnswerStatisticsDTO.CustomersDTO customerDTO;

        for (Long x : customerList.keySet()) {
            final String SQL_QUERY_PURCHASES =
                    "SELECT \"GOODS\".\"NAME\", COUNT(DISTINCT \"PURCHASES\")*\"GOODS\".\"PRICE\" as \"total\"\n"
                            + "                  from \"GOODS\"\n"
                            + "                  join \"PURCHASES\" on \"PURCHASES\".\"GOODS_ID\" = \"GOODS\".\"ID\"\n"
                            + "                  where \"PURCHASES\".\"CUSTOMER_ID\" = ? and \"PURCHASES\".\"DATE\" between ? and ? \n"
                            + "                  and EXTRACT(dow FROM \"PURCHASES\".\"DATE\") not in (6,0) \n"
                            + "                  group by \"GOODS\".\"NAME\",\"GOODS\".\"PRICE\"";
            statement = connection.prepareStatement(SQL_QUERY_PURCHASES); // new statement ?
            statement.setLong(1, x);
            statement.setDate(2, startDate);
            statement.setDate(3, endDate);
            customerDTO = new AnswerStatisticsDTO.CustomersDTO(customerList.get(x));
            ResultSet resultSet1 = statement.executeQuery();
            while (resultSet1.next()) {
                AnswerStatisticsDTO.GoodsDTO purchase =
                        new AnswerStatisticsDTO.GoodsDTO(
                                resultSet1.getString("NAME"), resultSet1.getInt("total"));
                customerDTO.addPurchases(purchase);
            }
            customersDTOList.add(customerDTO);
        }
        return customersDTOList;
    }
}
