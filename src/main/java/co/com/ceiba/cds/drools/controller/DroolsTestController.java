package co.com.ceiba.cds.drools.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import co.com.ceiba.cds.drools.model.Product;
import co.com.ceiba.cds.drools.service.DroolsTestService;


@RestController
public class DroolsTestController {
	
	private final DroolsTestService droolsTestService;

	@Autowired
	public DroolsTestController(DroolsTestService droolsTestService) {
		this.droolsTestService = droolsTestService;
	}

	@GetMapping(value = "/getDiscount/{type}")
	public Product getQuestions(@PathVariable String type) {
		Product product = new Product();
		product.setType(type);
		try {
			Thread.sleep(3000L);
			droolsTestService.getProductDiscount(product);
		} catch (InterruptedException ex) {
			System.err.println("Error");
		}
		
		return product;
	}

}
