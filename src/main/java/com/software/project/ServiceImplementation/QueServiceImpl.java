package com.software.project.ServiceImplementation;

import java.util.List;

import com.software.project.Domain.Content;
import com.software.project.Domain.Que;
import com.software.project.Repository.ContentRepository;
import com.software.project.Repository.QueRepo;
import com.software.project.Service.ContentService;
import com.software.project.Service.QueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class QueServiceImpl implements QueService {
    @Autowired
    QueRepo contentRepository;

    @Override
    public void DeleteById(int id) {
    //    if(contentRepository.existsById(id)){
    //        contentRepository.deleteById(id);
    //    }
    //    else{
    //        throw new  RuntimeException("the history with id "+id+"is not found");
    //    }
    contentRepository.deleteById(id);
        
    }

    @Override
    public void updateById(int id, Que history) {
        if(contentRepository.existsById(id)){
            Que oldHistory=contentRepository.findById(id).get();
            oldHistory=history;
            contentRepository.save(oldHistory);
 
 
         }
         else{
             throw new RuntimeException("The  history with "+id+" that you are trying to get is not found ");
         }
      
        
    }

    @Override
    public Que findbyId(int id) {
        if(contentRepository.existsById(id)){
            return  contentRepository.findById(id).get();
        }
        else{
            throw new RuntimeException("The gallary object with "+id+" that you are trying to get is not found ");
          
        }
     
       
    }

    @Override
    public List<Que> findAllHistory() {

        return contentRepository.findAllHistory();
    }

    @Override
    public List<Que> findAllCulture() {
        return contentRepository.findAllCulture();
    }

    @Override
    public List<Que> findAllSites() {
        return contentRepository.findAllSites();
    }

    @Override
    public void saveContent(Que content) {
       contentRepository.save(content);
        
    }

    @Override
    public List<Que> findAll() {
        return contentRepository.findAll();
    }

    @Override
    public List<Que> searchFor(String searchKey) {
       return contentRepository.findBySearchKey(searchKey);
       
    }

    @Override
    public Que findByIdAndIncreaseViewCount(int id) {
        Que existingContent = contentRepository.findById(id).get();
       existingContent.setViewCount(existingContent.getViewCount()+1);
       contentRepository.save(existingContent);
        return existingContent;
    }

    @Override
    public Que findByIdAndIncreaseCommentCount(int id) {
        Que existingContent = contentRepository.findById(id).get();
        existingContent.setCommentCount(existingContent.getCommentCount()+1);
        contentRepository.save(existingContent);
         return existingContent;
    }


    
}
