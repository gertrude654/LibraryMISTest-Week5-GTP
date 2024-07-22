package com.LMS.LMS.Testing.integration;

import com.LMS.LMS.Testing.exception.patron.PatronNotFoundException;
import com.LMS.LMS.Testing.model.Patron;
import com.LMS.LMS.Testing.repository.PatronRepo;
import com.LMS.LMS.Testing.service.implementation.PatronServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class PatronIntegrationTest {

    @Autowired
    private PatronRepo patronRepository;

    @Autowired
    private PatronServiceImpl patronService;

    @BeforeEach
    public void setUp() {
        patronRepository.deleteAll(); // Clean up the repository before each test
    }

    @Test
    public void testCreateAndFindPatron() {
        Patron patron = new Patron();
        patron.setFirstName("Alice");
        patron.setLastName("Smith");


        Patron createdPatron = patronService.createPatron(patron);
        assertNotNull(createdPatron);
        assertEquals("Alice", createdPatron.getFirstName());

        Patron foundPatron = patronService.findPatronById(createdPatron.getPatron_id());
        assertNotNull(foundPatron);
        assertEquals("Alice", foundPatron.getFirstName());
    }

    @Test
    public void testUpdatePatron() {
        Patron patron = new Patron();
        patron.setFirstName("Bob");
        patron.setLastName("Jones");

        Patron createdPatron = patronService.createPatron(patron);
        createdPatron.setFirstName("Robert");

        patronService.updatePatron(createdPatron);

        Patron updatedPatron = patronService.findPatronById(createdPatron.getPatron_id());
        assertNotNull(updatedPatron);
        assertEquals("Robert", updatedPatron.getFirstName());
    }

    @Test
    public void testDeletePatron() {
        Patron patron = new Patron();
        patron.setFirstName("Charlie");
        patron.setLastName("Brown");


        Patron createdPatron = patronService.createPatron(patron);
        patronService.deletePatronById(createdPatron.getPatron_id());

        assertThrows(PatronNotFoundException.class, () -> patronService.findPatronById(createdPatron.getPatron_id()));
    }
}
