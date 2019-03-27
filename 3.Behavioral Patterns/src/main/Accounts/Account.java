package main.Accounts;

import main.CashbackService.AccountCashback;

import java.util.Observable;

public abstract class Account extends Observable {
    private Account successor;
    private double balance;

    Account(double balance){
        this.balance = balance;
    }

    public void setSuccessor(Account successor){
        this.successor = successor;
    }

    public boolean pay(double amount){
        if (this.canPay(amount)){
            this.balance -= amount;
            setChanged();
            notifyObservers(new AccountCashback(this, amount));
            System.out.println("Paid $" + amount + " using " + getClass().getSimpleName() + " Remaining: " + this.balance);
            return true;
        } else if (this.successor != null){
            System.out.println("Can not pay $" + amount + " using " + getClass().getSimpleName() + ". Proceeding...");
            return successor.pay(amount);
        } else {
            System.out.println("Can not pay $" + amount + " with " + getClass().getSimpleName());
            return false;
        }
    }

    private boolean canPay(double amount){
        return this.balance >= amount;
    }

    public double getBalance(){
        return this.balance;
    }

    public void addToBalance(double amount){
        this.balance += amount;
    }
}
