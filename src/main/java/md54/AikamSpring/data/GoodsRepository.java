package md54.AikamSpring.data;

import md54.AikamSpring.entity.Customer;
import md54.AikamSpring.entity.Goods;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GoodsRepository extends CrudRepository<Goods, Long> {
    Goods getGoodsByName(String name);
}
