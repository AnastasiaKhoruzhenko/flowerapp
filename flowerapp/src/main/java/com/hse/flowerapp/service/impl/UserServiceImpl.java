package com.hse.flowerapp.service.impl;

import com.hse.flowerapp.domain.*;
import com.hse.flowerapp.dto.AddressDto;
import com.hse.flowerapp.repository.*;
import com.hse.flowerapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final RoleRepository roleRepository;
    private final ReviewRepository reviewRepository;
    private final ShopRepository shopRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                           ItemRepository itemRepository, OrderRepository orderRepository,
                           RoleRepository roleRepository, ReviewRepository reviewRepository, ShopRepository shopRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
        this.roleRepository = roleRepository;
        this.reviewRepository = reviewRepository;
        this.shopRepository = shopRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public User register(User user, String role) {
        Date date = new Date();
        List<Order> orderList = new ArrayList<>();
        List<Review> reviewList = new ArrayList<>();

        User user1 = findByEmail(user.getEmail());
        if(user1!=null)
            return null;

        // Если продавец
        switch (role) {
            case "ROLE_SELLER": {
                List<Role> roleList = new ArrayList<>();
                Role roleUser = roleRepository.findByName(role);
                roleList.add(roleUser);

                user.setPassword(passwordEncoder.encode(user.getPassword()));

                user.setOrderList(orderList);
                user.setReviewList(reviewList);
                //customer.setItemList(itemList);
                user.setStatus(Status.ACTIVE);
                user.setCreated(date);
                user.setUpdated(date);
                user.setBonuses(0);
                user.setAllowPush(true);
                //customer.setUser_rate(0);
                //customer.setShopName(null);
                //customer.setCountry(null);
                user.setRoleList(roleList);

                //todo: Проверка пароля и логина

                User regUser = userRepository.save(user);
                log.info("IN register user SELLER was registered successfully:");
                return regUser;
            }
            case "ROLE_USER": {
                Role roleUser = roleRepository.findByName("ROLE_USER");

                Cart cart = new Cart();
                cart.setTotalSum(0);
                cart.setDiscountSum(0);
                cart.setTotalCount(0);

                user.setCart(cart);
                cart.setUser(user);
                List<Role> roleList = new ArrayList<>();
                roleList.add(roleUser);
                user.setRoleList(roleList);

                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setOrderList(orderList);
                user.setReviewList(reviewList);
                user.setStatus(Status.ACTIVE);
                user.setCreated(date);
                user.setUpdated(date);
                user.setBonuses(0);
                user.setAllowPush(true);

                //todo: Проверка пароля и логина

                User regUser = userRepository.save(user);
                log.info("IN register user USER was registered successfully:");
                return regUser;
            }
            case "ROLE_OWNER": {
                List<Role> roleList = new ArrayList<>();
                Role roleUser = roleRepository.findByName(role);
                roleList.add(roleUser);

                user.setPassword(passwordEncoder.encode(user.getPassword()));

                user.setOrderList(orderList);
                user.setReviewList(reviewList);
                user.setStatus(Status.ACTIVE);
                user.setCreated(date);
                user.setUpdated(date);
                user.setBonuses(0);
                user.setAllowPush(true);
                user.setRoleList(roleList);

                //todo: Проверка пароля и логина

                User regUser = userRepository.save(user);
                log.info("IN register user OWNER was registered successfully:");
                return regUser;
            }
            case "ROLE_ADMIN":
                break;
            default:
                break;
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();

        log.info("Users were found successfully IN getAll");

        return result;
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.getById(id);

        if (user == null)
            log.info("IN findById user with id: {} do not exist", id);
        else
            log.info("IN findById user was found successfully: {}", user.toString());

        return user;
    }

    @Override
    public User findByEmail(String name) {
        User user = userRepository.findByEmail(name);

        return user;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN delete user with id: {} was deleted successfully", id);
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        User user = userRepository.getById(userId);

        if (userId == null) {
            log.info("IN getUserOrders user with id: {} do not exist", userId);
        }

        return user.getOrderList();
    }

    @Override
    public List<Review> getUserReview(Long userId) {
        User user = userRepository.getById(userId);

        if (userId == null) {
            log.info("IN getUserReview user with id: {} do not exist", userId);
        }

        return user.getReviewList();
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public User addShopToUser(User user) {
        Shop shop = new Shop();
        Date date = new Date();
        shop.setUser(user);
        shop.setCreated(date);
        shop.setUpdated(date);
        shop.setItemCount(0);
        shop.setStatus(Status.CREATED);
        user.setShop(shop);

        shopRepository.save(shop);

        return user;
    }

    @Override
    public List<Address> getUserAddresses(Long id) {
        return userRepository.getById(id).getAddressList();
    }

    @Override
    public void deleteUserAddress(Long user_id, Address address) {
        User user = userRepository.getById(user_id);
        List<Address> addressList = user.getAddressList();
        addressList.remove(address);
        user.setAddressList(addressList);

        userRepository.save(user);
    }

    @Override
    public Address createPersonalAddress(AddressDto address) {
        Address newAddress = convertDtoToEntity(address);
        newAddress.setAddressType(AddressType.PERSONAL_ADDRESS);

        List<User> userList = newAddress.getUserList();
        userList.add(userRepository.getById(address.getUserId()));
        newAddress.setUserList(userList);
        addressRepository.save(newAddress);

        User user = userRepository.getById(address.getUserId());
        List<Address> addressList = user.getAddressList();
        addressList.add(newAddress);
        user.setAddressList(addressList);
        userRepository.save(user);

        return newAddress;
    }

    @Override
    public Address createShopAddress(AddressDto address) {
        log.info(address.getHouseBuilding());
        Address newAddress = convertDtoToEntity(address);
        log.info(newAddress.getHouseBuilding());
        newAddress.setAddressType(AddressType.SHOP_ADDRESS);
        addressRepository.save(newAddress);

        log.info("shop address " + address.getShopId().toString());
        // не работает
        Shop shop = shopRepository.getShopById(address.getShopId());
        shop.setShopAddress(newAddress);
        shopRepository.save(shop);

        newAddress.setShop(shop);

        return newAddress;
    }

    public Address convertDtoToEntity(AddressDto addressDto){
        Address newAddress = new Address();
        newAddress.setRegion(addressDto.getRegion());
        newAddress.setTown(addressDto.getTown());
        newAddress.setStreet(addressDto.getStreet());
        newAddress.setHouse(addressDto.getHouse());

        log.info("conc=vert to entity " + addressDto.getHouseBuilding());
        if(addressDto.getHouseBuilding() != null)
            newAddress.setHouseBuilding(addressDto.getHouseBuilding());

        if(addressDto.getFlat() != null)
            newAddress.setFlat(addressDto.getFlat());

        return newAddress;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
