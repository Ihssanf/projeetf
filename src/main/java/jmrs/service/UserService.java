package jmrs.service;

import jmrs.entity.User;

public interface UserService extends BaseService {

	User checkUserAndPassword(String name, String password);
}