package com.marketmito.apporder.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marketmito.apporder.entity.Users;
import com.marketmito.apporder.repository.UserRepository;
import com.marketmito.apporder.service.UserService;

//https://www.baeldung.com/transaction-configuration-with-jpa-and-spring

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Transactional(readOnly = true)
	@Override
	public List<Users> getAll() throws Exception {
		return (List<Users>)userRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Users> getAll(Pageable pageable) throws Exception {
		return userRepository.findAll(pageable);
	}

	@Transactional
	@Override
	public Users saveOrUpdate(Users entity) throws Exception {
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		return userRepository.save(entity);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Users> getOne(Long id) throws Exception {
		return userRepository.findById(id);
	}

	@Transactional
	@Override
	public void deleteById(Long id) throws Exception {
		userRepository.deleteById(id);	
	}

}
