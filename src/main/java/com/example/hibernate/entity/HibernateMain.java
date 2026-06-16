package com.example.hibernate.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class HibernateMain implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    public static void main(String[] args) {
        SpringApplication.run(HibernateMain.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        try {
            System.out.println("=== @OneToOne RELATIONSHIP TRANSACTION STARTED ===");

            // 1. Create a Passport Object
            Passport transientPassport = new Passport("IND998877");

            // 2. Create a User Object
            UserHibernate user = new UserHibernate();
            user.setName("Priyanshu Saxena");

            // 3. Link them together
            user.setPassport(transientPassport);

            // 4. Save the parent object.
            // Because of cascade = CascadeType.ALL, Hibernate will save the passport first,
            // then save the user with the passport's ID automatically!
            entityManager.persist(user);

            System.out.println("=== TRANSACTION COMPLETED SUCCESSFULLY ===");

        } catch (Exception e) {
            System.err.println("Transaction failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}