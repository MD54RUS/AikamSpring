package md54.AikamSpring.data;

import md54.AikamSpring.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> getCustomersByLastName(String lastName);
}
