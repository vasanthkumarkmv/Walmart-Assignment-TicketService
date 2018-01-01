package com.walmart.ticketservice.modal;

import java.sql.Timestamp;

public class SeatReservation {
    private int seatId;
    private int eventId;
    private int rowNumber;
    private int seatNumberInRow;
    private Integer customerId;
    private SeatStatus status;
    private Timestamp reservationTime;
    private String holdUuid;

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getSeatNumberInRow() {
        return seatNumberInRow;
    }

    public void setSeatNumberInRow(int seatNumberInRow) {
        this.seatNumberInRow = seatNumberInRow;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public String getStatusAsString() {
        String statusAsString;
        if (status == SeatStatus.HOLD) {
            statusAsString = "HOLD";
        } else if (status == SeatStatus.RESERVED) {
            statusAsString = "RESERVED";
        } else {
            statusAsString = "OPEN";
        }
        return statusAsString;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    public Timestamp getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(Timestamp reservationTime) {
        this.reservationTime = reservationTime;
    }

    public String getHoldUuid() {
        return holdUuid;
    }

    public void setHoldUuid(String holdUuid) {
        this.holdUuid = holdUuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeatReservation that = (SeatReservation) o;

        if (seatId != that.seatId) return false;
        if (eventId != that.eventId) return false;
        if (rowNumber != that.rowNumber) return false;
        if (seatNumberInRow != that.seatNumberInRow) return false;
        if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (reservationTime != null ? !reservationTime.equals(that.reservationTime) : that.reservationTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = seatId;
        result = 31 * result + eventId;
        result = 31 * result + rowNumber;
        result = 31 * result + seatNumberInRow;
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (reservationTime != null ? reservationTime.hashCode() : 0);
        return result;
    }

}
