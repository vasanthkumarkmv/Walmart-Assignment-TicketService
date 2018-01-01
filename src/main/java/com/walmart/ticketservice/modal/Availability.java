package com.walmart.ticketservice.modal;

public class Availability implements Comparable<Availability> {
    private int level;
    private int seatNumber;
    private int numberOfSeatsAvailable;

    public Availability(int level, int seatNumber, int numberOfSeatsAvailable) {
        this.level = level;
        this.seatNumber = seatNumber;
        this.numberOfSeatsAvailable = numberOfSeatsAvailable;
    }

    public int getLevel() {
        return level;
    }


    public int getSeatNumber() {
        return seatNumber;
    }

    public int getNumberOfSeatsAvailable() {
        return numberOfSeatsAvailable;
    }

    public void substractExceedingSeats(int exceedingSeats) {
        numberOfSeatsAvailable -= exceedingSeats;
    }

    @Override
    public int compareTo(Availability o) {
        return o.numberOfSeatsAvailable - this.numberOfSeatsAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Availability that = (Availability) o;

        if (level != that.level) return false;
        if (seatNumber != that.seatNumber) return false;
        return numberOfSeatsAvailable == that.numberOfSeatsAvailable;
    }

    @Override
    public int hashCode() {
        int result = level;
        result = 31 * result + seatNumber;
        result = 31 * result + numberOfSeatsAvailable;
        return result;
    }

    @Override
    public String toString() {
        return "Availability{" +
                "level=" + level +
                ", seatNumber=" + seatNumber +
                ", numberOfSeatsAvailable=" + numberOfSeatsAvailable +
                '}';
    }
}
