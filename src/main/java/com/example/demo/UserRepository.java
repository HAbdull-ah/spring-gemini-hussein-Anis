package com.example.demo;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends DatastoreRepository<User, String> {
    // No custom queries needed – we use the key (username) directly
}
