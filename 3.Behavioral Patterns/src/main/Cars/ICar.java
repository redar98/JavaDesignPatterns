package main.Cars;

import main.CarColorSchemes.ICarColorScheme;
import main.Enums.CarType;
import main.Enums.Manufacturer;

public interface ICar {
    Manufacturer getManufacturer();
    void setManufacturer(Manufacturer manufacturer);
    String getModel();
    void setModel(String model);
    int getYear();
    void setYear(int year);
    CarType getType();
    void setType(CarType type);
    void setColorScheme(ICarColorScheme newColor);
    ICarColorScheme getColorScheme();
    int getPrice();
    void setPrice(int newPrice);
}
