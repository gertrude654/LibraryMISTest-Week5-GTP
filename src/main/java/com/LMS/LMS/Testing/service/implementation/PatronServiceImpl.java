package com.LMS.LMS.Testing.service.implementation;

import com.LMS.LMS.Testing.exception.patron.PatronAlreadyExistsException;
import com.LMS.LMS.Testing.exception.patron.PatronNotFoundException;
import com.LMS.LMS.Testing.repository.PatronRepo;
import com.LMS.LMS.Testing.service.PatronService;

import com.LMS.LMS.Testing.model.Patron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatronServiceImpl implements PatronService {

    @Autowired
    private PatronRepo patronRepository;

    @Override
    public Patron findPatronById(int id) {
        return patronRepository.findById(id)
                .orElseThrow(() -> new PatronNotFoundException("Patron not found with id: " + id));
    }

    @Override
    public List<Patron> findAllPatrons() {
        return patronRepository.findAll();
    }

    @Override
    public Patron createPatron(Patron patron) {
        if (patronRepository.existsById(patron.getPatron_id())) {
            throw new PatronAlreadyExistsException("Patron already exists with id: " + patron.getPatron_id());
        }
        return patronRepository.save(patron);
    }

    @Override
    public Patron updatePatron(Patron patron) {
        if (!patronRepository.existsById(patron.getPatron_id())) {
            throw new PatronNotFoundException("Patron not found with id: " + patron.getPatron_id());
        }
        return patronRepository.save(patron);
    }

    @Override
    public void deletePatronById(int id) {
        if (!patronRepository.existsById(id)) {
            throw new PatronNotFoundException("Patron not found with id: " + id);
        }
        patronRepository.deleteById(id);
    }
}
