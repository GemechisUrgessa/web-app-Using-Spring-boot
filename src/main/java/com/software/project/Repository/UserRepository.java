package com.software.project.Repository;

import com.software.project.Domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public  interface UserRepository extends JpaRepository<User,Integer> {
    public User findByUsername(String username);
}
