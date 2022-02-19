package com.software.project.Service;

import java.util.List;

import com.software.project.Domain.Que;

public interface QueService {
    public void saveContent(Que content);
    public void DeleteById(int id);
    public void updateById(int id,Que history);
    public Que findbyId(int id);
    public Que findByIdAndIncreaseViewCount(int id);
    public Que findByIdAndIncreaseCommentCount(int id);
    public List<Que> findAll();
    public List<Que> findAllHistory();
    public List<Que> findAllCulture();
    public List<Que> findAllSites();
    public List<Que> searchFor(String searchKey);
}
