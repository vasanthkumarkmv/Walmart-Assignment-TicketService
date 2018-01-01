package com.walgreens.ticketservice.vo;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * HoldInfo
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-01T15:23:50.265-06:00")

public class HoldInfo   {
  @JsonProperty("hold-ref")
  private String holdRef = null;

  public HoldInfo holdRef(String holdRef) {
    this.holdRef = holdRef;
    return this;
  }

   /**
   * Get holdRef
   * @return holdRef
  **/
  @ApiModelProperty(value = "")


  public String getHoldRef() {
    return holdRef;
  }

  public void setHoldRef(String holdRef) {
    this.holdRef = holdRef;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HoldInfo holdInfo = (HoldInfo) o;
    return Objects.equals(this.holdRef, holdInfo.holdRef);
  }

  @Override
  public int hashCode() {
    return Objects.hash(holdRef);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HoldInfo {\n");
    
    sb.append("    holdRef: ").append(toIndentedString(holdRef)).append("\n");
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

