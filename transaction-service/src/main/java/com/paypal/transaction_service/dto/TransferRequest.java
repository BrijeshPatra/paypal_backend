package com.paypal.transaction_service.dto;

public class TransferRequest {
    private String senderName;
    private String receiverName;
    private String amount;

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public TransferRequest() {
    }

    public TransferRequest(String senderName, String receiverName, String amount) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.amount = amount;
    }

}
