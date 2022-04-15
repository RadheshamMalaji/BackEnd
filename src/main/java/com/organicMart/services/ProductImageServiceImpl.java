package com.organicMart.services;

import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.organicMart.dao.ProductImageRepository;
import com.organicMart.pojos.ProductImage;

@Service
@Transactional
public class ProductImageServiceImpl implements IProductImageService {
	@Autowired
	private ProductImageRepository imgRepo;
	
	@Override
	public ProductImage findByProductName(String pName, MultipartFile imageFile) {
		try {
			ProductImage i = imgRepo.findByProductName(pName);
			i.setImage(imageFile.getBytes());
			i.setImageContentType(imageFile.getContentType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ProductImage getImageByName(String productName) {
		return imgRepo.findByProductName(productName);
	}
}
