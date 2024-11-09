package com.inventory.service;

import com.inventory.dto.PurchaseDTO;
import com.inventory.entity.Purchase;

import java.util.List;
import java.util.Optional;

public interface PurchaseService {
    Purchase createPurchase(PurchaseDTO purchaseDTO);
    List<Purchase> getAllPurchases();
    Optional<Purchase> getPurchaseById(Long id);
    Purchase updatePurchase(Long id, PurchaseDTO purchaseDTO);
    void deletePurchase(Long id);
}
