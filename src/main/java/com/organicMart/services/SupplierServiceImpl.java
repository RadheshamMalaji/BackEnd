package com.organicMart.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.organicMart.dao.AddressRepository;
import com.organicMart.dao.CategoryRepository;
import com.organicMart.dao.ProductRepository;
import com.organicMart.dao.SuppliedProductRepository;
import com.organicMart.dao.SupplierRepository;
import com.organicMart.dao.UserRepository;
import com.organicMart.pojos.Address;
import com.organicMart.pojos.Category;
import com.organicMart.pojos.Product;
import com.organicMart.pojos.SuppliedProduct;
import com.organicMart.pojos.Supplier;
import com.organicMart.pojos.User;

@Service
@Transactional
public class SupplierServiceImpl implements ISupplierService {

	@Autowired
	private SupplierRepository supplierRepository;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private SuppliedProductRepository suppliedProductRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AddressRepository addressrepo;
	
	@Override
	public List<?> getAllProductsBySupplier(int supplierId) {
		User u = userRepo.findById(supplierId).get();
		System.out.println("User : "+u);
		Supplier s = supplierRepository.findByCurrentUser(u);
		System.out.println("Supp : "+supplierRepository.findById(1));
		System.out.println("Supplier : "+s);
		Category c = categoryRepo.findById(s.getSupplierCategory().getId()).get();
		System.out.println("Category : "+c);
		return productRepo.findBySelectedCategory(c);
	}
	
	@Override
	public String getProductCategoryName(int supplierId) {
		User u = userRepo.findById(supplierId).get();
		System.out.println("User : "+u);
		Supplier s = supplierRepository.findByCurrentUser(u);
		System.out.println("Supp : "+supplierRepository.findById(1));
		System.out.println("Supplier : "+s);
		Category c = categoryRepo.findById(s.getSupplierCategory().getId()).get();
		System.out.println("Category : "+c);
		return c.getCategoryName();
	}
	

	@Override
	public Product getProductById(int productId) {
		return supplierRepository.getProductById(productId);
	}

	@Override
	public String updateProduct(int productId, Product products) {
		Product product = productRepo.findById(productId).get();
		product.setProductName(products.getProductName());
		product.setDescription(products.getDescription());
		product.setPrice(products.getPrice());
		product.setDiscount(products.getDiscount());
        product.setFinalPrice(products.getFinalPrice());
        product.setQty(products.getQty());
        product.setGrams(products.getGrams());
		return "product updated";
	}

	@Override
	public Product addProduct(String CategoryName, Product product) {
		Category c = categoryRepo.findByCategoryName(CategoryName);
		product.setSelectedCategory(c);
		Product p = productRepo.findByProductNameAndGrams(product.getProductName(), product.getGrams());
		if(p == null) {
			return productRepo.save(product);
		} else {
			int qty = p.getQty() + product.getQty();
			p.setQty(qty);
			return productRepo.saveAndFlush(p);
		}
	}
	
	@Override
	public SuppliedProduct addProductBySupplier(String CategoryName, SuppliedProduct suppliedProduct) {
		Category c = categoryRepo.findByCategoryName(CategoryName);
		suppliedProduct.setSuppliedCategory(c);
		return suppliedProductRepo.save(suppliedProduct);
	}

	@Override
	public String deleteProduct(int productId) {
		productRepo.deleteById(productId);
		return "Product Deleted";
	}

	@Override
	public List<SuppliedProduct> getSuppliedProducts(int supplierId) {
		User u = userRepo.findById(supplierId).get();
		System.out.println("User : "+u);
		Supplier s = supplierRepository.findByCurrentUser(u);
		System.out.println("Supp : "+supplierRepository.findById(1));
		System.out.println("Supplier : "+s);
		Category c = categoryRepo.findById(s.getSupplierCategory().getId()).get();
		return suppliedProductRepo.findBySuppliedCategory(c);
	}
	
	@Override
	public String deleteFromSuppliedProducts(int productId) {
		suppliedProductRepo.deleteById(productId);
		return "Product Deleted";
	}

	@Override
	public String addAddress(int userId, Address address) {
		Address add = addressrepo.findById(userId).get();
		System.out.println("address : "+add);
		if(add != null) {
		add.setArea(address.getArea());
		add.setCity(address.getCity());
		add.setFlatNo(address.getFlatNo());
		add.setPinCode(address.getPinCode());
		add.setSocietyName(address.getSocietyName());
		add.setState(address.getState());
		}
		return "Address added Succefully";
	}
	
}
