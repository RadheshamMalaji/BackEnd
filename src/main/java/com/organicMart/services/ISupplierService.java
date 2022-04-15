package com.organicMart.services;

import java.util.List;

import com.organicMart.pojos.Address;
import com.organicMart.pojos.Product;
import com.organicMart.pojos.SuppliedProduct;

public interface ISupplierService {
	List<?> getAllProductsBySupplier(int supplierId);	

	String getProductCategoryName(int supplierId);	
	
	Product getProductById(int productId);
	
	String updateProduct(int productId, Product product);
	
	Product addProduct(String CategoryName, Product product);
	
	SuppliedProduct addProductBySupplier(String CategoryName, SuppliedProduct product);
	
	String deleteProduct(int productId);
	
	List<SuppliedProduct> getSuppliedProducts(int supplierId);
	
	String deleteFromSuppliedProducts(int productId);
	
	String addAddress(int userId, Address address);
}
