package org.example.controller;

import org.example.security.CheckSecurity;
import org.example.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @CheckSecurity(roles = {"ADMIN"})
    @PostMapping("/zabrani-pristup/{korisnikId}")
    public ResponseEntity<String> zabraniPristup(@PathVariable Integer korisnikId, @RequestHeader("Authorization") String authorization) {
        adminService.zabraniPristup(korisnikId);
        return ResponseEntity.ok("Pristup je zabranjen.");
    }
    @CheckSecurity(roles = {"ADMIN"})
    @PostMapping("/odobri-pristup/{korisnikId}")
    public ResponseEntity<String> odobriPristup(@PathVariable Integer korisnikId, @RequestHeader("Authorization") String authorization) {
        adminService.odobriPristup(korisnikId);
        return ResponseEntity.ok("Pristup je odobren.");
    }

    @GetMapping("/pristupi/{korisnikId}")
    public ResponseEntity<Boolean> pristupi(@PathVariable Integer korisnikId) {
        adminService.isZabranjen(korisnikId);
        return new ResponseEntity<>(adminService.isZabranjen(korisnikId), HttpStatus.OK);
    }
}
