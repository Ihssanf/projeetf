package jmrs.service.impl;


import jmrs.entity.Book;
import jmrs.repository.BookRepository;
import jmrs.service.BookService;
import jmrs.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BookServiceImpl extends BaseServiceImpl implements jmrs.service.BookService {

	@Autowired
	private jmrs.repository.BookRepository bookRepository;

	@Override
	public List<jmrs.entity.Book> getBooks(String date) {
		Date d = new Date();
		try {
			d = jmrs.utils.DateUtils.DATE_FORMAT.parse(date);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return bookRepository.findByBookDate(d);
	}

}
