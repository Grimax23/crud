package com.example.crud.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

//@Entity
public class Document
//        extends BaseEntity
        {

    @Column(name = "title")
    @NotEmpty
    private String title;

    @Column(name = "number")
    @NotEmpty
    private String number;

    private LocalDate expiryDate;

/*
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
*/

}
