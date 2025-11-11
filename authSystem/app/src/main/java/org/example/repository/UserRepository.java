package org.example.repository;


import org.example.entities.userInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<userInfo, String> {
    public userInfo findByUsername(String username);
}
