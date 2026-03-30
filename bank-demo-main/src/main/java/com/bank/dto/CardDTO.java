package com.bank.dto;

import java.time.LocalDateTime;

public class CardDTO {
    private Long id;
    private String cardNumber;
    private String cardHolder;
    private String expiryDate;
    private String cvv;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private Long accountId;

    public CardDTO() {}

    public CardDTO(Long id, String cardNumber, String cardHolder, String expiryDate,
                   String cvv, Boolean isActive, LocalDateTime createdAt, Long accountId) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.accountId = accountId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getCardHolder() { return cardHolder; }
    public void setCardHolder(String cardHolder) { this.cardHolder = cardHolder; }

    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }

    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }
}