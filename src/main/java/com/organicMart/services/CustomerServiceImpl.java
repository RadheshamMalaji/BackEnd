package com.organicMart.services;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.organicMart.dao.CartRepository;
import com.organicMart.dao.CategoryRepository;
import com.organicMart.dao.OrderAddressRepository;
import com.organicMart.dao.OrdersDetailsRepository;
import com.organicMart.dao.OrdersRepository;
import com.organicMart.dao.PaymentRepository;
import com.organicMart.dao.ProductRepository;
import com.organicMart.dao.UserRepository;
import com.organicMart.dto.PaymentDTO;
import com.organicMart.pojos.Cart;
import com.organicMart.pojos.Category;
import com.organicMart.pojos.OrderAddress;
import com.organicMart.pojos.OrderDetails;
import com.organicMart.pojos.OrderStatus;
import com.organicMart.pojos.Orders;
import com.organicMart.pojos.Payment;
import com.organicMart.pojos.PaymentStatus;
import com.organicMart.pojos.PaymentType;
import com.organicMart.pojos.Product;
import com.organicMart.pojos.Role;
import com.organicMart.pojos.User;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerServices {

	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private OrdersRepository ordersrepo;
	
	@Autowired
	private OrdersDetailsRepository ordersDetailsRepo;
	
	@Autowired
	private PaymentRepository paymentRepo;
	
	@Autowired
	private CategoryRepository catRepo;
	
	@Autowired
	private OrderAddressRepository orderAddressRepo;
	private static int deliveryBoyId;
	
	@Override
	public List<Product> getAllProduct(int categoryId) {
		List<Product> list = productRepo.getAllProduct(categoryId);
		List<Product> lt = new ArrayList<Product>();
		int index = 0;
		for(index = 0; index < list.size(); index++) {
			Product p = list.get(index);
			if(p.getQty() > 0)
				lt.add(p);
		}
		return lt;
	}
	
	@Override
	public List<Product> getAllProductlowtohigh(int categoryId) {
		List<Product> list = productRepo.getAllProductlowtohigh(categoryId);
		List<Product> lt = new ArrayList<Product>();
		int index = 0;
		for(index = 0; index < list.size(); index++) {
			Product p = list.get(index);
			if(p.getQty() > 0)
				lt.add(p);
		}
		return lt;
	}
	
	@Override
	public List<Product> getAllProducthightolow(int categoryId) {
		List<Product> list = productRepo.getAllProducthightolow(categoryId);
		List<Product> lt = new ArrayList<Product>();
		int index = 0;
		for(index = 0; index < list.size(); index++) {
			Product p = list.get(index);
			if(p.getQty() > 0)
				lt.add(p);
		}
		return lt;
	}
	
	@Override
	public Product getProductById(int productId) {
		Product p = productRepo.findById(productId).get();
		return p;
	}
	
	@Override
	public String addProductToCart(Cart c) {
		Cart cart = cartRepo.findByProductNameAndGrams(c.getProductName(), c.getGrams());
		if(cart != null) {
			int q = cart.getQty();
			cart.setQty(c.getQty() + q);;
		}else {
			Cart ct = cartRepo.save(c);
			if(ct != null)
			return "!!! Items Added to Cart !!!";
		}
		
		return "product not added";
	}
	
	@Override
	public List<Cart> getCartByuserId(int userId) {
		return cartRepo.getCartByuserId(userId);
	}
	
	@Override
	public Double getCartTotalAmt(int userId) {
		List<Cart> c = cartRepo.getCartByuserId(userId);
		double amt = 0;
		for (Cart cart : c) {
			amt = amt  + cart.getFinalPrice() * cart.getQty();
		}
		return amt;
	}
	 
	@Override
	public Double getCartTotalSavingAmt(int userId) {
		List<Cart> c = cartRepo.getCartByuserId(userId);
		double famt = 0, tamt = 0;
		for (Cart cart : c) {
			famt = famt + cart.getFinalPrice() * cart.getQty();
			tamt = tamt + cart.getPrice() * cart.getQty();
		}
		return (tamt - famt);
	}
	
	@Override
	public String deleteItemFromCart(int cartId) {
		cartRepo.deleteById(cartId);
		return "Items Deleted from Cart";
	}
	
	@Override
	public List<Product> getProductListByName(String productName) {
		List<Product> list = productRepo.getProductListByName(productName);
		List<Product> lt = new ArrayList<Product>();
		int index = 0;
		for(index = 0; index < list.size(); index++) {
			Product p = list.get(index);
			if(p.getQty() > 0)
				lt.add(p);
		}
		return lt;
	}
	public int addOrder(int userId, double totalPrice) {
		Orders order = new Orders();
		order.setDeliveryDate(LocalDate.now().plusDays(3));
		order.setOrderDeliveryStatus(OrderStatus.PENDING);
		order.setOrderDate(LocalDate.now());
		order.setTotalPrice(totalPrice);	
		order.setSelectedCustomer(userRepo.findById(userId).get());
		 List<Integer> list = userRepo.getAllDeliveryBoy(Role.DELIVERY_BOY);
		Random r = new Random();
		CustomerServiceImpl.deliveryBoyId = list.get(r.nextInt(list.size()));
		if(CustomerServiceImpl.deliveryBoyId == 0 )
			CustomerServiceImpl.deliveryBoyId = 1;
		System.out.println(CustomerServiceImpl.deliveryBoyId);
		order.setSelectedDeliveryBoy(userRepo.findById(CustomerServiceImpl.deliveryBoyId).get());	
		return ordersrepo.save(order).getId();
	}
	
	@Override
	public int addOrdersDetails(int userId, int orderId) {
		Orders orders = ordersrepo.findById(orderId).get();
		User user = userRepo.findById(userId).get();
		List<Cart> items = cartRepo.getCartByuserId(userId);
		for (Cart cart : items) {
			Product p = productRepo.findByProductNameAndGrams(cart.getProductName(), cart.getGrams());
			int qty = p.getQty() - cart.getQty();
			p.setQty(qty);
			OrderDetails od = new OrderDetails();
			od.setFinalPrice(cart.getFinalPrice());
			od.setGrams(cart.getGrams());
			od.setProductName(cart.getProductName());
			od.setQty(cart.getQty());
			od.setSelectedOrder(orders);
			od.setSelectedUser(user);
			ordersDetailsRepo.save(od);
		}
		
		cartRepo.deleteByUserId(userId);
		return CustomerServiceImpl.deliveryBoyId;
	}
	
	@Override
	public String addPayment(PaymentDTO paymentDTO) {
		Payment payment = new Payment();
		payment.setPaymentDate(LocalDate.now());
		payment.setPaymentStatus(PaymentStatus.PAID);
		if(paymentDTO.getPaymentType().equals("CREDIT"))
			payment.setPaymentType(PaymentType.CREDIT);
		if(paymentDTO.getPaymentType().equals("DEBIT"))
			payment.setPaymentType(PaymentType.DEBIT);
		User d = userRepo.findById(paymentDTO.getDeliveryBoyId()).get();
		Orders o = ordersrepo.findById(paymentDTO.getOrderId()).get();
		payment.setSelectedDeliveryBoyForPayment(d);
		payment.setSelectedOrder(o);
		paymentRepo.save(payment);
		return "Payment Done";
	}
	
	@Override
	public List<Orders> getOrdersList(int userId) {
		
		return ordersrepo.findByselectedCustomer(userRepo.findById(userId).get());
	}
	
	@Override
	public List<OrderDetails> getOrdersDetailsList(int orderId) {
		List<OrderDetails> list = ordersDetailsRepo.findBySelectedOrder(ordersrepo.findById(orderId).get());
		System.out.println("list : "+ list);
		return list;
	}
	
	@Override
	public String updateCartItems(int userId) {
		List<Cart> list = cartRepo.findByUserId(9999);
		for (Cart cart : list) {
			cart.setUserId(userId);
		}
		return "Cart Updated";
	}
	
	@Override
	public List<Orders> getOrdersListForDBoy(int deliveryBoyId) {
		return ordersrepo.findBySelectedDeliveryBoy(userRepo.findById(deliveryBoyId).get());
	}
	
	@Override
	public String deliveredOrder(int orderId) {
		Orders o = ordersrepo.findById(orderId).get();
		o.setOrderDeliveryStatus(OrderStatus.DELIVERED);
		o.setDeliveryDate(LocalDate.now());
		return "Order delivered";
	}
	
	@Override
	public List<Orders> getAllOrderList() {		
		return ordersrepo.findAll();
	}
	
	@Override
	public List<Category> getAllCategoryList() {
		return catRepo.findAll();
	}
	
	@Override
	public List<Product> getProductsForHomePage() {
		List<Product> list = productRepo.findAll();
		
		List<Product> lt = new ArrayList<Product>();
		int index = 0;
		for(index = 0; index < list.size(); index++) {
			Product p = list.get(index);
			if(p.getDiscount() >= 20)
				lt.add(p);
		}
		return lt;
	}
	
	@Override
	public int addOrderAddress(OrderAddress oa) {
		return orderAddressRepo.save(oa).getId();
	}
	
	@Override
	public String addOrderIdtoOrderAddress(int aId, String oId) {
		OrderAddress ao = orderAddressRepo.findById(aId).get();
		ao.setOrderId(oId);
		return "Done";
	}
	
	@Override
	public OrderAddress getOrderAddress(String oId) {
		System.out.println("getOrderAddress: "+ oId);
		return orderAddressRepo.findByOrderId(oId);
	}
}
