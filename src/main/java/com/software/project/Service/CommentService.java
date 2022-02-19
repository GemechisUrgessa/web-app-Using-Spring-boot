package com.software.project.Service;

import java.util.List;

import com.software.project.Domain.Comment;

public interface CommentService {
    public void savecomment(Comment comment);
    public Comment findbyId(int id);
    public List<Comment> findAllComment();
    public List<Comment> findCommentByContentId(int id);

    
}
