package com.paypal.transaction_service.services;

import com.paypal.transaction_service.dto.TransactionStatus;
import com.paypal.transaction_service.entity.Transaction;
import com.paypal.transaction_service.kafka.KafkaEventProducer;
import com.paypal.transaction_service.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final KafkaEventProducer kafkaEventProducer;
    private final ObjectMapper objectMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository,ObjectMapper objectMapper,
    KafkaEventProducer kafkaEventProducer ){
        this.transactionRepository=transactionRepository;
        this.objectMapper=objectMapper;
        this.kafkaEventProducer=kafkaEventProducer;
    }
    @Override
    public Transaction createTransaction(Transaction request) {

        Transaction transaction = new Transaction();
        transaction.setSenderId(request.getSenderId());
        transaction.setReceiverId(request.getReceiverId());
        transaction.setAmount(request.getAmount());
        transaction.setTimeStamp(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.SUCCESS);

       Transaction savedTransaction= transactionRepository.save(transaction);

       try{
           String key=String.valueOf(savedTransaction.getId());
           kafkaEventProducer.sendTransactionEvent(key,savedTransaction);
       }catch (Exception e){
           System.err.println("Failed to publish Kafka event for transactionId: "
                   + savedTransaction.getId());
       }
       return savedTransaction;
    }


    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

}
