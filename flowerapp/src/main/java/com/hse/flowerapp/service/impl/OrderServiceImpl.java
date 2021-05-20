package com.hse.flowerapp.service.impl;

import com.hse.flowerapp.domain.*;
import com.hse.flowerapp.dto.ChangeOrderStatusDto;
import com.hse.flowerapp.dto.ItemDto;
import com.hse.flowerapp.dto.OrderDto;
import com.hse.flowerapp.repository.*;
import com.hse.flowerapp.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShopRepository shopRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ItemRepository itemRepository, UserRepository userRepository, OrderItemRepository orderItemRepository, ShopRepository shopRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
        this.shopRepository = shopRepository;
    }

    @Override
    public OrderDto getItemById(Long order_id) {
        return OrderDto.convertToOrderDto(orderRepository.getById(order_id));
    }

    @Override
    public List<OrderDto> getOrdersByUserId(Long user_id) {
        return null;
    }

    @Override
    public Order createOrder(OrderDto orderDto) {
        User user = userRepository.getById(orderDto.getUserId());

        Order newOrder = new Order();

        newOrder.setTotalSum(orderDto.getTotalSum());
        newOrder.setOrderStatus(OrderStatus.CREATED);
        newOrder.setSecretDelivery(orderDto.getSecretDelivery());
        newOrder.setDeliveryPrice(orderDto.getDeliveryPrice());
        newOrder.setName(orderDto.getName());
        newOrder.setPhone(orderDto.getPhone());
        newOrder.setEmail(orderDto.getEmail());
        newOrder.setAddPostcard(orderDto.getAddPostcard());
        newOrder.setSayItIsFlower(orderDto.getSayItIsFlower());
        newOrder.setSayFrom(orderDto.getSayFrom());
        newOrder.setCallToConfirm(orderDto.getCallToConfirm());

        newOrder.setTown(orderDto.getTown());
        newOrder.setStreet(orderDto.getStreet());
        newOrder.setHouse(orderDto.getHouse());

        Long shop_id = itemRepository.getItemById(orderDto.getListIds().get(0)).getId();
        newOrder.setShopId(shop_id);

        Date date = new Date();
        newOrder.setUpdated(date);
        newOrder.setCreated(date);

        if(orderDto.getPromocode() != null)
            newOrder.setPromocode(orderDto.getPromocode());
        if(orderDto.getDiscountSum() != null)
            newOrder.setDiscountSum(orderDto.getDiscountSum());
        if(orderDto.getSecretName() != null)
            newOrder.setSectretName(orderDto.getSecretName());
        if(orderDto.getSecretPhone() != null)
            newOrder.setSecretPhone(orderDto.getSecretPhone());
        if(orderDto.getPostcardText() != null)
            newOrder.setPostcardText(orderDto.getPostcardText());

        if(orderDto.getBuilding() != null)
            newOrder.setBuilding(orderDto.getBuilding());
        if(orderDto.getFlat() != null)
            newOrder.setFlat(orderDto.getFlat());

        orderRepository.save(newOrder);

        for(Long id: orderDto.getListIds()){
            int index = orderDto.getListIds().indexOf(id);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(itemRepository.getItemById(id));
            orderItem.setCountOfThisItemInOrder(orderDto.getListCount().get(index));
            orderItem.setOrder(newOrder);
            orderItemRepository.save(orderItem);

            List<OrderItem> orderItemList1 = newOrder.getCountItemInOrder();
            orderItemList1.add(orderItem);
            newOrder.setCountItemInOrder(orderItemList1);

            List<OrderItem> orderItemList2 = itemRepository.getItemById(id).getCountItemInOrders();
            orderItemList2.add(orderItem);
            itemRepository.getItemById(Long.valueOf(id)).setCountItemInOrders(orderItemList2);
            itemRepository.save(itemRepository.getItemById(Long.valueOf(id)));
        }

        List<Order> orderItemList = user.getOrderList();
        orderItemList.add(newOrder);
        user.setOrderList(orderItemList);
        userRepository.save(user);

        newOrder.setUser(user);

        return newOrder;
    }

    @Override
    public OrderDto cancelOrder(OrderDto orderDto) {
        return null;
    }

    @Override
    public List<OrderDto> getAllShopOrders(Long id) {
        List<Order> orderList = new ArrayList<>();
        Shop shop = shopRepository.getShopById(id);
        orderList = orderRepository.findAll();
        orderList.removeIf(order -> !order.getShopId().equals(id));

        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order order : orderList) {
            orderDtoList.add(OrderDto.convertToOrderDto(order));
        }

        return orderDtoList;
    }

    @Override
    public OrderDto changeOrderStatus(Long order_id, OrderStatus orderStatus) {
        Order order = orderRepository.getById(order_id);
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        return OrderDto.convertToOrderDto(order);
    }

    @Override
    public OrderDto takeOrder(Long order_id, Integer seller_id) {
        Order order = orderRepository.getById(order_id);
        order.setOrderStatus(OrderStatus.SELLERADDED);
        order.setSellerId(seller_id);
        orderRepository.save(order);
        return OrderDto.convertToOrderDto(order);
    }

    @Override
    public List<ItemDto> getAllOrderItems(Long order_id) {
        Order order = orderRepository.getById(order_id);
        List<OrderItem> itemList = order.getCountItemInOrder();
        List<ItemDto> itemDtoList = new ArrayList<>();
        for (OrderItem orderItem: itemList) {
            itemDtoList.add(ItemDto.convertToDTO(orderItem.getItem()));
        }

        return itemDtoList;
    }
}
