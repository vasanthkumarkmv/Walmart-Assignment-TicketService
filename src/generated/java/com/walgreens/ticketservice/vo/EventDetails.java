package com.walgreens.ticketservice.vo;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * EventDetails
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-01T15:23:50.265-06:00")

public class EventDetails   {
  @JsonProperty("venueId")
  private Integer venueId = null;

  @JsonProperty("eventDescription")
  private String eventDescription = null;

  @JsonProperty("eventStartDateTime")
  private String eventStartDateTime = null;

  @JsonProperty("eventEndDateTime")
  private String eventEndDateTime = null;

  public EventDetails venueId(Integer venueId) {
    this.venueId = venueId;
    return this;
  }

   /**
   * Venue ID
   * @return venueId
  **/
  @ApiModelProperty(example = "1", required = true, value = "Venue ID")
  @NotNull


  public Integer getVenueId() {
    return venueId;
  }

  public void setVenueId(Integer venueId) {
    this.venueId = venueId;
  }

  public EventDetails eventDescription(String eventDescription) {
    this.eventDescription = eventDescription;
    return this;
  }

   /**
   * Description of venue
   * @return eventDescription
  **/
  @ApiModelProperty(example = "Christmas Event", required = true, value = "Description of venue")
  @NotNull

 @Size(max=100)
  public String getEventDescription() {
    return eventDescription;
  }

  public void setEventDescription(String eventDescription) {
    this.eventDescription = eventDescription;
  }

  public EventDetails eventStartDateTime(String eventStartDateTime) {
    this.eventStartDateTime = eventStartDateTime;
    return this;
  }

   /**
   * Start date time of the event
   * @return eventStartDateTime
  **/
  @ApiModelProperty(example = "2017-12-25 10:00:00", required = true, value = "Start date time of the event")
  @NotNull

 @Pattern(regexp="(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})")
  public String getEventStartDateTime() {
    return eventStartDateTime;
  }

  public void setEventStartDateTime(String eventStartDateTime) {
    this.eventStartDateTime = eventStartDateTime;
  }

  public EventDetails eventEndDateTime(String eventEndDateTime) {
    this.eventEndDateTime = eventEndDateTime;
    return this;
  }

   /**
   * End date time of the event
   * @return eventEndDateTime
  **/
  @ApiModelProperty(example = "2017-12-25 18:00:00", required = true, value = "End date time of the event")
  @NotNull

 @Pattern(regexp="(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})")
  public String getEventEndDateTime() {
    return eventEndDateTime;
  }

  public void setEventEndDateTime(String eventEndDateTime) {
    this.eventEndDateTime = eventEndDateTime;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventDetails eventDetails = (EventDetails) o;
    return Objects.equals(this.venueId, eventDetails.venueId) &&
        Objects.equals(this.eventDescription, eventDetails.eventDescription) &&
        Objects.equals(this.eventStartDateTime, eventDetails.eventStartDateTime) &&
        Objects.equals(this.eventEndDateTime, eventDetails.eventEndDateTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(venueId, eventDescription, eventStartDateTime, eventEndDateTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventDetails {\n");
    
    sb.append("    venueId: ").append(toIndentedString(venueId)).append("\n");
    sb.append("    eventDescription: ").append(toIndentedString(eventDescription)).append("\n");
    sb.append("    eventStartDateTime: ").append(toIndentedString(eventStartDateTime)).append("\n");
    sb.append("    eventEndDateTime: ").append(toIndentedString(eventEndDateTime)).append("\n");
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

