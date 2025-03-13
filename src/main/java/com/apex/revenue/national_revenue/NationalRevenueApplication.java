package com.apex.revenue.national_revenue;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.persistence.*;
import java.util.List;
import java.util.Arrays;

// ✅ Start Spring Boot Application
@SpringBootApplication
public class NationalRevenueApplication {
	public static void main(String[] args) {
		SpringApplication.run(NationalRevenueApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(RevenueRepository revenueRepo, CountyRepository countyRepo,
			MinistryRepository ministryRepo, ConditionalGrantRepository grantRepo,
			EqualizationFundRepository fundRepo) {
		return args -> {
			if (revenueRepo.count() == 0) {
				revenueRepo.saveAll(Arrays.asList(
						new Revenue("Ordinary Revenue (Taxes)", 2570000000000.0),
						new Revenue("Grants", 46700000000.0),
						new Revenue("Loans (Domestic & External)", 894500000000.0)));
			}

			if (countyRepo.count() == 0) {
				countyRepo.saveAll(Arrays.asList(
						new County("Nairobi", 39000000000.0),
						new County("Kiambu", 12000000000.0),
						new County("Mombasa", 10000000000.0)));
			}

			if (ministryRepo.count() == 0) {
				ministryRepo.saveAll(Arrays.asList(
						new Ministry("Education", 544000000000.0),
						new Ministry("Health", 122000000000.0),
						new Ministry("Defense", 180000000000.0)));
			}

			if (grantRepo.count() == 0) {
				grantRepo.saveAll(Arrays.asList(
						new ConditionalGrant("Roads Development", 15000000000.0),
						new ConditionalGrant("Water Projects", 8000000000.0)));
			}

			if (fundRepo.count() == 0) {
				fundRepo.saveAll(Arrays.asList(
						new EqualizationFund("Healthcare", 6000000000.0),
						new EqualizationFund("Education", 5000000000.0)));
			}
		};
	}
}

// ✅ ENTITY MODELS (JPA) -------------------------------------------
@Entity
class Revenue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String category;
	private double amount;

	public Revenue() {
	}

	public Revenue(String category, double amount) {
		this.category = category;
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public String getCategory() {
		return category;
	}

	public double getAmount() {
		return amount;
	}
}

@Entity
class County {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private double budget;

	public County() {
	}

	public County(String name, double budget) {
		this.name = name;
		this.budget = budget;
	}
}

@Entity
class Ministry {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private double allocation;

	public Ministry() {
	}

	public Ministry(String name, double allocation) {
		this.name = name;
		this.allocation = allocation;
	}
}

@Entity
class ConditionalGrant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String project;
	private double funds;

	public ConditionalGrant() {
	}

	public ConditionalGrant(String project, double funds) {
		this.project = project;
		this.funds = funds;
	}
}

@Entity
class EqualizationFund {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String sector;
	private double amount;

	public EqualizationFund() {
	}

	public EqualizationFund(String sector, double amount) {
		this.sector = sector;
		this.amount = amount;
	}
}

// ✅ REPOSITORIES (Spring Data JPA) -------------------------------------------
interface RevenueRepository extends JpaRepository<Revenue, Long> {
}

interface CountyRepository extends JpaRepository<County, Long> {
}

interface MinistryRepository extends JpaRepository<Ministry, Long> {
}

interface ConditionalGrantRepository extends JpaRepository<ConditionalGrant, Long> {
}

interface EqualizationFundRepository extends JpaRepository<EqualizationFund, Long> {
}

// ✅ SERVICE LAYER ------------------------------------------------------------
@Service
class RevenueService {
	@Autowired
	private RevenueRepository revenueRepository;

	public List<Revenue> getAllRevenue() {
		return revenueRepository.findAll();
	}
}

@Service
class CountyService {
	@Autowired
	private CountyRepository countyRepository;

	public List<County> getAllCounties() {
		return countyRepository.findAll();
	}
}

@Service
class MinistryService {
	@Autowired
	private MinistryRepository ministryRepository;

	public List<Ministry> getAllMinistries() {
		return ministryRepository.findAll();
	}
}

@Service
class ConditionalGrantService {
	@Autowired
	private ConditionalGrantRepository grantRepository;

	public List<ConditionalGrant> getAllGrants() {
		return grantRepository.findAll();
	}
}

@Service
class EqualizationFundService {
	@Autowired
	private EqualizationFundRepository fundRepository;

	public List<EqualizationFund> getAllFunds() {
		return fundRepository.findAll();
	}
}

// ✅ REST CONTROLLERS ---------------------------------------------------------
@RestController
@RequestMapping("/api/revenue")
class RevenueController {
	@Autowired
	private RevenueService revenueService;

	@CrossOrigin(origins = "http://localhost:5173")
	@GetMapping("/all")
	public List<Revenue> getAllRevenue() {
		return revenueService.getAllRevenue();
	}
}

@RestController
@RequestMapping("/api/counties")
class CountyController {
	@Autowired
	private CountyService countyService;

	@CrossOrigin(origins = "http://localhost:5173")
	@GetMapping("/all")
	public List<County> getAllCounties() {
		return countyService.getAllCounties();
	}
}

// Add Ministry, Grants, and EqualizationFund Controllers (same structure)
