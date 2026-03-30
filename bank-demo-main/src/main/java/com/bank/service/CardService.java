package com.bank.service;

import com.bank.entity.Account;
import com.bank.entity.Card;
import com.bank.repository.AccountRepository;
import com.bank.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private AccountRepository accountRepository;

    private Random random = new Random();

    public List<Card> getAllCards() {
        return cardRepository.findByIsActiveTrue();
    }

    public Optional<Card> getCardById(Long id) {
        return cardRepository.findById(id);
    }

    public Card createCard(Card card, Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));

        // Генерируем данные карты
        card.setCardNumber(generateCardNumber());
        card.setExpiryDate(generateExpiryDate());
        card.setCvv(generateCVV());
        card.setAccount(account);

        return cardRepository.save(card);
    }

    public Card updateCard(Long id, Card cardDetails) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found with id: " + id));

        card.setCardHolder(cardDetails.getCardHolder());
        card.setIsActive(cardDetails.getIsActive());

        return cardRepository.save(card);
    }

    public void deleteCard(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found with id: " + id));

        card.setIsActive(false);
        cardRepository.save(card);
    }

    public List<Card> getCardsByAccountId(Long accountId) {
        return cardRepository.findByAccountId(accountId);
    }

    private String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            if (i > 0 && i % 4 == 0) {
                cardNumber.append(" ");
            }
            cardNumber.append(random.nextInt(10));
        }
        return cardNumber.toString();
    }

    private String generateExpiryDate() {
        int month = random.nextInt(12) + 1;
        int year = 28 + random.nextInt(10);
        return String.format("%02d/%02d", month, year);
    }

    private String generateCVV() {
        return String.format("%03d", random.nextInt(1000));
    }
}