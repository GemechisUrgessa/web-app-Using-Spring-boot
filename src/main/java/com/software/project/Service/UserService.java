package com.software.project.Service;

import java.util.List;

import com.software.project.Domain.User;

public interface UserService {
    public void DeleteById(int id);
    public void saveUser(User user);
    public void updateById(int id,User user);
    public User findbyId(int id);
    public User findbyUsername(String username);
    public List<User> findAllUsers();
    public User findByUsername(String username);
    public boolean checkIfExists(String username);
    
}
