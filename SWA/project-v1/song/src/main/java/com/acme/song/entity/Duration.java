package com.acme.song.entity;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class Duration {


    private int hours;
    private int minutes;
    private int second;


}


