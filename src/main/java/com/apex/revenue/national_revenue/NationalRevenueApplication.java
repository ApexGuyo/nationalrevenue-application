package com.apex.revenue.national_revenue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.persistence.*;

import java.util.List;

// ✅ Start Spring Boot Application
@SpringBootApplication
public class NationalRevenueApplication {
	public static void main(String[] args) {
		SpringApplication.run(NationalRevenueApplication.class, args);
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

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getBudget() {
		return budget;
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

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getAllocation() {
		return allocation;
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

	public Long getId() {
		return id;
	}

	public String getProject() {
		return project;
	}

	public double getFunds() {
		return funds;
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

	public Long getId() {
		return id;
	}

	public String getSector() {
		return sector;
	}

	public double getAmount() {
		return amount;
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
	private ConditionalGrantRepository conditionalGrantRepository;

	public List<ConditionalGrant> getAllGrants() {
		return conditionalGrantRepository.findAll();
	}
}

@Service
class EqualizationFundService {
	@Autowired
	private EqualizationFundRepository equalizationFundRepository;

	public List<EqualizationFund> getAllFunds() {
		return equalizationFundRepository.findAll();
	}
}

// ✅ REST CONTROLLERS ---------------------------------------------------------

@RestController
@RequestMapping("/api/revenue")
class RevenueController {
	@Autowired
	private RevenueService revenueService;

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

	@GetMapping("/all")
	public List<County> getAllCounties() {
		return countyService.getAllCounties();
	}
}

@RestController
@RequestMapping("/api/ministries")
class MinistryController {
	@Autowired
	private MinistryService ministryService;

	@GetMapping("/all")
	public List<Ministry> getAllMinistries() {
		return ministryService.getAllMinistries();
	}
}

@RestController
@RequestMapping("/api/grants")
class ConditionalGrantController {
	@Autowired
	private ConditionalGrantService grantService;

	@GetMapping("/all")
	public List<ConditionalGrant> getAllGrants() {
		return grantService.getAllGrants();
	}
}

@RestController
@RequestMapping("/api/funds")
class EqualizationFundController {
	@Autowired
	private EqualizationFundService fundService;

	@GetMapping("/all")
	public List<EqualizationFund> getAllFunds() {
		return fundService.getAllFunds();
	}
}
