package com.softserveinc.todosoap.dao;

import com.softservinc.todosoap.User;

public interface UserDAO {

	User add(User user);

	User findById(long id);

	User findByEmail(String email);

	User update(User user);

	User remove(long id);
}
