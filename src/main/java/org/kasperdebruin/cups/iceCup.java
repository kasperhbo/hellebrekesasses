package org.kasperdebruin.cups;

public abstract class iceCup {
    private final float amountInLiters;

    public iceCup(float amountInLiters) {
        this.amountInLiters = amountInLiters;
    }

    public float getLiterAmount(){
        return amountInLiters;
    }
}
