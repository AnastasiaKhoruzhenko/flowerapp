package com.hse.flowerapp.rest;

import com.hse.flowerapp.dto.ItemDto;
import com.hse.flowerapp.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/items/")
public class ItemRestController {

    private final ItemService itemService;

    @Autowired
    public ItemRestController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(value = "/all")
    @ResponseBody
    public List<ItemDto> getAll(){
        return itemService.getAllItems();
    }

    @GetMapping(value = "{id}")
    @ResponseBody
    public ItemDto getItemById(@PathVariable("id") Long id){
        return itemService.getItemById(id);
    }
}

