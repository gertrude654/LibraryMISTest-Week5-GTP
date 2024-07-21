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
@Table(name="book_tbl")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int book_id;
    private String isbn;
    private String title;
    private String author;
    private LocalDate publication_date;
    private String category;
    private boolean checkOut;

    @OneToMany(mappedBy = "book")
    private List<Transaction> transactions;

}
