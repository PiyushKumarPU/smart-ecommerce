package com.instaprepsai.common.api.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.instaprepsai.common.api.response.error.ApiError;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__({@JsonCreator}))
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_EMPTY)
public class InstaprepsApiResponse<T> {

  @JsonProperty("success")
  @Schema(description = "true|false indicating if request completed successfully.")
  private Boolean success;

  @JsonProperty("payload")
  private T payload;

  @JsonProperty("error")
  @Schema(example = "Request submitted successfully.")
  private ApiError error;

  @JsonProperty("message")
  @Schema(example = "Request submitted successfully.")
  private String message;

  public InstaprepsApiResponse(Boolean success, T payload, String message) {
    this.success = success;
    this.payload = payload;
    this.message = message;
  }

  public InstaprepsApiResponse(T payload, String message) {
    this.payload = payload;
    this.message = message;
  }

  public InstaprepsApiResponse(ApiError error) {
    this.success = false;
    this.error = error;
  }

  public InstaprepsApiResponse(T payload) {
    this.success = true;
    this.payload = payload;
  }

  public static <T> InstaprepsApiResponse<T> failure(@NonNull String code, @NonNull String message) {
    return new InstaprepsApiResponse<>(ApiError.builder().code(code).message(message).build());
  }
}
