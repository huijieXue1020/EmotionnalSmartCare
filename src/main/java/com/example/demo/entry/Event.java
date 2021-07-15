package com.example.demo.entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
//@ToString
public class Event {

    private int id;
    private int eventType;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date eventDate;
    private String eventLocation;
    private String eventDesc;
    private int oldPerson;

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"eventType\":" + eventType +
                ", \"eventDate\":\"" + eventDate +
                "\", \"eventLocation\":\"" + eventLocation+
                "\", \"eventDesc\":\"" + eventDesc +
                "\", \"oldPerson\":" + oldPerson +
                '}';
    }
}
