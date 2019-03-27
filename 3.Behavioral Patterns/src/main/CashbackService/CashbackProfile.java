package main.CashbackService;

public interface CashbackProfile {
    int getCashbackAmount(double amountSpent);
    float getCashbackRate();
}
