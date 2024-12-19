package jmrs.repository;


import jmrs.entity.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface BookRepository extends CrudRepository<jmrs.entity.Book, Integer> {

	List<jmrs.entity.Book> findByBookDate(Date date);
}
