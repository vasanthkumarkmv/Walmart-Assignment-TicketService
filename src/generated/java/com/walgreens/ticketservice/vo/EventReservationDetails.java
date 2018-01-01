package com.walgreens.ticketservice.vo;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * EventReservationDetails
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-01T15:23:50.265-06:00")

public class EventReservationDetails   {
  @JsonProperty("eventId")
  private Integer eventId = null;

  @JsonProperty("numberOfSeats")
  private Integer numberOfSeats = null;

  @JsonProperty("customerEmail")
  private String customerEmail = null;

  public EventReservationDetails eventId(Integer eventId) {
    this.eventId = eventId;
    return this;
  }

   /**
   * Event ID
   * @return eventId
  **/
  @ApiModelProperty(example = "1", required = true, value = "Event ID")
  @NotNull


  public Integer getEventId() {
    return eventId;
  }

  public void setEventId(Integer eventId) {
    this.eventId = eventId;
  }

  public EventReservationDetails numberOfSeats(Integer numberOfSeats) {
    this.numberOfSeats = numberOfSeats;
    return this;
  }

   /**
   * Number of seats to be reserved
   * @return numberOfSeats
  **/
  @ApiModelProperty(example = "4", required = true, value = "Number of seats to be reserved")
  @NotNull


  public Integer getNumberOfSeats() {
    return numberOfSeats;
  }

  public void setNumberOfSeats(Integer numberOfSeats) {
    this.numberOfSeats = numberOfSeats;
  }

  public EventReservationDetails customerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
    return this;
  }

   /**
   * Email ID of the customer
   * @return customerEmail
  **/
  @ApiModelProperty(example = "vasanthkumar.km@gmail.com", required = true, value = "Email ID of the customer")
  @NotNull

 @Size(max=100)
  public String getCustomerEmail() {
    return customerEmail;
  }

  public void setCustomerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventReservationDetails eventReservationDetails = (EventReservationDetails) o;
    return Objects.equals(this.eventId, eventReservationDetails.eventId) &&
        Objects.equals(this.numberOfSeats, eventReservationDetails.numberOfSeats) &&
        Objects.equals(this.customerEmail, eventReservationDetails.customerEmail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(eventId, numberOfSeats, customerEmail);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventReservationDetails {\n");
    
    sb.append("    eventId: ").append(toIndentedString(eventId)).append("\n");
    sb.append("    numberOfSeats: ").append(toIndentedString(numberOfSeats)).append("\n");
    sb.append("    customerEmail: ").append(toIndentedString(customerEmail)).append("\n");
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

