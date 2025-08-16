package com.ecommerce.sales_promotionservice.controller;
import com.ecommerce.sales_promotionservice.models.Promotion;
import com.ecommerce.sales_promotionservice.service.PromotionService;
import com.ecommerce.sales_promotionservice.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private RedisService service;

    @PostMapping
    public Promotion create(@RequestBody Promotion promotion) {
        return promotionService.createPromotion(promotion);
    }

    @GetMapping
    public List<Promotion> getAllActive() {
        return promotionService.getActivePromotions();
    }

    @GetMapping("/{id}")
    public Promotion getById(@PathVariable String id) {
        return promotionService.getPromotionById(id);
    }

    @PutMapping("/{id}")
    public Promotion update(@PathVariable String id, @RequestBody Promotion promotion) {
        return promotionService.updatePromotion(id, promotion);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        promotionService.deletePromotion(id);
    }
}