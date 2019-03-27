package main;

import main.CarColorSchemes.ICarColorScheme;
import main.Cars.ICar;
import main.Enums.CarType;
import main.Enums.Manufacturer;

public class ToyCarAdapter implements ICar {
    private ToyCar toyCar;

    public ToyCarAdapter(ToyCar toyCar){
        this.toyCar = toyCar;
    }

    public ToyCar getToyCar(){
        return this.toyCar;
    }

    @Override
    public Manufacturer getManufacturer() {
        return this.toyCar.getBrand();
    }

    @Override
    public void setManufacturer(Manufacturer manufacturer) {
        this.toyCar.setBrand(manufacturer);
    }

    @Override
    public String getModel() {
        return this.toyCar.getToyName();
    }

    @Override
    public void setModel(String model) {
        this.toyCar.setToyName(model);
    }

    @Override
    public int getYear() {
        return this.toyCar.getReleaseYear();
    }

    @Override
    public void setYear(int year) {
        this.toyCar.setReleaseYear(year);
    }

    @Override
    public CarType getType() {
        return this.toyCar.getCarType();
    }

    @Override
    public void setType(CarType type) {
        this.toyCar.setCarType(type);
    }

    @Override
    public void setColorScheme(ICarColorScheme newColorScheme) {
        toyCar.setColorScheme(newColorScheme);
    }

    @Override
    public ICarColorScheme getColorScheme() {
        return toyCar.getColorScheme();
    }

    @Override
    public int getPrice() {
        return this.toyCar.getPrice();
    }

    @Override
    public void setPrice(int newPrice){
        this.toyCar.setPrice(newPrice);
    }
}
