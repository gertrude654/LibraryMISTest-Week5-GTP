package com.LMS.LMS.Testing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name="patron_tbl")
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int patron_id;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "patron")
    private List<Transaction> transactions;
}

