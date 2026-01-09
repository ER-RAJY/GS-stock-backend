package com.gsstock.backend.config;

import com.gsstock.backend.domain.auth.Role;
import com.gsstock.backend.domain.auth.User;
import com.gsstock.backend.domain.stock.Produit;
import com.gsstock.backend.domain.stock.Unite;
import com.gsstock.backend.repository.ProduitRepository;
import com.gsstock.backend.repository.UserRepository;
import com.gsstock.backend.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProduitRepository produitRepository;
    private final StockService stockService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        seedUsers();
        seedProduits();
    }

    // ======================
    // USERS
    // ======================
    private void seedUsers() {

        if (userRepository.count() > 0) return;

        User admin = new User();
        admin.setUsername("admin");
        admin.setPasswordHash(passwordEncoder.encode("admin123"));
        admin.setRoles(Set.of(Role.ADMIN));
        admin.setEnabled(true);

        User comptable = new User();
        comptable.setUsername("comptable");
        comptable.setPasswordHash(passwordEncoder.encode("comptable123"));
        comptable.setRoles(Set.of(Role.COMPTABLE));
        comptable.setEnabled(true);

        userRepository.save(admin);
        userRepository.save(comptable);
    }

    // ======================
    // PRODUITS + STOCK
    // ======================
    private void seedProduits() {

        if (produitRepository.count() > 0) return;

        Produit ciment = createProduit("Ciment CPJ", Unite.KG, 100);
        Produit sable = createProduit("Sable", Unite.KG, 500);
        Produit fer = createProduit("Fer 8mm", Unite.PIECE, 50);

        // Stock initial
        stockService.addEntree(ciment.getId(), BigDecimal.valueOf(1000), "Stock initial");
        stockService.addEntree(sable.getId(), BigDecimal.valueOf(3000), "Stock initial");
        stockService.addEntree(fer.getId(), BigDecimal.valueOf(200), "Stock initial");
    }

    private Produit createProduit(String name,  Unite unite, int stockMin) {
        Produit p = new Produit();
        p.setDesignation(name);
        p.setUnite(unite);
        p.setStockMin(BigDecimal.valueOf(stockMin));
        p.setActive(true);
        return produitRepository.save(p);
    }
}
