package main.CarColorSchemes;

import main.Enums.Color;
import main.Enums.InteriorColor;

public class LightCarColorScheme implements ICarColorScheme {
    private Color bodyColor;
    private InteriorColor interiorColor;


    public LightCarColorScheme(Color bodyColor){
        this.bodyColor = bodyColor;
        this.interiorColor = InteriorColor.LIGHT;
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
