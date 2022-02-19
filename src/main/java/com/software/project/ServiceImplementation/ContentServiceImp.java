package com.software.project.ServiceImplementation;

import java.util.List;

import com.software.project.Domain.Content;
import com.software.project.Repository.ContentRepository;
import com.software.project.Service.ContentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ContentServiceImp implements ContentService {
    @Autowired
    ContentRepository contentRepository;

    @Override
    public void DeleteById(int id) {
       if(contentRepository.existsById(id)){
           contentRepository.deleteById(id);
       }
       else{
           throw new  RuntimeException("the history with id "+id+"is not found");
       }
        
    }

    @Override
    public void updateById(int id, Content history) {
        if(contentRepository.existsById(id)){
            Content oldHistory=contentRepository.findById(id).get();
            oldHistory=history;
            contentRepository.save(oldHistory);
 
 
         }
         else{
             throw new RuntimeException("The  history with "+id+" that you are trying to get is not found ");
         }
      
        
    }

    @Override
    public Content findbyId(int id) {
        if(contentRepository.existsById(id)){
            return  contentRepository.findById(id).get();
        }
        else{
            throw new RuntimeException("The content object with "+id+" that you are trying to get is not found ");
          
        }
     
       
    }

    @Override
    public List<Content> findAllHistory() {

        return contentRepository.findAllHistory();
    }

    @Override
    public List<Content> findAllCulture() {
        return contentRepository.findAllCulture();
    }

    @Override
    public List<Content> findAllSites() {
        return contentRepository.findAllSites();
    }

    @Override
    public void saveContent(Content content) {
       contentRepository.save(content);
        
    }

    @Override
    public List<Content> findAll() {
        return contentRepository.findAll();
    }

    @Override
    public List<Content> searchFor(String searchKey) {
       return contentRepository.findBySearchKey(searchKey);
       
    }

    @Override
    public Content findByIdAndIncreaseViewCount(int id) {
       Content existingContent = contentRepository.findById(id).get();
       existingContent.setViewCount(existingContent.getViewCount()+1);
       contentRepository.save(existingContent);
        return existingContent;
    }

    @Override
    public Content findByIdAndIncreaseCommentCount(int id) {
        Content existingContent = contentRepository.findById(id).get();
        existingContent.setCommentCount(existingContent.getCommentCount()+1);
        contentRepository.save(existingContent);
         return existingContent;
    }


    
}
