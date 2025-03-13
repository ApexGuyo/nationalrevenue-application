package com.apex.revenue.national_revenue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
import jakarta.persistence.*;
//import javax.sql.DataSource;
//import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.ArrayList;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class NationalRevenueApplication {
	public static void main(String[] args) {
		SpringApplication.run(NationalRevenueApplication.class, args);
	}
}

@Entity
class Revenue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String category;
	private double amount;

	// ✅ Add this constructor
	public Revenue(String category, double amount) {
		this.category = category;
		this.amount = amount;
	}

	// ✅ Default constructor (needed by JPA)
	public Revenue() {
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}

interface RevenueRepository extends JpaRepository<Revenue, Long> {
}

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/revenue")
class RevenueController {
	@Autowired
	private RevenueRepository revenueRepository;

	@GetMapping("/all")
	public List<Revenue> getAllRevenue() {
		return revenueRepository.findAll();
	}

	// ✅ Add @Transactional to handle database commits properly
	@PostMapping("/add")
	@Transactional
	public String addKenyaRevenue() {
		try {
			List<Revenue> revenues = new ArrayList<>();
			revenues.add(new Revenue("Ordinary Revenue (Taxes)", 2570000000000L));
			revenues.add(new Revenue("Grants", 46700000000L));
			revenues.add(new Revenue("Loans (Domestic & External)", 894500000000L));
			revenues.add(new Revenue("National Government", 2220000000000L));
			revenues.add(new Revenue("County Governments", 385000000000L));
			revenues.add(new Revenue("Consolidated Fund Services", 995000000000L));

			revenueRepository.saveAll(revenues);
			System.out.println(" Revenue data added successfully!");

			return "Kenyan National Revenue data inserted successfully!";
		} catch (Exception e) {
			System.err.println(" Error inserting revenue data: " + e.getMessage());
			return "Error inserting revenue data.";
		}
	}
}
