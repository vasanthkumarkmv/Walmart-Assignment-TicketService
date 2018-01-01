package com.walmart.ticketservice.modal;

import java.util.HashMap;
import java.util.Map;

public enum SeatStatus {
    OPEN(0),
    HOLD(1),
    RESERVED(2);

    private int value;
    private static final Map map = new HashMap();

    SeatStatus(int value) {
        this.value = value;
    }

    static {
        for (SeatStatus seatStatus : SeatStatus.values()) {
            map.put(seatStatus.value, seatStatus);
        }
    }

    public static SeatStatus valueOf(int seatStatus) {
        return (SeatStatus) map.get(seatStatus);
    }

}
