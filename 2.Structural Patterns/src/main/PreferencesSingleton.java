package main;

import main.Cars.Car;
import main.Cars.ICar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PreferencesSingleton {
    private static volatile PreferencesSingleton instance;
    private String owner;
    private List<Car> manufacturedCars;
    private List<Engine> manufacturedEngines;
    private ICar favoriteCar;

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
}
