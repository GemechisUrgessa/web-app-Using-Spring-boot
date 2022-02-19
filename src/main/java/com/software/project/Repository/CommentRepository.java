package com.software.project.Repository;

import java.util.List;

import com.software.project.Domain.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
    @Query(value = "select * from comment where content_id=?1",nativeQuery = true)
    public List<Comment> findCommentsUsingContentId(int id);
    
}
