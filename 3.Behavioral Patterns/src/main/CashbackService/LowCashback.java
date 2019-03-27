package main.CashbackService;

public class LowCashback implements CashbackProfile {
    private final static float cashbackRate = 0.02f;

    @Override
    public int getCashbackAmount(double amountSpent) {
        return (int)(amountSpent * cashbackRate);
    }

    @Override
    public float getCashbackRate() {
        return cashbackRate * 100;
    }
}
