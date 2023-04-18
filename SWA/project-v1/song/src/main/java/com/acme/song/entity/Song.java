package com.acme.song.entity;


import lombok.*;

import java.util.UUID;

@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter
@Setter
@ToString
public class Song {

    private UUID id;
    



}
