package jmrs.repository;


import jmrs.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

	User findByName(String name);
}
