package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import bean.Admin;
import bean.Book;
import bean.User;

public class UserDao {

	public final static String BOOK_ISSUED = "Issued";
	public final static String BOOK_NOT_ISSUED = "Not Issued";

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public int checkUserLogin(final String userLoginName, final String password) {

		final Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery(
				"select userId from User a where a.userLoginName =:userLoginName and a.password =:password");
		query.setString("userLoginName", userLoginName);
		query.setString("password", password);
		int userId = 0;

		if (query.uniqueResult() != null)
			userId = (Integer) query.uniqueResult();

		return userId;
	}

	public List<Book> viewAllAvailableBooks(final int userId) {

		final Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Book b where b.issueStatus =:issueStatus");
		query.setString("issueStatus", BOOK_NOT_ISSUED);

		final List<Book> bookList = query.list();
		return bookList;

	}

	public void issueBook(final int userId, final int bookId) {

		final Session session = this.sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(
				"update book b set b.issueStatus =:issueStatus , b.userId =:userId where b.bookId=:bookId");
		query.setString("issueStatus", BOOK_ISSUED);
		query.setInteger("userId", userId);
		query.setInteger("bookId", bookId);
		query.executeUpdate();

	}

	public List<Book> viewIssuedBookList(final int userId) {

		final Session session = this.sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery("select * from book b where b.userId =:userId ");
		query.setInteger("userId", userId);
		query.addEntity(Book.class);
		final List books = query.list();
		return books;

	}

	public void returnBook(final int userId, final int bookId) {

		final Session session = this.sessionFactory.getCurrentSession();
		SQLQuery query = session.createSQLQuery(
				"update book b set b.issueStatus =:issueStatus , b.userId =:userId where b.bookId=:bookId");
		query.setString("issueStatus", BOOK_NOT_ISSUED);
		query.setString("userId", null);
		query.setInteger("bookId", bookId);
		int result= query.executeUpdate();
		System.out.println(result);

	}
}
