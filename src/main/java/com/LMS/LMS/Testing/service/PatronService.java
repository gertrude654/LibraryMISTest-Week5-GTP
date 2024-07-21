package com.LMS.LMS.Testing.service;

import com.LMS.LMS.Testing.model.Patron;

import java.util.List;

public interface PatronService {

    Patron findPatronById(int id);

    List<Patron> findAllPatrons();

    Patron createPatron(Patron Patron);

    Patron updatePatron(Patron Patron);

    void deletePatronById(int id);
}
