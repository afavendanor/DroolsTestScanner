package co.com.ceiba.cds.drools.service;

import java.util.Date;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.cds.drools.model.Product;

@Service
public class DroolsTestService {

	private KieContainer kieContainer;

	@Autowired
	public DroolsTestService(KieContainer kieContainer) {
		this.kieContainer = kieContainer;
	}

	public Product getProductDiscount(Product product) {

		while (true) {
			KieSession kSession = kieContainer.newKieSession();
			kSession.insert(product);
			int rules = kSession.fireAllRules();

			System.err.println("***Rules : " + rules + " - " + new Date());
			System.err.println("*** Price of product = " + product.getPrice());
			kSession.dispose();

			return product;
		}

	}

}
