package com.clothstore.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.clothstore.dao.CartDao;
import com.clothstore.dao.OrderDao;
import com.clothstore.dao.ProductDao;
import com.clothstore.dao.UserDao;
import com.clothstore.dto.MyOrderResponse;
import com.clothstore.dto.UpdateDeliveryStatusRequest;
import com.clothstore.model.Cart;
import com.clothstore.model.Orders;
import com.clothstore.model.Product;
import com.clothstore.model.User;
import com.clothstore.utility.Helper;
import com.clothstore.utility.Constants.DeliveryStatus;
import com.clothstore.utility.Constants.DeliveryTime;
import com.clothstore.utility.Constants.IsDeliveryAssigned;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
    private OrderDao orderDao;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDao productDao;

    private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public ResponseEntity<?> customerOrder(int userId) throws JsonProcessingException {
		
		try {
            String orderId = Helper.getAlphaNumericOrderId();
            List<Cart> userCarts = cartDao.findByUser_id(userId);

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formatDateTime = currentDateTime.format(formatter);

            for (Cart cart : userCarts) {
                Orders order = new Orders();
                order.setOrderId(orderId);
                order.setUser(cart.getUser());
                order.setProduct(cart.getProduct());
                order.setQuantity(cart.getQuantity());
                order.setOrderDate(formatDateTime);
                order.setDeliveryDate(DeliveryStatus.PENDING.value());
                order.setDeliveryStatus(DeliveryStatus.PENDING.value());
                order.setDeliveryTime(DeliveryTime.DEFAULT.value());
                order.setDeliveryAssigned(IsDeliveryAssigned.NO.value());

                orderDao.save(order);

                Product product = cart.getProduct();
                int quantity = cart.getQuantity();
                int newQuantity = product.getQuantity() - quantity;
                product.setQuantity(newQuantity);
                productDao.save(product);

                cartDao.delete(cart);
            }

            return ResponseEntity.ok("ORDER SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ORDER FAILED");
        }
    }
	
	
	
	@Override
	public ResponseEntity<List<MyOrderResponse>> getMyOrders(int userId) {
        List<Orders> userOrder = orderDao.findByUser_id(userId);
        List<MyOrderResponse> orderDatas = new ArrayList<>();

        for (Orders order : userOrder) {
            MyOrderResponse orderData = new MyOrderResponse();
            orderData.setOrderId(order.getOrderId());
            orderData.setProductDescription(order.getProduct().getDescription());
            orderData.setProductName(order.getProduct().getTitle());
            orderData.setProductImage(order.getProduct().getImageName());
            orderData.setQuantity(order.getQuantity());
            orderData.setOrderDate(order.getOrderDate());
            orderData.setProductId(order.getProduct().getId());
            orderData.setDeliveryDate(order.getDeliveryDate() + " " + order.getDeliveryTime());
            orderData.setDeliveryStatus(order.getDeliveryStatus());
            orderData.setTotalPrice(
                    String.valueOf(order.getQuantity() * Double.parseDouble(order.getProduct().getPrice().toString())));

            if (order.getDeliveryPersonId() == 0) {
                orderData.setDeliveryPersonContact(DeliveryStatus.PENDING.value());
                orderData.setDeliveryPersonName(DeliveryStatus.PENDING.value());
            } else {
                User deliveryPerson = userDao.findById(order.getDeliveryPersonId()).orElse(null);
                if (deliveryPerson != null) {
                    orderData.setDeliveryPersonContact(deliveryPerson.getPhoneNo());
                    orderData.setDeliveryPersonName(deliveryPerson.getFirstName());
                }
            }
            orderDatas.add(orderData);
        }
        return ResponseEntity.ok(orderDatas);
    }	

	@Override
	public ResponseEntity<List<MyOrderResponse>> getAllOrders() {
        List<Orders> userOrder = orderDao.findAll();
        List<MyOrderResponse> orderDatas = new ArrayList<>();

        for (Orders order : userOrder) {
            MyOrderResponse orderData = createMyOrderResponse(order);
            orderDatas.add(orderData);
        }

        return ResponseEntity.ok(orderDatas);
    }
	
	
	//Common Method.........Called in many classes.
	private MyOrderResponse createMyOrderResponse(Orders order) {
        MyOrderResponse orderData = new MyOrderResponse();
        orderData.setOrderId(order.getOrderId());
        orderData.setProductDescription(order.getProduct().getDescription());
        orderData.setProductName(order.getProduct().getTitle());
        orderData.setProductImage(order.getProduct().getImageName());
        orderData.setQuantity(order.getQuantity());
        orderData.setOrderDate(order.getOrderDate());
        orderData.setProductId(order.getProduct().getId());
        orderData.setDeliveryDate(order.getDeliveryDate() + " " + order.getDeliveryTime());
        orderData.setDeliveryStatus(order.getDeliveryStatus());
        orderData.setTotalPrice(
                String.valueOf(order.getQuantity() * Double.parseDouble(order.getProduct().getPrice().toString())));

        if (order.getDeliveryPersonId() == 0) {
            orderData.setDeliveryPersonContact(DeliveryStatus.PENDING.value());
            orderData.setDeliveryPersonName(DeliveryStatus.PENDING.value());
        } else {
            User deliveryPerson = userDao.findById(order.getDeliveryPersonId()).orElse(null);
            if (deliveryPerson != null) {
                orderData.setDeliveryPersonContact(deliveryPerson.getPhoneNo());
                orderData.setDeliveryPersonName(deliveryPerson.getFirstName());
            }
        }

        orderData.setUserId(order.getUser().getId());
        orderData.setUserName(order.getUser().getFirstName() + " " + order.getUser().getLastName());
        orderData.setUserPhone(order.getUser().getPhoneNo());
        orderData.setAddress(order.getUser().getAddress());

        return orderData;
    }	
	
	

	@Override
	public ResponseEntity<List<MyOrderResponse>> getOrdersByOrderId(String orderId) {
        List<Orders> userOrder = orderDao.findByOrderId(orderId);
        List<MyOrderResponse> orderDatas = new ArrayList<>();

        for (Orders order : userOrder) {
            MyOrderResponse orderData = createMyOrderResponse(order);
            orderDatas.add(orderData);
        }

        return ResponseEntity.ok(orderDatas);
    }

	@Override
	public ResponseEntity<List<MyOrderResponse>> updateOrderDeliveryStatus(UpdateDeliveryStatusRequest deliveryRequest)
      {
        List<Orders> orders = orderDao.findByOrderId(deliveryRequest.getOrderId());

        for (Orders order : orders) {
            order.setDeliveryDate(deliveryRequest.getDeliveryDate());
            order.setDeliveryStatus(deliveryRequest.getDeliveryStatus());
            order.setDeliveryTime(deliveryRequest.getDeliveryTime());
            orderDao.save(order);
        }

        List<Orders> userOrder = orderDao.findByOrderId(deliveryRequest.getOrderId());

        List<MyOrderResponse> orderDatas = new ArrayList<>();

        for (Orders order : userOrder) {
            MyOrderResponse orderData = createMyOrderResponse(order);
            orderDatas.add(orderData);
        }

        return ResponseEntity.ok(orderDatas);
    }


	@Override
	public ResponseEntity<List<MyOrderResponse>> assignDeliveryPersonForOrder(
            UpdateDeliveryStatusRequest deliveryRequest) {
        List<Orders> orders = orderDao.findByOrderId(deliveryRequest.getOrderId());

        User deliveryPerson = null;

        Optional<User> optionalDeliveryPerson = userDao.findById(deliveryRequest.getDeliveryId());

        if (optionalDeliveryPerson.isPresent()) {
            deliveryPerson = optionalDeliveryPerson.get();
        }

        for (Orders order : orders) {
            order.setDeliveryAssigned(IsDeliveryAssigned.YES.value());
            order.setDeliveryPersonId(deliveryRequest.getDeliveryId());
            orderDao.save(order);
        }

        List<Orders> userOrder = orderDao.findByOrderId(deliveryRequest.getOrderId());

        List<MyOrderResponse> orderDatas = new ArrayList<>();

        for (Orders order : userOrder) {
            MyOrderResponse orderData = createMyOrderResponse(order);
            if (order.getDeliveryPersonId() == 0) {
                orderData.setDeliveryPersonContact(DeliveryStatus.PENDING.value());
                orderData.setDeliveryPersonName(DeliveryStatus.PENDING.value());
            } else {
                User dPerson = userDao.findById(order.getDeliveryPersonId()).orElse(null);
                if (dPerson != null) {
                    orderData.setDeliveryPersonContact(dPerson.getPhoneNo());
                    orderData.setDeliveryPersonName(dPerson.getFirstName());
                }
            }
            orderDatas.add(orderData);
        }

        return ResponseEntity.ok(orderDatas);
    }

	@Override
	public ResponseEntity<List<MyOrderResponse>> getMyDeliveryOrders(int deliveryPersonId)
            {
        User person = userDao.findById(deliveryPersonId).orElse(null);

        List<Orders> userOrder = orderDao.findByDeliveryPersonId(deliveryPersonId);

        List<MyOrderResponse> orderDatas = new ArrayList<>();

        for (Orders order : userOrder) {
            MyOrderResponse orderData = createMyOrderResponse(order);
            if (order.getDeliveryPersonId() == 0) {
                orderData.setDeliveryPersonContact(DeliveryStatus.PENDING.value());
                orderData.setDeliveryPersonName(DeliveryStatus.PENDING.value());
            } else {
                if (person != null) {
                    orderData.setDeliveryPersonContact(person.getPhoneNo());
                    orderData.setDeliveryPersonName(person.getFirstName());
                }
            }
            orderDatas.add(orderData);
        }

        return ResponseEntity.ok(orderDatas);
    }

}
