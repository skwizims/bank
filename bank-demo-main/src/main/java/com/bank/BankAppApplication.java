package com.bank;

import com.bank.entity.Customer;
import com.bank.repository.AccountRepository;
import com.bank.repository.CustomerRepository;
import com.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.bank", "repository", "entity", "dto", "com/bank/service"})  // ← ДОБАВЬТЕ "service"
public class BankAppApplication {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    public static void main(String[] args) {
        SpringApplication.run(BankAppApplication.class, args);
        System.out.println("✅ Банковское приложение запущено!");
        System.out.println("📍 Адрес: http://localhost:8080");
        System.out.println("📊 H2 Console: http://localhost:8080/h2-console");
    }

    @Bean
    public CommandLineRunner initTestData() {
        return args -> {
            if (customerRepository.count() == 0) {
                System.out.println("🎯 Инициализация тестовых данных...");

                // Простой тест
                Customer customer1 = new Customer("Иван", "Петров", "ivan@test.com", "+79991234567");
                customerRepository.save(customer1);

                System.out.println("✅ Тестовый клиент создан!");
            }
        };
    }
}