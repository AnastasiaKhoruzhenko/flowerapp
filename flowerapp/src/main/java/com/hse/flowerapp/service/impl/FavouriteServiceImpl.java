package com.hse.flowerapp.service.impl;

import com.hse.flowerapp.domain.Favourite;
import com.hse.flowerapp.repository.FavouriteRepository;
import com.hse.flowerapp.service.FavouriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FavouriteServiceImpl implements FavouriteService {

    private final FavouriteRepository favouriteRepository;

    @Autowired
    public FavouriteServiceImpl(FavouriteRepository favouriteRepository){
        this.favouriteRepository = favouriteRepository;
    }
    @Override
    public Favourite getFavById(Long id) {
        return favouriteRepository.findById(id).orElse(new Favourite());
    }
}
