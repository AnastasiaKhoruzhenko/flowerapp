package com.hse.flowerapp.rest;

import com.hse.flowerapp.dto.ItemDto;
import com.hse.flowerapp.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity getAll(){
        List<ItemDto> itemDtoList = itemService.getAllItems();
        return ResponseEntity.ok(itemDtoList);
    }

    @GetMapping(value = "{id}")
    @ResponseBody
    public ResponseEntity getItemById(@PathVariable("id") Long id){
        return ResponseEntity.ok(itemService.getItemById(id));
    }
}

