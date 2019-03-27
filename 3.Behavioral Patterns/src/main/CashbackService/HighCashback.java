package main.CashbackService;

public class HighCashback implements CashbackProfile {
    private final static float cashbackRate = 0.12f;

    @Override
    public int getCashbackAmount(double amountSpent) {
        return (int)(amountSpent * cashbackRate);
    }

    @Override
    public float getCashbackRate() {
        return cashbackRate * 100;
    }
}
