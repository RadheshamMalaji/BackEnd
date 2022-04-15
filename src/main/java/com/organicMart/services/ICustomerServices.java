package com.organicMart.services;

import java.util.List;

import com.organicMart.dto.PaymentDTO;
import com.organicMart.pojos.Cart;
import com.organicMart.pojos.Category;
import com.organicMart.pojos.OrderAddress;
import com.organicMart.pojos.OrderDetails;
import com.organicMart.pojos.Orders;
import com.organicMart.pojos.Product;

public interface ICustomerServices {
	List<Product> getAllProduct(int categoryId);
	
	List<Product> getAllProductlowtohigh(int categoryId);
	
	List<Product> getAllProducthightolow(int categoryId);
	
	Product getProductById(int productId);
	
	String addProductToCart(Cart c);
	
	List<Cart> getCartByuserId(int userId);
	
	Double getCartTotalSavingAmt(int userId);
	
	Double getCartTotalAmt(int userId);
	
	String deleteItemFromCart(int cartId);
	
	List<Product> getProductListByName(String productName);
	
	int addOrder(int userId, double totalPrice);
	
	int addOrdersDetails(int userId, int OrderId);
	
	String addPayment(PaymentDTO paymentDTO);
	
	List<Orders> getOrdersList(int userId);
	
	List<Orders> getOrdersListForDBoy(int deliveryBoyId);
	
	List<OrderDetails> getOrdersDetailsList(int orderId);
	
	String updateCartItems(int userId);
	
	String deliveredOrder(int orderId);
	
	List<Orders> getAllOrderList();
	
	List<Category> getAllCategoryList();
	
	List<Product> getProductsForHomePage();
	
	int addOrderAddress(OrderAddress oa);
	
	OrderAddress getOrderAddress(String oId);

	String addOrderIdtoOrderAddress(int aId, String oId);
}
