package com.software.project.Repository;

import java.util.List;

import com.software.project.Domain.Content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContentRepository extends JpaRepository<Content,Integer> {

    @Query(value="select * from content where content_type='HISTORY'",nativeQuery = true)
    public List<Content> findAllHistory();
    @Query(value="select * from content where content_type='CULTURE'",nativeQuery = true)
    public List<Content> findAllCulture();
    @Query(value="select * from content where content_type='SITE'",nativeQuery = true)
    public List<Content> findAllSites();
    @Query(value = "select * from content where content_intro like %?1% ",nativeQuery = true)
    public List<Content> findBySearchKey(String key);

    
}
