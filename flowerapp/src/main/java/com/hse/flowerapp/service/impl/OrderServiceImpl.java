package com.hse.flowerapp.service.impl;

import com.hse.flowerapp.domain.Order;
import com.hse.flowerapp.domain.OrderItem;
import com.hse.flowerapp.domain.OrderStatus;
import com.hse.flowerapp.domain.User;
import com.hse.flowerapp.dto.OrderDto;
import com.hse.flowerapp.repository.ItemRepository;
import com.hse.flowerapp.repository.OrderItemRepository;
import com.hse.flowerapp.repository.OrderRepository;
import com.hse.flowerapp.repository.UserRepository;
import com.hse.flowerapp.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ItemRepository itemRepository, UserRepository userRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderDto getItemById(Long order_id) {
        return null;
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

        if(orderDto.getPromocode() != null)
            newOrder.setPromocode(orderDto.getPromocode());
        if(orderDto.getDiscountSum() != null)
            newOrder.setDiscountSum(orderDto.getDiscountSum());
        if(orderDto.getSectretName() != null)
            newOrder.setSectretName(orderDto.getSectretName());
        if(orderDto.getSecretPhone() != null)
            newOrder.setSecretPhone(orderDto.getSecretPhone());
        if(orderDto.getPostcardText() != null)
            newOrder.setPostcardText(orderDto.getPostcardText());

        if(orderDto.getBuilding() != null)
            newOrder.setBuilding(orderDto.getBuilding());
        if(orderDto.getFlat() != null)
            newOrder.setFlat(orderDto.getFlat());

        orderRepository.save(newOrder);

        //log.info("khor " + orderDto.getListItemIds().toString());

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

//        for (Map.Entry<String, String> item: orderDto.getListItemIds().entrySet()) {
//            OrderItem orderItem = new OrderItem();
//            orderItem.setItem(itemRepository.getItemById(Long.valueOf(item.getKey())));
//            orderItem.setCountOfThisItemInOrder(Integer.valueOf(item.getValue()));
//            orderItem.setOrder(newOrder);
//            orderItemRepository.save(orderItem);
//
//            List<OrderItem> orderItemList1 = newOrder.getCountItemInOrder();
//            orderItemList1.add(orderItem);
//            newOrder.setCountItemInOrder(orderItemList1);
//            orderRepository.save(newOrder);
//
//            List<OrderItem> orderItemList2 = itemRepository.getItemById(Long.valueOf(item.getKey())).getCountItemInOrders();
//            orderItemList2.add(orderItem);
//            itemRepository.getItemById(Long.valueOf(item.getKey())).setCountItemInOrders(orderItemList2);
//            itemRepository.save(itemRepository.getItemById(Long.valueOf(item.getKey())));
//        }
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
}
