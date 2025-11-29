package com.example.demo;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;

public interface UserRepository extends DatastoreRepository<User, Long> {

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);
}
