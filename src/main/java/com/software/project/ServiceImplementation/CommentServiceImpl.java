package com.software.project.ServiceImplementation;

import java.util.List;

import com.software.project.Domain.Comment;
import com.software.project.Repository.CommentRepository;
import com.software.project.Service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Override
    public Comment findbyId(int id) {
     return commentRepository.findById(id).get();
    }

    @Override
    public List<Comment> findAllComment() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> findCommentByContentId(int id) {
        return commentRepository.findCommentsUsingContentId(id);
    }

    @Override
    public void savecomment(Comment comment) {
        commentRepository.save(comment);
        
    }
    
}
