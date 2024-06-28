package com.coderjiang.pojo.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Resident implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "identity_number")
    private String identityNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "family_number")
    private Integer familyNumber;

    @Column(name = "in_park_time")
    private String inParkTime;

    @Column(name = "tags")
    private String tags;


}
