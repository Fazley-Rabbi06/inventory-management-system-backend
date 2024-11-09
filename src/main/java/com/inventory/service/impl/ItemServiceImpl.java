package com.inventory.service.impl;

import com.inventory.dto.ItemDTO;
import com.inventory.entity.Item;
import com.inventory.repository.ItemRepository;
import com.inventory.service.ItemService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    @Transactional
    public Item createItem(ItemDTO itemDTO) {
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setStockQuantity(itemDTO.getStockQuantity());
        return itemRepository.save(item);
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    @Override
    @Transactional
    public Item updateItem(Long id, ItemDTO itemDTO) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        item.setName(itemDTO.getName());
        item.setStockQuantity(itemDTO.getStockQuantity());
        return itemRepository.save(item);
    }

    @Override
    @Transactional
    public void deleteItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        itemRepository.delete(item);
    }

    @Override
    public String generateStockReport() {
        List<Item> items = itemRepository.findAll();
        StringBuilder report = new StringBuilder("<h1>Stock Report</h1>");

        report.append("<style>")
                .append("h1 { text-align: center; color: #333; }")
                .append("ul { font-family: Arial, sans-serif; padding-left: 20px; }")
                .append("li { font-size: 16px; padding: 5px 0; }")
                .append("li:odd { background-color: #f9f9f9; }")
                .append("li:even { background-color: #eaeaea; }")
                .append("</style>");

        report.append("<ul>");
        for (Item item : items) {
            report.append("<li>")
                    .append("<strong>").append(item.getName()).append("</strong> - Quantity: ")
                    .append(item.getStockQuantity())
                    .append("</li>");
        }
        report.append("</ul>");

        return report.toString();
    }

}
