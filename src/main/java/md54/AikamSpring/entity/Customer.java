package md54.AikamSpring.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Data
@Entity
@Table(name = "\"CUSTOMER\"")
public class Customer extends BaseEntity {

    @Column(name = "\"LASTNAME\"")
    private String lastName;

    @Column(name = "\"NAME\"")
    private String firstName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return firstName.equals(customer.firstName) && lastName.equals(customer.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return "Customer{" + ", name='" + firstName + '\'' + ", lastName='" + lastName + '\'' + '}';
    }
}
