package main.Transmissions;

import main.Enums.TransmissionType;

public abstract class Transmission {
    private TransmissionType type;
    private int gears;
    private float shiftTimeMS;

    Transmission(TransmissionType type, int gears, float shiftTimeMS){
        this.type = type;
        this.gears = gears;
        this.shiftTimeMS = shiftTimeMS;
    }

    protected abstract void construct();

    public TransmissionType getType(){
        return this.type;
    }

    public void setType(TransmissionType type){
        this.type = type;
    }

    public int getGears(){
        return this.gears;
    }

    public void setGears(int gears){
        this.gears = gears;
    }

    public float getShiftTimeMS(){
        return this.shiftTimeMS;
    }

    public void setShiftTimeMS(float shiftTimeMS){
        this.shiftTimeMS = shiftTimeMS;
    }

    @Override
    public String toString(){
        return this.type + " transmission" + (this.gears > 0? " with " + this.gears + " gears.": "") + (this.shiftTimeMS > 0? " shift time: " + this.shiftTimeMS + "ms": "");
    }
}
