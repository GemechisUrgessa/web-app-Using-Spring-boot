package com.software.project.ServiceImplementation;

import java.util.List;

import com.software.project.Domain.Contact;
import com.software.project.Repository.ContactRepository;
import com.software.project.Service.ContactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ContactServiceImp implements ContactService {
    @Autowired
    ContactRepository contactRepository;

    @Override
    public void DeleteById(int id) {
      if(contactRepository.existsById(id)){

        contactRepository.deleteById(id);
      }
      else {
          throw new  RuntimeException("the contact with id: "+id+" does not exist.");
      }
        
    }

    @Override
    public void updateById(int id,Contact contact) {
        if(contactRepository.existsById(id)){
            Contact oldContact=contactRepository.findById(id).get();
            oldContact=contact;
            contactRepository.save(oldContact);
            
        }
        else {
            throw new  RuntimeException("the contact with id: "+id+" does not exist.");
        }

       
        
    }

    @Override
    public Contact findbyId(int id) {
       ;
        if(contactRepository.existsById(id)){

       return contactRepository.findById(id).get();
    }
    else{
        throw new  RuntimeException("the contact with id: "+id+" does not exist.");

    }
    }
    @Override
    public List<Contact> findAllContact() {
       
        return contactRepository.findAll();
    }

    @Override
    public void Save(Contact contact) {
      contactRepository.save(contact);
        
    }
    
}
