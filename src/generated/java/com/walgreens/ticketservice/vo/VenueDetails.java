package com.walgreens.ticketservice.vo;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * VenueDetails
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-01T15:23:50.265-06:00")

public class VenueDetails   {
  @JsonProperty("venueName")
  private String venueName = null;

  @JsonProperty("venueLocation")
  private String venueLocation = null;

  @JsonProperty("levelsInVenue")
  private Integer levelsInVenue = null;

  @JsonProperty("seatsInLevel")
  private Integer seatsInLevel = null;

  public VenueDetails venueName(String venueName) {
    this.venueName = venueName;
    return this;
  }

   /**
   * Name of the venue ( Allows only alphanumeric,-,_ or space )
   * @return venueName
  **/
  @ApiModelProperty(example = "The Ballroom At I Street", required = true, value = "Name of the venue ( Allows only alphanumeric,-,_ or space )")
  @NotNull

 @Size(max=100)
  public String getVenueName() {
    return venueName;
  }

  public void setVenueName(String venueName) {
    this.venueName = venueName;
  }

  public VenueDetails venueLocation(String venueLocation) {
    this.venueLocation = venueLocation;
    return this;
  }

   /**
   * Location of the venue
   * @return venueLocation
  **/
  @ApiModelProperty(example = "2202 SW I St, Bentonville, AR 72712", required = true, value = "Location of the venue")
  @NotNull

 @Size(max=500)
  public String getVenueLocation() {
    return venueLocation;
  }

  public void setVenueLocation(String venueLocation) {
    this.venueLocation = venueLocation;
  }

  public VenueDetails levelsInVenue(Integer levelsInVenue) {
    this.levelsInVenue = levelsInVenue;
    return this;
  }

   /**
   * Number of levels or rows in venue
   * @return levelsInVenue
  **/
  @ApiModelProperty(example = "3", required = true, value = "Number of levels or rows in venue")
  @NotNull


  public Integer getLevelsInVenue() {
    return levelsInVenue;
  }

  public void setLevelsInVenue(Integer levelsInVenue) {
    this.levelsInVenue = levelsInVenue;
  }

  public VenueDetails seatsInLevel(Integer seatsInLevel) {
    this.seatsInLevel = seatsInLevel;
    return this;
  }

   /**
   * Number of seats in each level
   * @return seatsInLevel
  **/
  @ApiModelProperty(example = "3", required = true, value = "Number of seats in each level")
  @NotNull


  public Integer getSeatsInLevel() {
    return seatsInLevel;
  }

  public void setSeatsInLevel(Integer seatsInLevel) {
    this.seatsInLevel = seatsInLevel;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VenueDetails venueDetails = (VenueDetails) o;
    return Objects.equals(this.venueName, venueDetails.venueName) &&
        Objects.equals(this.venueLocation, venueDetails.venueLocation) &&
        Objects.equals(this.levelsInVenue, venueDetails.levelsInVenue) &&
        Objects.equals(this.seatsInLevel, venueDetails.seatsInLevel);
  }

  @Override
  public int hashCode() {
    return Objects.hash(venueName, venueLocation, levelsInVenue, seatsInLevel);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VenueDetails {\n");
    
    sb.append("    venueName: ").append(toIndentedString(venueName)).append("\n");
    sb.append("    venueLocation: ").append(toIndentedString(venueLocation)).append("\n");
    sb.append("    levelsInVenue: ").append(toIndentedString(levelsInVenue)).append("\n");
    sb.append("    seatsInLevel: ").append(toIndentedString(seatsInLevel)).append("\n");
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

