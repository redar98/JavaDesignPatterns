package main.Cars;

import main.CarColorSchemes.DarkCarColorScheme;
import main.CarColorSchemes.ICarColorScheme;
import main.Enums.CarType;
import main.Enums.Color;
import main.Enums.Manufacturer;

public abstract class Car implements ICar {

    private Manufacturer manufacturer;
    private String model;
    private int year;
    private CarType type;
    private ICarColorScheme colorScheme;
    private int price;

    // TODO: add a class that will have information about engine
    //  (model, horsepower, cylinders, petrol/diesel/hybrid/electric)
    //  Consider using premade engines?
    // private Engine engine;

    public Car(Manufacturer manufacturer, String model, int year, CarType type){
        this.manufacturer = manufacturer;
        this.model = model;
        this.year = year;
        this.type = type;
        this.colorScheme = new DarkCarColorScheme(Color.WHITE);
    }

    public Manufacturer getManufacturer(){
        return this.manufacturer;
    }

    public String getModel(){
        return this.model;
    }

    public int getYear(){
        return this.year;
    }

    public CarType getType(){
        return this.type;
    }

    public void setManufacturer(Manufacturer manufacturer){
        this.manufacturer = manufacturer;
    }

    public void setModel(String model){
        this.model = model;
    }

    public void setYear(int year){
        this.year = year;
    }

    public void setType(CarType type){
        this.type = type;
    }

    public void setColorScheme(ICarColorScheme newColorScheme){
        this.colorScheme = newColorScheme;
    }

    public ICarColorScheme getColorScheme(){
        return this.colorScheme;
    }

    protected abstract void construct();

    public abstract Car clone();

    public int getPrice(){
        return this.price;
    }

    public void setPrice(int newPrice){
        this.price = newPrice;
    }

    @Override
    public String toString(){
        return this.manufacturer + " " + this.model + "(" + this.type + ") manufactured in " + this.year;
    }
}

