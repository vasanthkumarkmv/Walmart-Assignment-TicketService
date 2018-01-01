package com.walgreens.ticketservice.vo;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.walgreens.ticketservice.vo.EventDetailsExt;
import com.walgreens.ticketservice.vo.VenueDetailsExt;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * EventReservationStatus
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-01T15:23:50.265-06:00")

public class EventReservationStatus   {
  @JsonProperty("venueDetails")
  private VenueDetailsExt venueDetails = null;

  @JsonProperty("eventDetails")
  private EventDetailsExt eventDetails = null;

  @JsonProperty("numberOfSeatsAvailable")
  private Integer numberOfSeatsAvailable = null;

  @JsonProperty("reservationStatus")
  private List<List<String>> reservationStatus = new ArrayList<List<String>>();

  public EventReservationStatus venueDetails(VenueDetailsExt venueDetails) {
    this.venueDetails = venueDetails;
    return this;
  }

   /**
   * Get venueDetails
   * @return venueDetails
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public VenueDetailsExt getVenueDetails() {
    return venueDetails;
  }

  public void setVenueDetails(VenueDetailsExt venueDetails) {
    this.venueDetails = venueDetails;
  }

  public EventReservationStatus eventDetails(EventDetailsExt eventDetails) {
    this.eventDetails = eventDetails;
    return this;
  }

   /**
   * Get eventDetails
   * @return eventDetails
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public EventDetailsExt getEventDetails() {
    return eventDetails;
  }

  public void setEventDetails(EventDetailsExt eventDetails) {
    this.eventDetails = eventDetails;
  }

  public EventReservationStatus numberOfSeatsAvailable(Integer numberOfSeatsAvailable) {
    this.numberOfSeatsAvailable = numberOfSeatsAvailable;
    return this;
  }

   /**
   * Number of seats available
   * @return numberOfSeatsAvailable
  **/
  @ApiModelProperty(example = "4", value = "Number of seats available")


  public Integer getNumberOfSeatsAvailable() {
    return numberOfSeatsAvailable;
  }

  public void setNumberOfSeatsAvailable(Integer numberOfSeatsAvailable) {
    this.numberOfSeatsAvailable = numberOfSeatsAvailable;
  }

  public EventReservationStatus reservationStatus(List<List<String>> reservationStatus) {
    this.reservationStatus = reservationStatus;
    return this;
  }

  public EventReservationStatus addReservationStatusItem(List<String> reservationStatusItem) {
    this.reservationStatus.add(reservationStatusItem);
    return this;
  }

   /**
   * Get reservationStatus
   * @return reservationStatus
  **/
  @ApiModelProperty(example = "[[\"HOLD\",\"HOLD\",\"OPEN\"],[\"HOLD\",\"HOLD\",\"OPEN\"],[\"RESERVED\",\"RESERVED\",\"RESERVED\"]]", required = true, value = "")
  @NotNull

  @Valid

  public List<List<String>> getReservationStatus() {
    return reservationStatus;
  }

  public void setReservationStatus(List<List<String>> reservationStatus) {
    this.reservationStatus = reservationStatus;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventReservationStatus eventReservationStatus = (EventReservationStatus) o;
    return Objects.equals(this.venueDetails, eventReservationStatus.venueDetails) &&
        Objects.equals(this.eventDetails, eventReservationStatus.eventDetails) &&
        Objects.equals(this.numberOfSeatsAvailable, eventReservationStatus.numberOfSeatsAvailable) &&
        Objects.equals(this.reservationStatus, eventReservationStatus.reservationStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(venueDetails, eventDetails, numberOfSeatsAvailable, reservationStatus);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventReservationStatus {\n");
    
    sb.append("    venueDetails: ").append(toIndentedString(venueDetails)).append("\n");
    sb.append("    eventDetails: ").append(toIndentedString(eventDetails)).append("\n");
    sb.append("    numberOfSeatsAvailable: ").append(toIndentedString(numberOfSeatsAvailable)).append("\n");
    sb.append("    reservationStatus: ").append(toIndentedString(reservationStatus)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

