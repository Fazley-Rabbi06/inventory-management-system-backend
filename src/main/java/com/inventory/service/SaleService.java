package com.inventory.service;

import com.inventory.dto.SaleDTO;
import com.inventory.entity.Sale;

import java.util.List;
import java.util.Optional;

public interface SaleService {
    Sale createSale(SaleDTO saleDTO);
    List<Sale> getAllSales();
    Optional<Sale> getSaleById(Long id);
    Sale updateSale(Long id, SaleDTO saleDTO);
    void deleteSale(Long id);
}
