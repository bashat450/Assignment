package com.javatechie.jpa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javatechie.jpa.demo.Entity.Product;
import com.javatechie.jpa.demo.Repository.ProductRepo;

@RestController
@RequestMapping("/api")
public class ProductController {
	@Autowired
	ProductRepository productRepo;
	// this method is for creating new product
	@PostMapping("/productt")
	public String createNewProduct(@RequestBody Product product)
	{
		ProductRepository.save(product);
		return "Product Created in database";
	}
	//this method is for getting all product
	@GetMapping("/Product")
	public ResponseEntity<List<Product>> getAllProduct()
	{
		List<Product> plist=new ArrayList<>();
		ProductRepository.findAll().forEach(plist::add);
		return new ResponseEntity<List<Product>>(plist,HttpStatus.OK);
	}
	// this method is for gtting a single product
	@GetMapping("/Product/{productid}")
	public ResponseEntity<Product> getProductById(@PathVariable int productid)
	{
		Optional<Product> pd=ProductRepository.findById(productid);
	 if(pd.isPresent())
	 {
		 return new ResponseEntity<Product>(pd.get(),HttpStatus.FOUND);
	 }
	 else
	 {
		 return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
	 }
	}
	//this method is for updting the product
	@PutMapping("/Product/{productid}")
	public String UpdateProductById(@PathVariable int productid, @RequestBody Product product)
	{
		Optional<Product>pd=ProductRepository.findById(productid);
		if(pd.isPresent())
		{
			Product existpd=pd.get();
			existpd.setProduct(product.getProduct());
			existpd.setQuantity(product.getQuantity());
			existpd.setPrice(product.getPrice());
			ProductRepository.save(existpd);
			return "Product Details aganst Id"+ productid+"Updated";
		}
		else
		{
			return "Product Details does not exist for id"+ productid;
		}
	}
	//this  method is for deleting the product
	@DeleteMapping("/productt/{productid}")
	public String DeleteProductById(@PathVariable int productid)
	{
		ProductRepository.deleteById(productid);
		return "Product Deleted";
	}
}
