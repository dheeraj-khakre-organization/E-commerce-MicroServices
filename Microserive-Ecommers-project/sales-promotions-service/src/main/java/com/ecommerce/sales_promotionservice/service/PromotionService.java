package com.ecommerce.sales_promotionservice.service;



import com.ecommerce.sales_promotionservice.models.Promotion;
import com.ecommerce.sales_promotionservice.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    public RedisService redisService;

    public Promotion createPromotion(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    public List<Promotion> getActivePromotions() {
        return promotionRepository.findByIsActiveTrue();
    }

    public Promotion getPromotionById(String id) {
        Promotion promotion = redisService.getData(id, Promotion.class);
        if (promotion!=null){
            return promotion;
        }else {
            Promotion promotion1 = promotionRepository.findById(id).orElse(null);
            assert promotion1 != null;
            redisService.saveData(promotion1.getId(), (Object) promotion1, 300L);
            return promotion1;
        }
    }

    public void deletePromotion(String id) {
        promotionRepository.deleteById(id);
    }

    public Promotion updatePromotion(String id, Promotion updated) {
        updated.setId(id);
        return promotionRepository.save(updated);
    }
}