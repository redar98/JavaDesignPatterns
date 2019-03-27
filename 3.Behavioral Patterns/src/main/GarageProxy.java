package main;

public class GarageProxy extends Garage {
    private boolean doorsOpen = false;


    @Override
    public void applyDiscount(float percentage){
        if (doorsOpen){
            System.out.println("Applying discounts to your garage!");
            super.applyDiscount(percentage);
        } else {
            System.out.println("Doors to this garage are closed! You are not allowed to make any changes!");
        }
    }

    public void openDoors(String password){
        if (isValidPassword(password) && !doorsOpen){
            doorsOpen = true;
            System.out.println("Doors to your garage are now open! You can apply discounts...");
        } else if (!isValidPassword(password) && !doorsOpen) {
            System.out.println("No! You can not open doors to that garage!");
        } else {
            System.out.println("Doors to your garage are already open.");
        }
    }

    private boolean isValidPassword(String password){
        return password.equals("$ecr@t");
    }

    public void closeDoors(){
        if (doorsOpen) {
            doorsOpen = false;
            System.out.println("Doors are now closed!");
        }
    }
}
