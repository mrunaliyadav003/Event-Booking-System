package com.eventbookingapi.studentmeetup.collection;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
@Builder

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    @JsonProperty("data")
    public T Data;

    @JsonProperty("success")
    public boolean Success=false;

    @JsonProperty("message")
    public String Message;
}
