package com.eventbookingapi.studentmeetup.collection;

import lombok.*;
@Builder

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    public T Data;
    public boolean Success=false;
    public String Message;
}
