package com.walmart.ticketservice.modal;

public class Venue {
    private int venueId;
    private String name;
    private String location;
    private int seatsPerRow;
    private int numberOfRows;

    public Venue(String name, String location, int seatsPerRow, int numberOfRows) {
        this.name = name;
        this.location = location;
        this.seatsPerRow = seatsPerRow;
        this.numberOfRows = numberOfRows;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public void setSeatsPerRow(int seatsPerRow) {
        this.seatsPerRow = seatsPerRow;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Venue venue = (Venue) o;

        if (venueId != venue.venueId) return false;
        if (seatsPerRow != venue.seatsPerRow) return false;
        if (numberOfRows != venue.numberOfRows) return false;
        if (name != null ? !name.equals(venue.name) : venue.name != null) return false;
        if (location != null ? !location.equals(venue.location) : venue.location != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = venueId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + seatsPerRow;
        result = 31 * result + numberOfRows;
        return result;
    }

}
