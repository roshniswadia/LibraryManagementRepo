package service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import bean.Book;
import bean.User;
import dao.AdminDao;
import dao.UserDao;

public class UserService {

	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Transactional
	public int checkUserLogin(final String userLoginName,final String password) {
		final int userId =  userDao.checkUserLogin(userLoginName,password);
		return userId;
	}
	
	@Transactional(readOnly = true)
	public List<Book> viewAllAvailableBooks(final int userId) {
		final List<Book> book = userDao.viewAllAvailableBooks(userId);
		return book;
	}
	
	@Transactional
	public void issueBook(final int userId,final int bookId) {
		userDao.issueBook(userId,bookId);
	}
	
	@Transactional
	public List<Book> viewIssuedBookList(final int userId) {
		final List<Book> listOfIssuedBooks = userDao.viewIssuedBookList(userId);
		return listOfIssuedBooks;
	}
	
	@Transactional
	public void returnBook(final int userId,final int bookId) {
		userDao.returnBook(userId,bookId);
	}
	
}
