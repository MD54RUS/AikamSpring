package md54.AikamSpring.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "\"GOODS\"")
public class Goods extends BaseEntity {

  @Column(name = "\"NAME\"")
  private String name;

  @Column(name = "\"PRICE\"")
  private int price;
}
