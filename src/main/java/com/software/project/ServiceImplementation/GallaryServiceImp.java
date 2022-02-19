package com.software.project.ServiceImplementation;

import java.util.List;

import com.software.project.Domain.Gallary;
import com.software.project.Repository.GallaryRepository;
import com.software.project.Service.GallaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class GallaryServiceImp implements GallaryService {


    @Autowired

    GallaryRepository gallaryRepository;

    @Override
    public void DeleteById(int id) {

        if(gallaryRepository.existsById(id)){
            gallaryRepository.deleteById(id);
        }
        else{
                        throw new RuntimeException("The gallary object with "+id+" that you are trying to delete is not found ");

        }
       
        
    }

    @Override
    public void updateById(int id,Gallary gallary) {
        if(gallaryRepository.existsById(id)){
           Gallary oldGallary=gallaryRepository.findById(id).get();
           oldGallary=gallary;
           gallaryRepository.save(oldGallary);


        }
        else{
            throw new RuntimeException("The gallary object with "+id+" that you are trying to get is not found ");
        }

       
        
    }

    @Override
    public Gallary findbyId(int id) {
        if (gallaryRepository.existsById(id)){
            return gallaryRepository.findById(id).get();
       }
       return null;
      

      
      
    }

    @Override
    public List<Gallary> findAllGallary() {
       
        return  gallaryRepository.findAll();
    }

    @Override
    public void save(Gallary gallary) {
        gallaryRepository.save(gallary);
        
    }

    @Override
    public List<Gallary> findByRegion(String Region) {
       return gallaryRepository.findBaseOnRegion(Region);
    }
    
}
