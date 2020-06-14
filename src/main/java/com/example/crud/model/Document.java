package com.example.crud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Document extends BaseEntity {

    @Column(name = "title")
    @NotEmpty
    private String title;

    @Column(name = "number")
    private String number;

    @Column(name = "expiry_date")
    @NotNull
    private LocalDate expiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    //@NotNull
    private User owner;

    public Document(String title, String number, LocalDate expiryDate) {
        this.title = title;
        this.number = number;
        this.expiryDate = expiryDate;
    }

    @JsonIgnore
    public User getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "Document{" +
                "title='" + title + '\'' +
                ", number='" + number + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
