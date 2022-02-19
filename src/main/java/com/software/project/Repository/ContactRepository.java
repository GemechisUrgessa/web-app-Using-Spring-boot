package com.software.project.Repository;
import com.software.project.Domain.Contact;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact,Integer>{
    
}