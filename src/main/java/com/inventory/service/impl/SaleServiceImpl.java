package com.inventory.service.impl;

import com.inventory.dto.SaleDTO;
import com.inventory.entity.Item;
import com.inventory.entity.Sale;
import com.inventory.repository.ItemRepository;
import com.inventory.repository.SaleRepository;
import com.inventory.service.SaleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Override
    @Transactional
    public Sale createSale(SaleDTO saleDTO) {
        Item item = itemRepository.findById(saleDTO.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (item.getStockQuantity() < saleDTO.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        Sale sale = new Sale();
        sale.setItem(item);
        sale.setQuantity(saleDTO.getQuantity());
        sale.setItemName(item.getName());

        item.setStockQuantity(item.getStockQuantity() - saleDTO.getQuantity());
        itemRepository.save(item);

        return saleRepository.save(sale);
    }

    @Override
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    @Override
    public Optional<Sale> getSaleById(Long id) {
        return saleRepository.findById(id);
    }

    @Override
    @Transactional
    public Sale updateSale(Long id, SaleDTO saleDTO) {
        Sale existingSale = saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found"));

        Item item = itemRepository.findById(saleDTO.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        int adjustedStockQuantity = item.getStockQuantity() + existingSale.getQuantity() - saleDTO.getQuantity();
        if (adjustedStockQuantity < 0) {
            throw new RuntimeException("Insufficient stock for update");
        }

        item.setStockQuantity(adjustedStockQuantity);
        itemRepository.save(item);

        existingSale.setItem(item);
        existingSale.setQuantity(saleDTO.getQuantity());
        existingSale.setItemName(item.getName());

        return saleRepository.save(existingSale);
    }

    @Override
    @Transactional
    public void deleteSale(Long id) {
        Sale existingSale = saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found"));

        Item item = existingSale.getItem();
        item.setStockQuantity(item.getStockQuantity() + existingSale.getQuantity());
        itemRepository.save(item);

        saleRepository.delete(existingSale);
    }
}
