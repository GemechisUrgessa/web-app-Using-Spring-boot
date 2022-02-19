package com.software.project.Repository;

import java.util.List;

import com.software.project.Domain.Content;
import com.software.project.Domain.Que;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QueRepo extends JpaRepository<Que,Integer> {

    @Query(value="select * from que where content_type='HISTORY'",nativeQuery = true)
    public List<Que> findAllHistory();
    @Query(value="select * from que where content_type='CULTURE'",nativeQuery = true)
    public List<Que> findAllCulture();
    @Query(value="select * from que where content_type='SITE'",nativeQuery = true)
    public List<Que> findAllSites();
    @Query(value = "select * from que where content_intro like %?1% ",nativeQuery = true)
    public List<Que> findBySearchKey(String key);

    
}
