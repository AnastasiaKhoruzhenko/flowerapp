package com.hse.flowerapp.repository;


import com.hse.flowerapp.domain.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
