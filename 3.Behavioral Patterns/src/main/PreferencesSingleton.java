package main;

import main.Accounts.Account;
import main.Cars.Car;
import main.Cars.ICar;
import main.CashbackService.AccountCashback;
import main.CashbackService.CashbackProfile;
import main.CashbackService.LowCashback;

import java.util.*;

public class PreferencesSingleton implements Observer {
    private static volatile PreferencesSingleton instance;
    private String owner;
    private List<Car> manufacturedCars;
    private List<Engine> manufacturedEngines;
    private ICar favoriteCar;
    private CashbackProfile cashbackProfile = new LowCashback();

    private PreferencesSingleton(){
        manufacturedCars = new ArrayList<>();
        manufacturedEngines = new ArrayList<>();
    }

    public static PreferencesSingleton getInstance(){
        if (instance == null){
            // Thread-Safe (Double-checked locking)
            synchronized (PreferencesSingleton.class){
                if (instance == null){
                    instance = new PreferencesSingleton();
                }
            }
        }
        return instance;
    }

    public String getOwner(){
        return this.owner;
    }

    public void setOwner(String owner){
        this.owner = owner;
    }

    public List<Car> getManufacturedCars(){
        return this.manufacturedCars;
    }

    public void appendManufacturedCar(Car car){
        this.manufacturedCars.add(car);
    }

    public List<Engine> getManufacturedEngines(){
        return this.manufacturedEngines;
    }

    public void appendManufacturedEngine(Engine engine){
        this.manufacturedEngines.add(engine);
    }

    public void setFavoriteCar(ICar car){
        this.favoriteCar = car;
    }

    public void showFavoriteCarInfo(){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int carAge = currentYear - this.favoriteCar.getYear();

        StringBuilder string = new StringBuilder();
        string.append("Current favorite car is \"")
                .append(this.favoriteCar.getModel())
                .append("\" manufactured by ")
                .append(this.favoriteCar.getManufacturer())
                .append(". This car is ")
                .append(carAge > 0? carAge + " years old.": "new.");

        System.out.println(string.toString());
    }

    public void setCashbackProfile(CashbackProfile newCashbackProfile){
        this.cashbackProfile = newCashbackProfile;
    }

    @Override
    public void update(Observable o, Object arg) {
        AccountCashback accountCashback = (AccountCashback) arg;
        Account account = accountCashback.getAccount();

        double spentAmount = accountCashback.getSpentAmount();
        int cashback = this.cashbackProfile.getCashbackAmount(spentAmount);
        account.addToBalance(cashback);

        System.out.println("[!] Observer: Account balance changed! Withdrawn amount: $" + spentAmount
                + " Cashback(" + this.cashbackProfile.getCashbackRate() + "%): $" + cashback);
    }
}
