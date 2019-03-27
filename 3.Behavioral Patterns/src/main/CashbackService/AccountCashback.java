package main.CashbackService;

import main.Accounts.Account;

public class AccountCashback {
    private Account account;
    private double spentAmount;

    public AccountCashback(Account account, double spentAmount){
        this.account = account;
        this.spentAmount = spentAmount;
    }

    public Account getAccount(){
        return this.account;
    }

    public double getSpentAmount(){
        return this.spentAmount;
    }
}
