package com.Ironhack.FinalProject.DTOs;

import java.math.BigDecimal;

public class TransferDTO {
    private Long senderId;
    private Long senderAccountNumber;
    private BigDecimal transfer;
    private Long receiverAccountNumber;
    private Long receiverId;

    public TransferDTO() {
    }

    public TransferDTO(Long senderAccountNumber, BigDecimal transfer, Long receiverAccountNumber, Long receiverId) {
        setSenderAccountNumber(senderAccountNumber);
        setTransfer(transfer);
        setReceiverAccountNumber(receiverAccountNumber);
        setReceiverId(receiverId);
    }

    public Long getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public void setSenderAccountNumber(Long senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    public BigDecimal getTransfer() {
        return transfer;
    }

    public void setTransfer(BigDecimal transfer) {
        this.transfer = transfer;
    }

    public Long getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(Long receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

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
}
