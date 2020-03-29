package md54.AikamSpring.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "\"PURCHASES\"")
public class Purchases extends BaseEntity {

  @JoinColumn(name = "\"CUSTOMER_ID\"")
  @ManyToOne
  private Customer customer;

  @JoinColumn(name = "\"GOODS_ID\"")
  @ManyToOne
  private Goods goods;

  @Column(name = "\"DATE\"")
  private LocalDate date;
}
