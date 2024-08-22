package com.clothstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.clothstore.dao.UserDao;
import com.clothstore.model.User;


@Service
public class AppUserService implements UserDetailsService {
	@Autowired
	private UserDao userDao;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userDao.findByEmailId(email);
		return user.toUser();
	}
}
