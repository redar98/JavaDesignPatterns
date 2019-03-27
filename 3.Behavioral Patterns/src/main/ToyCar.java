package main;

import main.CarColorSchemes.DarkCarColorScheme;
import main.CarColorSchemes.ICarColorScheme;
import main.Enums.CarType;
import main.Enums.Color;
import main.Enums.Manufacturer;

import java.util.Calendar;

public class ToyCar {
    private Manufacturer brand;
    private String toyName;
    private int releaseYear;
    private CarType carType;
    private ICarColorScheme colorScheme;
    private int price;

    public ToyCar(Manufacturer brand, String toyName, CarType carType){
        this.brand = brand;
        this.toyName = toyName;
        this.releaseYear = Calendar.getInstance().get(Calendar.YEAR);
        this.carType = carType;
        this.colorScheme = new DarkCarColorScheme(Color.WHITE);
    }

    public Manufacturer getBrand() {
        return brand;
    }

    public void setBrand(Manufacturer brand) {
        this.brand = brand;
    }

    public String getToyName() {
        return toyName;
    }

    public void setToyName(String toyName) {
        this.toyName = toyName;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setColorScheme(ICarColorScheme newColorScheme){
        this.colorScheme = newColorScheme;
    }

    public ICarColorScheme getColorScheme(){
        return this.colorScheme;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public void Play(){
        System.out.println("Playing with toy car: " + this.toyName + " by " + this.brand + "!");
    }

    @Override
    public String toString(){
        return "Toy car \'" + this.toyName + "\' " + this.brand + ". Released in " + this.releaseYear;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
