package com.organicMart.services;

import org.springframework.web.multipart.MultipartFile;

import com.organicMart.pojos.ProductImage;

public interface IProductImageService {
	ProductImage findByProductName(String pName, MultipartFile imageFile);
	
	ProductImage getImageByName(String productName);
}
