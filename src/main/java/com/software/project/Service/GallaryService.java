package com.software.project.Service;

import java.util.List;

import com.software.project.Domain.Gallary;

public interface GallaryService {
    public void save(Gallary gallary);
    public void DeleteById(int id);
    public void updateById(int id,Gallary gallary);
    public Gallary findbyId(int id);
    public List<Gallary> findByRegion(String Region);
    public List<Gallary> findAllGallary();
    
}
