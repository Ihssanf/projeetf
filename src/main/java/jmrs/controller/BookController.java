package jmrs.controller;

import jmrs.entity.Book;
import jmrs.entity.User;
import jmrs.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
@CrossOrigin(origins = "http://localhost:3000")
@Controller
@Scope("prototype")
@RequestMapping("/book")
public class BookController extends BaseController {

	@Autowired
	private jmrs.service.BookService bookService;

	private jmrs.entity.Book book;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add(jmrs.entity.Book book) {
		ModelAndView modelAndView = new ModelAndView("book-addEdit");
		modelAndView.addObject(ACTION, ACTION_ADD);

		modelAndView.addObject("book", book);

		return modelAndView;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(jmrs.entity.Book book) {
		ModelAndView modelAndView = new ModelAndView("redirect:/");

		if (!validateBooking(book)) {
			return add(book);
		}
		book.setUser(new jmrs.entity.User(1));

		countMinuteAndHour(book);

		bookService.save(book);

		modelAndView.addObject("currentDay", smt.format(book.getBookDate()));

		return modelAndView;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(jmrs.entity.Book book) {
		ModelAndView modelAndView = new ModelAndView("redirect:/");

		if (!validateBooking(book)) {
			return edit(book.getBookId());
		}
		book.setUser(new jmrs.entity.User(1));

		countMinuteAndHour(book);

		bookService.update(book);

		modelAndView.addObject("currentDay", smt.format(book.getBookDate()));

		return modelAndView;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView edit(@RequestParam("bookId") Integer bookId) {
		ModelAndView modelAndView = new ModelAndView("book-addEdit");
		modelAndView.addObject(ACTION, ACTION_EDIT);

		book = bookService.findOne(jmrs.entity.Book.class, bookId);

		modelAndView.addObject("book", book);

		return modelAndView;
	}

	private void countMinuteAndHour(jmrs.entity.Book book) {
		if (book.getStartTime() != null && book.getEndTime() != null) {
			long duration = book.getEndTime().getTime() - book.getStartTime().getTime();
			int minutes = (int) (duration / 1000 / 60);
			double hours = (double) minutes / 60;

			book.setMinutes(minutes);
			book.setHours(hours);
		}
	}

	private boolean validateBooking(jmrs.entity.Book book) {

		// 檢查必填
		if (StringUtils.isEmpty(book.getName())) {
			return false;
		}

		// 檢查時間衝突
		if (book.getStartTime() != null && book.getEndTime() != null) {

		}

		return true;
	}
}
