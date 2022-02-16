package dev.dankom.unanimous.exception;

public class TransactionFailureException extends Throwable {
    public TransactionFailureException(Throwable throwable) {
        super(throwable);
    }
}
