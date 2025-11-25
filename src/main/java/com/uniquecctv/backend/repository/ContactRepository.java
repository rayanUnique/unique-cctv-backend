package com.uniquecctv.backend.repository;

import com.uniquecctv.backend.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
	List<Contact> findAllByOrderByCreatedAtDesc();
    List<Contact> findByIsReadFalseOrderByCreatedAtDesc();
    Long countByIsReadFalse();
}