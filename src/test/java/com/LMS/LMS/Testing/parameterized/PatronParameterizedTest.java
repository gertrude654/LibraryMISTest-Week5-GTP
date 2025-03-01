package com.LMS.LMS.Testing.parameterized;

import com.LMS.LMS.Testing.exception.patron.PatronAlreadyExistsException;
import com.LMS.LMS.Testing.exception.patron.PatronNotFoundException;
import com.LMS.LMS.Testing.model.Patron;
import com.LMS.LMS.Testing.repository.PatronRepo;
import com.LMS.LMS.Testing.service.implementation.PatronServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PatronParameterizedTest {

    @Mock
    private PatronRepo patronRepository;

    @InjectMocks
    private PatronServiceImpl patronService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @CsvSource({
            "1, Alice, Smith, true",
            "2, Bob, Brown, false"
    })
    void testCreatePatron_PatronExists(int id, String firstName, String lastName, boolean exists) {
        Patron patron = new Patron();
        patron.setPatron_id(id);
        patron.setFirstName(firstName);
        patron.setLastName(lastName);

        when(patronRepository.existsById(id)).thenReturn(exists);

        if (exists) {
            assertThrows(PatronAlreadyExistsException.class, () -> patronService.createPatron(patron));
            verify(patronRepository, never()).save(patron); // Ensure save is not called
        } else {
            when(patronRepository.save(patron)).thenReturn(patron); // Mock save method

            Patron createdPatron = patronService.createPatron(patron);
            assertNotNull(createdPatron);
            assertEquals(id, createdPatron.getPatron_id());
            assertEquals(firstName, createdPatron.getFirstName());
            assertEquals(lastName, createdPatron.getLastName());
            verify(patronRepository).save(patron); // Verify save is called once
        }

        verify(patronRepository).existsById(id); // Verify existsById is called once
    }

    @ParameterizedTest
    @CsvSource({
            "1, Alice, Smith, true",
            "2, Bob, Brown, false"
    })
    void testUpdatePatron_PatronExists(int id, String firstName, String lastName, boolean exists) {
        Patron patron = new Patron();
        patron.setPatron_id(id);
        patron.setFirstName(firstName);
        patron.setLastName(lastName);

        when(patronRepository.existsById(id)).thenReturn(exists);

        if (exists) {
            when(patronRepository.save(patron)).thenReturn(patron); // Mock save method

            Patron updatedPatron = patronService.updatePatron(patron);
            assertNotNull(updatedPatron);
            assertEquals(id, updatedPatron.getPatron_id());
            assertEquals(firstName, updatedPatron.getFirstName());
            assertEquals(lastName, updatedPatron.getLastName());
            verify(patronRepository).save(patron); // Verify save is called once
        } else {
            assertThrows(PatronNotFoundException.class, () -> patronService.updatePatron(patron));
            verify(patronRepository, never()).save(patron); // Ensure save is not called
        }

        verify(patronRepository).existsById(id); // Verify existsById is called once
    }
}


//package com.LMS.LMS.Testing.parameterized;
//
//import com.LMS.LMS.Testing.exception.patron.PatronAlreadyExistsException;
//import com.LMS.LMS.Testing.exception.patron.PatronNotFoundException;
//import com.LMS.LMS.Testing.model.Patron;
//import com.LMS.LMS.Testing.repository.PatronRepo;
//import com.LMS.LMS.Testing.service.implementation.PatronServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class PatronParameterizedTest {
//
//    @Mock
//    private PatronRepo patronRepository;
//
//    @InjectMocks
//    private PatronServiceImpl patronService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @ParameterizedTest
//    @CsvSource({
//            "1, true",
//            "2, false"
//    })
//    void testCreatePatron_PatronExists(int id, boolean exists) {
//        Patron patron = new Patron();
//        patron.setPatron_id(id);
//
//        when(patronRepository.existsById(id)).thenReturn(exists);
//
//        if (exists) {
//            assertThrows(PatronAlreadyExistsException.class, () -> patronService.createPatron(patron));
//        } else {
//            Patron createdPatron = patronService.createPatron(patron);
//            assertNotNull(createdPatron);
//            assertEquals(id, createdPatron.getPatron_id());
//        }
//    }
//
//    @ParameterizedTest
//    @CsvSource({
//            "1, true",
//            "2, false"
//    })
//    void testUpdatePatron_PatronExists(int id, boolean exists) {
//        Patron patron = new Patron();
//        patron.setPatron_id(id);
//
//        when(patronRepository.existsById(id)).thenReturn(exists);
//
//        if (exists) {
//            Patron updatedPatron = patronService.updatePatron(patron);
//            assertNotNull(updatedPatron);
//            assertEquals(id, updatedPatron.getPatron_id());
//        } else {
//            assertThrows(PatronNotFoundException.class, () -> patronService.updatePatron(patron));
//        }
//    }
//}
