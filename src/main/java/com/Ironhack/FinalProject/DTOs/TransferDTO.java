package com.Ironhack.FinalProject.DTOs;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TransferDTO {
    @NotNull
    private Long senderAccountNumber;
    @NotNull
    private BigDecimal transfer;
    @NotNull
    private Long receiverAccountNumber;
    @NotNull
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

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }
}
