package org.kasperdebruin.transferbelts;

import org.kasperdebruin.boxes.box;
import org.kasperdebruin.pallets.palet;

public interface ItransferBelt {
    void turnOnOrOff(boolean on);

    int getBoxesProduced();

    int getPaletsProduced();

    int getProductionSpeed();

    boolean isRunning();

    box getCurrentBox();

    palet getCurrentPalet();

    void setProducingCups(boolean producingCups);

    boolean isProducingCups();

    int getTotalSpoonsInBox();

    int getTotalSpoons();

    boolean getCanProduceCups();

    float getLitersProduced();

}
