package org.kasperdebruin.pallets;

public abstract class palet {
    private final int palletCapacity;
    private boolean hasRoom = true;
    private int currentAmount = 0;


    public palet(int boxCapacity) {
        hasRoom = true;
        this.palletCapacity = boxCapacity;
    }

    public void addBox(int amount) {

        if(hasRoom)
            currentAmount+=amount;

        if(currentAmount>=palletCapacity)
            hasRoom = false;
    }

    public boolean hasRoom() {
        return hasRoom;
    }

    public int getCurrentBoxAmount(){
        return currentAmount;
    }
}
