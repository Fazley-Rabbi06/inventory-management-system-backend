package com.inventory.service.impl;

import com.inventory.dto.PurchaseDTO;
import com.inventory.entity.Item;
import com.inventory.entity.Purchase;
import com.inventory.repository.ItemRepository;
import com.inventory.repository.PurchaseRepository;
import com.inventory.service.PurchaseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    @Transactional
    public Purchase createPurchase(PurchaseDTO purchaseDTO) {
        Item item = itemRepository.findById(purchaseDTO.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        Purchase purchase = new Purchase();
        purchase.setItem(item);
        purchase.setQuantity(purchaseDTO.getQuantity());
        purchase.setItemName(item.getName());

        item.setStockQuantity(item.getStockQuantity() + purchaseDTO.getQuantity());
        itemRepository.save(item);

        return purchaseRepository.save(purchase);
    }

    @Override
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    @Override
    public Optional<Purchase> getPurchaseById(Long id) {
        return purchaseRepository.findById(id);
    }

    @Override
    @Transactional
    public Purchase updatePurchase(Long id, PurchaseDTO purchaseDTO) {
        Purchase existingPurchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));

        Item item = itemRepository.findById(purchaseDTO.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        item.setStockQuantity(item.getStockQuantity() - existingPurchase.getQuantity() + purchaseDTO.getQuantity());
        itemRepository.save(item);

        existingPurchase.setItem(item);
        existingPurchase.setQuantity(purchaseDTO.getQuantity());
        existingPurchase.setItemName(item.getName());

        return purchaseRepository.save(existingPurchase);
    }

    @Override
    @Transactional
    public void deletePurchase(Long id) {
        Purchase existingPurchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));

        Item item = existingPurchase.getItem();
        item.setStockQuantity(item.getStockQuantity() - existingPurchase.getQuantity());
        itemRepository.save(item);

        purchaseRepository.delete(existingPurchase);
    }
}
