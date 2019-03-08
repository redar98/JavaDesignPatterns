package main.CarColorSchemes;

import main.Enums.Color;
import main.Enums.InteriorColor;

public class DarkCarColorScheme implements ICarColorScheme {
    private Color bodyColor;
    private InteriorColor interiorColor;


    public DarkCarColorScheme(Color bodyColor){
        this.bodyColor = bodyColor;
        this.interiorColor = InteriorColor.DARK;
    }

    @Override
    public Color getBodyColor() {
        return this.bodyColor;
    }

    @Override
    public InteriorColor getInteriorColor() {
        return this.interiorColor;
    }

    @Override
    public String toString(){
        return "Body color: " + this.bodyColor + " | " + this.interiorColor + " interior!";
    }
}
