package md54.AikamSpring.data;

import md54.AikamSpring.entity.Purchases;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PurchasesRepository extends CrudRepository<Purchases, Long> {
    List<Purchases> getPurchasesByGoodsId(long id);
}
