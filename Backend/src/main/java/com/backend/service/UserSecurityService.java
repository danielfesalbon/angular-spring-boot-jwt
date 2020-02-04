package com.backend.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.entity.Usertable;
import com.backend.repository.UsertableRepository;
import com.backend.util.AES;

@Service
public class UserSecurityService implements UserDetailsService {

	@Autowired
	private UsertableRepository userjpa;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Usertable user = userjpa.findById(username).get();
		return new User(user.getUsername(), AES.decrypt(user.getPassword(), AES.KEY), new ArrayList<>());
	}

}
