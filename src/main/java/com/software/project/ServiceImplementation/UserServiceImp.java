package com.software.project.ServiceImplementation;

import java.util.List;
import java.util.Optional;

import com.software.project.Domain.User;
import com.software.project.Repository.UserRepository;
import com.software.project.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public void DeleteById(int id) {
     //
     if(userRepository.existsById(id)){
        userRepository.deleteById(id);
    }
    
    }
    @Override
    public void updateById(int id,User user) {
        // TODO Auto-generated method stub
        if (userRepository.existsById(id)){
            userRepository.save(user); 
        }  
    }

    @Override
    public User findbyId(int id) {
        if (userRepository.existsById(id)){
            return userRepository.findById(id).get();
       }
       return null;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();}
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    @Override
    public void saveUser(User user) {
        userRepository.save(user);
        
    }
    @Override
    public User findbyUsername(String username) {
       
        return userRepository.findByUsername(username);
    }
    @Override
    public boolean checkIfExists(String username) {
    //     User user = userRepository.findByUsername(username);
    //    if()
    return true;
    }
    
}
