package com.api.redis.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.redis.dao.UserDao;
import com.api.redis.models.User;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserDao userDao;
	
	@PostMapping
	public User createUser(@RequestBody User user) {
		user.setUserId(UUID.randomUUID().toString());
		return userDao.save(user);
		
	}
	
	@GetMapping("/{userId}")
	public User getUser(@PathVariable("userId") String userId) {
		return userDao.get(userId);
	}
	
	@GetMapping
	public List<User> getAll(){
		Map<Object, Object> all = userDao.findAll();
		Collection<Object> values = all.values();
		List<User> collect = values.stream().map(value -> (User) value).collect(Collectors.toList());
		return collect;
		
	}
	
	@DeleteMapping("/{userId}")
	public void delete(@PathVariable("userId") String userId) {
		 userDao.delete(userId);
	}

}
