package com.hse.flowerapp.service.impl;

import com.hse.flowerapp.domain.Address;
import com.hse.flowerapp.repository.AddressRepository;
import com.hse.flowerapp.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }


//    @Override
//    public Address getById(Long id) {
//        return addressRepository.findById(id);
//    }

    @Override
    public void saveAddress(Address address) {
        addressRepository.save(address);
    }
}
