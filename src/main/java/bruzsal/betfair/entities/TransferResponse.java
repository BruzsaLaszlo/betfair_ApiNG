package bruzsal.betfair.entities;

public class TransferResponse {
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    private String transactionId;
}
