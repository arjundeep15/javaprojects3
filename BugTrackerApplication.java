package com.arjundeep.bugtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

// 1. THE MAIN APP
@SpringBootApplication
public class BugTrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BugTrackerApplication.class, args);
    }
}

// 2. THE DATABASE ENTITY (Maps exactly to your SQL table)
@Entity
@Table(name = "bug_reports")
class BugReport {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String description;
    private String status = "OPEN";
    private String severity = "LOW";
    
    @Column(name = "created_on", updatable = false)
    private LocalDateTime createdOn = LocalDateTime.now();

    // Getters and Setters (Jackson needs these to convert to JSON)
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    public LocalDateTime getCreatedOn() { return createdOn; }
}

// 3. THE REPOSITORY (Handles all the SQL queries under the hood)
interface BugReportRepository extends JpaRepository<BugReport, Long> {
    // Spring writes the SQL for you automatically
    List<BugReport> findByStatus(String status);
}

// 4. THE REST CONTROLLER (The API endpoints your HTML fetches)
@RestController
@RequestMapping("/api/bugs")
@CrossOrigin(origins = "*") // Allows your HTML frontend to talk to this backend without CORS errors
class BugController {
    
    private final BugReportRepository repo;
    
    public BugController(BugReportRepository repo) {
        this.repo = repo;
    }

    // GET http://localhost:8080/api/bugs
    @GetMapping
    public List<BugReport> getAllBugs() {
        return repo.findAll();
    }

    // POST http://localhost:8080/api/bugs
    @PostMapping
    public BugReport reportBug(@RequestBody BugReport bug) {
        return repo.save(bug);
    }
}
