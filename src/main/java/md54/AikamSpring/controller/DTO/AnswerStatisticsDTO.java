package md54.AikamSpring.controller.DTO;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AnswerStatisticsDTO extends AnswerTemplate {
    private int totalDays;
    private List<CustomersDTO> customers;
    private BigDecimal totalExpenses;
    private BigDecimal avgExpenses;

    public AnswerStatisticsDTO(Map<String, LocalDate> dates, List<CustomersDTO> customers) {
        totalExpenses = new BigDecimal(0);
        LocalDate startDay = dates.get("start");
        LocalDate endDay = dates.get("end");
        type = "stat";
        totalDays = daysBetween(startDay, endDay);
        this.customers = customers;
        for (CustomersDTO customer : customers) {
            totalExpenses = totalExpenses.add(BigDecimal.valueOf(customer.totalExpenses));
        }
        avgExpenses =
                totalExpenses.divide(BigDecimal.valueOf(customers.size()), 2, RoundingMode.HALF_UP);
    }

    public int getTotalDays() {
        return totalDays;
    }

    public List<CustomersDTO> getCustomers() {
        return customers;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public BigDecimal getAvgExpenses() {
        return avgExpenses;
    }

    private int daysBetween(final LocalDate start, final LocalDate end) {
        int days = (int) ChronoUnit.DAYS.between(start, end);
        List<DayOfWeek> ignore = new ArrayList<>();
        ignore.add(DayOfWeek.SATURDAY);
        ignore.add(DayOfWeek.SUNDAY);

        if (days == 0) {
            return 0;
        }

        if (!ignore.isEmpty()) {
            int startDay = start.getDayOfWeek().getValue();
            int endDay = end.getDayOfWeek().getValue();
            int diff = days / 7 * ignore.size();

            for (DayOfWeek day : ignore) {
                int currDay = day.getValue();
                if (startDay <= currDay) {
                    diff++;
                }
                if (endDay > currDay) {
                    diff++;
                }
            }
            if (endDay > startDay) {
                diff -= endDay - startDay;
            }

            return days - diff;
        }

        return days;
    }

    public static class CustomersDTO {
        String name;
        List<GoodsDTO> purchases;
        int totalExpenses;

        public CustomersDTO(String name) {
            this.name = name;
            purchases = new LinkedList<>();
            totalExpenses = 0;
        }

        public void addPurchases(GoodsDTO purchases) {
            this.purchases.add(purchases);
            totalExpenses += purchases.expenses;
        }

        public String getName() {
            return name;
        }

        public List<GoodsDTO> getPurchases() {
            return purchases;
        }

        public int getTotalExpenses() {
            return totalExpenses;
        }
    }

    public static class GoodsDTO {
        String name;
        int expenses;

        public GoodsDTO(String name, int expenses) {
            this.name = name;
            this.expenses = expenses;
        }

        public String getName() {
            return name;
        }

        public int getExpenses() {
            return expenses;
        }
    }
}
