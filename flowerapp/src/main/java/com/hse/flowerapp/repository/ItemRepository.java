package com.hse.flowerapp.repository;

import com.hse.flowerapp.domain.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

    Item getItemById(Long id);
}
