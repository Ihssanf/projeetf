package jmrs.service;


import jmrs.entity.Book;

import java.util.List;

public interface BookService extends BaseService {

	List<jmrs.entity.Book> getBooks(String date);
}
