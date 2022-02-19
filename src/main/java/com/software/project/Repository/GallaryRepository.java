package com.software.project.Repository;

import java.util.List;

import com.software.project.Domain.Gallary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public  interface GallaryRepository extends JpaRepository<Gallary,Integer>{
    @Query(value = "select * from gallary where region=?1",nativeQuery = true)
    public List<Gallary> findBaseOnRegion(String region);
    
}
