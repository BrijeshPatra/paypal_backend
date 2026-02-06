package com.paypal.transaction_service.entity;

import com.paypal.transaction_service.dto.TransactionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "sender_name",nullable = false)
    private Long senderId;

    @Column(name = "receiver_name",nullable = false)
    private Long receiverId;

    @Column(nullable = false)
    @Positive(message = "Amount must be positive")
    private Double amount;

    @Column(nullable = false)
    private LocalDateTime timeStamp;

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    @Enumerated(EnumType.STRING)

    private TransactionStatus status;

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Transaction() {
    }

    public Transaction(long id, Long senderName, Long receiverName, Double amount, LocalDateTime timeStamp, String status) {
        this.id = id;
        this.senderId = senderName;
        this.receiverId = receiverName;
        this.amount = amount;
        this.timeStamp = timeStamp;
    }

    @PrePersist
    public void prePersist(){
        if (timeStamp==null){
            timeStamp=LocalDateTime.now();
        }
        if (status==null){
            status= TransactionStatus.valueOf("PENDING");
        }
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", senderName='" + senderId + '\'' +
                ", receiverName='" + receiverId + '\'' +
                ", amount=" + amount +
                ", timeStamp=" + timeStamp +
                ", status='" + status + '\'' +
                '}';
    }
}
