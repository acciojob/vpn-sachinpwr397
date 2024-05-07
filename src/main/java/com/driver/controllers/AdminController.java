package com.driver.controllers;

import com.driver.model.Admin;
import com.driver.model.ServiceProvider;
import com.driver.services.AdminService;
import com.driver.services.exceptions.CountryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerAdmin(@RequestParam String username, @RequestParam String password) {
        Admin admin = adminService.register(username, password);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addProvider")
    public ResponseEntity<Void> addServiceProvider(@RequestParam int adminId, @RequestParam String providerName) {
        Admin admin = adminService.addServiceProvider(adminId, providerName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addCountry")
    public ResponseEntity<Void> addCountry(@RequestParam int serviceProviderId, @RequestParam String countryName) {
        try {
            ServiceProvider serviceProvider = adminService.addCountry(serviceProviderId, countryName);
            return ResponseEntity.ok().build();
        } catch (CountryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Country not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
