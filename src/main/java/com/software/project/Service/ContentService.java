package com.software.project.Service;

import java.util.List;

import com.software.project.Domain.Content;

public interface ContentService {
    public void saveContent(Content content);
    public void DeleteById(int id);
    public void updateById(int id,Content history);
    public Content findbyId(int id);
    public Content findByIdAndIncreaseViewCount(int id);
    public Content findByIdAndIncreaseCommentCount(int id);
    public List<Content> findAll();
    public List<Content> findAllHistory();
    public List<Content> findAllCulture();
    public List<Content> findAllSites();
    public List<Content> searchFor(String searchKey);
}
