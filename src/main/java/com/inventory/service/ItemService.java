package com.inventory.service;

import com.inventory.dto.ItemDTO;
import com.inventory.entity.Item;

import java.util.List;

public interface ItemService {
    Item createItem(ItemDTO itemDTO);
    List<Item> getAllItems();
    Item getItemById(Long id);
    Item updateItem(Long id, ItemDTO itemDTO);
    void deleteItem(Long id);
    String generateStockReport();
}
