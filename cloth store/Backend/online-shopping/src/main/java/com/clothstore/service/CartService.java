package com.clothstore.service;

import com.clothstore.dto.AddToCartRequest;
import com.clothstore.dto.CartResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface CartService {
	
	public void addToCart(AddToCartRequest addToCartRequest);
	
	public CartResponse getMyCart(int userId) throws JsonProcessingException;
	
	public void removeCartItem(int cartId);

}
