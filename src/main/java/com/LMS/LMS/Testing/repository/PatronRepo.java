package com.LMS.LMS.Testing.repository;

import com.LMS.LMS.Testing.model.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronRepo extends JpaRepository<Patron, Integer> {
}
