package com.group.shoppingapp.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import com.group.shoppingapp.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}