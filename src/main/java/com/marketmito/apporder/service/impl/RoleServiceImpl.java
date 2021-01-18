package com.marketmito.apporder.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marketmito.apporder.entity.Role;
import com.marketmito.apporder.repository.RoleRepository;
import com.marketmito.apporder.service.RoleService;

//https://www.baeldung.com/transaction-configuration-with-jpa-and-spring

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	@Transactional(readOnly = true)
	@Override
	public List<Role> getAll() throws Exception {
		return (List<Role>)roleRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Role> getAll(Pageable pageable) throws Exception {
		return roleRepository.findAll(pageable);
	}

	@Transactional
	@Override
	public Role saveOrUpdate(Role entity) throws Exception {
		return roleRepository.save(entity);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Role> getOne(Long id) throws Exception {
		return roleRepository.findById(id);
	}

	@Transactional
	@Override
	public void deleteById(Long id) throws Exception {
		roleRepository.deleteById(id);	
	}

}
