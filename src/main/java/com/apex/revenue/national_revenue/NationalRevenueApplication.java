package com.apex.revenue.national_revenue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.persistence.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.ArrayList;

@SpringBootApplication
public class NationalRevenueApplication {
	public static void main(String[] args) {
		SpringApplication.run(NationalRevenueApplication.class, args);
	}
}

// Revenue Entity
@Entity
class Revenue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String category;
	private double amount;

	public Revenue(String category, double amount) {
		this.category = category;
		this.amount = amount;
	}

	public Revenue() {
	} // Default constructor (Required by JPA)

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

// ✅ Repository
interface RevenueRepository extends JpaRepository<Revenue, Long> {
}

// ✅ Controller
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/revenue")
class RevenueController {
	@Autowired
	private RevenueRepository revenueRepository;

	// ✅ GET: Fetch all revenue data
	@GetMapping("/all")
	public List<Revenue> getAllRevenue() {
		return revenueRepository.findAll();
	}

	// ✅ POST: Insert revenue data
	@PostMapping("/add")
	@Transactional
	public String addKenyaRevenue() {
		try {
			List<Revenue> revenues = new ArrayList<>();
			revenues.add(new Revenue("Ordinary Revenue (Taxes)", 2570000000000.0));
			revenues.add(new Revenue("Grants", 46700000000.0));
			revenues.add(new Revenue("Loans (Domestic & External)", 894500000000.0));
			revenues.add(new Revenue("National Government", 2220000000000.0));
			revenues.add(new Revenue("County Governments", 385000000000.0));
			revenues.add(new Revenue("Consolidated Fund Services", 995000000000.0));

			revenueRepository.saveAll(revenues);
			System.out.println("Revenue data added successfully!");
			return "Kenyan National Revenue data inserted successfully!";
		} catch (Exception e) {
			System.err.println("Error inserting revenue data: " + e.getMessage());
			return "Error inserting revenue data.";
		}
	}
}
