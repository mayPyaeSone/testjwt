package com.pkg.testrole.repository;

import org.springframework.stereotype.Repository;

import com.pkg.testrole.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {
	Users findByUsername(String username);
}
