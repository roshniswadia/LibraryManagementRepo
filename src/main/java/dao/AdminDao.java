package dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import bean.Admin;
import bean.Book;
import bean.User;

public class AdminDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public boolean checkAdminLogin(final String adminLoginName, final String password) {

		final Session session = this.sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("from Admin a where a.adminLoginName =:adminLoginName and a.password =:password");
		query.setString("adminLoginName", adminLoginName);
		query.setString("password", password);

		final Admin admin = (Admin) query.uniqueResult();

		if (admin != null)
			return true;
		else
			return false;
	}

	public List<Book> viewAllBooks() {

		final Session session = this.sessionFactory.getCurrentSession();
		final List<Book> empList = session.createQuery("from Book").list();
		return empList;

	}

	@Transactional
	public Book getBook(final int bookId) {

		final Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Book b where b.bookId =:bookId");
		query.setInteger("bookId", bookId);

		final Book emp = (Book) query.uniqueResult();

		return emp;
	}

	public void updateBook(final Book book) {

		final Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("update Book b set b.bookName = :bookName ,b.authorName =:authorName , "
				+ "b.issueStatus =:issueStatus,b.amount =:amount where b.bookId =:bookId");
		query.setString("bookName", book.getBookName());
		query.setString("authorName", book.getAuthorName());
		query.setString("issueStatus", book.getIssueStatus());
		query.setInteger("amount", book.getAmount());

		query.setInteger("bookId", book.getBookId());
		query.executeUpdate();
	}

	public boolean deleteBook(final int bookId) {

		final Session session = this.sessionFactory.getCurrentSession();
		boolean isIssued = false;
		Query query = session.createQuery("select issueStatus from Book b where b.bookId =:bookId");
		query.setInteger("bookId", bookId);
		final String issueStatus = (String) query.uniqueResult();

		if ("issued".equalsIgnoreCase(issueStatus)) {
			isIssued = true;
		} else {
			Query query2 = session.createQuery("delete from Book e where e.bookId =:bookId");
			query2.setInteger("bookId", bookId);
			query2.executeUpdate();
			isIssued = false;
		}

		return isIssued;
	}

	public void addNewBook(final Book book) {

		final Session session = this.sessionFactory.getCurrentSession();
		session.persist(book);
	}

	public List<User> viewAllUsers() {

		final Session session = this.sessionFactory.getCurrentSession();
		final List<User> userList = session.createQuery("from User").list();
		return userList;
	}

}
