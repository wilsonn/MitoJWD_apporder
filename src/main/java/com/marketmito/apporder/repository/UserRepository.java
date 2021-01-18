package com.marketmito.apporder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marketmito.apporder.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
	Users findByUserName(String userName);
}
