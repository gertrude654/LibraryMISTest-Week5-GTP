package com.LMS.LMS.Testing.unit;


import com.LMS.LMS.Testing.exception.patron.PatronAlreadyExistsException;
import com.LMS.LMS.Testing.exception.patron.PatronNotFoundException;
import com.LMS.LMS.Testing.model.Patron;
import com.LMS.LMS.Testing.repository.PatronRepo;
import com.LMS.LMS.Testing.service.implementation.PatronServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PatronUnitTest {

    @Mock
    private PatronRepo patronRepository;

    @InjectMocks
    private PatronServiceImpl patronService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindPatronById_PatronNotFound() {
        when(patronRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(PatronNotFoundException.class, () -> patronService.findPatronById(1));
    }

    @Test
    void testCreatePatron_PatronAlreadyExists() {
        Patron patron = new Patron();
        patron.setPatron_id(1);

        when(patronRepository.existsById(1)).thenReturn(true);

        assertThrows(PatronAlreadyExistsException.class, () -> patronService.createPatron(patron));
    }

    @Test
    void testUpdatePatron_PatronNotFound() {
        Patron patron = new Patron();
        patron.setPatron_id(1);

        when(patronRepository.existsById(1)).thenReturn(false);

        assertThrows(PatronNotFoundException.class, () -> patronService.updatePatron(patron));
    }

    @Test
    void testDeletePatron_PatronNotFound() {
        when(patronRepository.existsById(1)).thenReturn(false);

        assertThrows(PatronNotFoundException.class, () -> patronService.deletePatronById(1));
    }
}
