package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bean.Admin;
import bean.Book;
import bean.User;
import service.AdminService;

@Controller
public class AdminController {

	private static final String NULL_DETAILS = "Please enter Details";
	private static final String WRONG_DETAILS = "Invalid Details";
	private AdminService adminService;

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	@RequestMapping("/admin")
	public ModelAndView showform() {
		return new ModelAndView("adminlogin");
	}

	@RequestMapping("/logout")
	public ModelAndView logout(final HttpServletRequest request) {
		final HttpSession session = request.getSession();
		session.invalidate();
		return new ModelAndView("adminlogin");
	}

	@RequestMapping("/homePage")
	public ModelAndView showHomePage(Model model,
			@RequestParam(value = "userName", required = true) final String userName, final HttpServletRequest req,
			final HttpServletResponse res) {
		verifyLoginAttributes(req, userName, res);
		model.addAttribute("userName", userName);
		return new ModelAndView("adminhomepage");
	}

	@RequestMapping("/loginRedirect")
	public ModelAndView redirectPage() {
		return new ModelAndView("adminlogin");
	}

	/**
	 * Returns the Admin's HomePage if login credential are correct. If the
	 * credentials are wrong or if user enters null values it will redirect to
	 * the login Page.
	 * 
	 * @model The model.
	 * @param admin
	 *            An object of bean Admin which contain the username and
	 *            password.
	 * 
	 * @return ModelAndView
	 */

	@RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
	public ModelAndView verifyAdminLogin(final Model model, @ModelAttribute("admin") final Admin admin,
			final HttpServletRequest request) {

		System.out.println(admin.getAdminLoginName() + "  -- " + admin.getPassword());
		if (admin != null && admin.getAdminLoginName() != null & admin.getPassword() != null) {

			final boolean result = adminService.checkAdminLogin(admin.getAdminLoginName(), admin.getPassword());

			if (result == true) {
				final HttpSession session = request.getSession();
				session.setAttribute("username", admin.getAdminLoginName());
				model.addAttribute("userName", admin.getAdminLoginName());
				model.addAttribute("message", "welcome" + admin.getAdminLoginName());
				return new ModelAndView("adminhomepage");
			} else {
				return new ModelAndView("adminlogin", "error", WRONG_DETAILS);
			}
		} else {
			return new ModelAndView("adminlogin", "error", NULL_DETAILS);
		}
	}

	/**
	 * It is used to view all the Books present in the library.
	 * 
	 * @return the complete list of all books
	 */
	@RequestMapping(value = "/viewAllBooks", method = RequestMethod.GET)
	public ModelAndView viewAllBooks(final Model model,
			@RequestParam(value = "userName", required = true) final String userName, final HttpServletRequest req,
			final HttpServletResponse res) {
		verifyLoginAttributes(req, userName, res);
		model.addAttribute("userName", userName);

		final List<Book> bookList = adminService.viewAllBooks();
		return new ModelAndView("booklist", "bookList", bookList);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView editBook(final Model model, @RequestParam(value = "bookId", required = true) final String bookId,
			@RequestParam(value = "userName", required = true) final String userName, final HttpServletRequest req,
			final HttpServletResponse res) {
		verifyLoginAttributes(req, userName, res);
		final Book book = adminService.getBook(Integer.parseInt(bookId));
		// model.addAttribute("user", book.getUser());
		model.addAttribute("userName", userName);
		return new ModelAndView("modifybook", "book", book);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView editOrSaveBook(final Model model, @ModelAttribute("book") final Book book,
			@RequestParam(value = "userName", required = true) final String userName, final HttpServletRequest req,
			final HttpServletResponse res) {
		verifyLoginAttributes(req, userName, res);
		adminService.updateBook(book);
		model.addAttribute("userName", userName);
		final List<Book> bookList = adminService.viewAllBooks();
		return new ModelAndView("booklist", "bookList", bookList);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteBook(final Model model,
			@RequestParam(value = "bookId", required = true) final String bookId,
			@RequestParam(value = "userName", required = true) final String userName, final HttpServletRequest req,
			final HttpServletResponse res) {
		verifyLoginAttributes(req, userName, res);
		final boolean isIssued = adminService.deleteBook(Integer.parseInt(bookId));

		if (isIssued == true) {
			model.addAttribute("error", "Issued book cannot be deleted");
		}

		model.addAttribute("userName", userName);
		final List<Book> bookList = adminService.viewAllBooks();
		return new ModelAndView("booklist", "bookList", bookList);
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ModelAndView addNewBook(Model model, @ModelAttribute("book") final Book book,
			@RequestParam(value = "userName", required = true) final String userName, final HttpServletRequest req,
			final HttpServletResponse res) {
		verifyLoginAttributes(req, userName, res);
		adminService.addNewBook(book);
		model.addAttribute("userName", userName);
		final List<Book> bookList = adminService.viewAllBooks();
		return new ModelAndView("booklist", "bookList", bookList);
	}

	@RequestMapping("/addBook")
	public ModelAndView showAddEditBookPage(Model model,
			@RequestParam(value = "userName", required = true) final String userName, final HttpServletRequest req,
			final HttpServletResponse res) {
		verifyLoginAttributes(req, userName, res);
		model.addAttribute("userName", userName);
		return new ModelAndView("modifybook");
	}

	@RequestMapping(value = "/viewAllUser", method = RequestMethod.GET)
	public ModelAndView viewAllUsers(final Model model,
			@RequestParam(value = "userName", required = true) final String userName, final HttpServletRequest req,
			final HttpServletResponse res) {
		verifyLoginAttributes(req, userName, res);
		model.addAttribute("userName", userName);
		final List<User> userList = adminService.viewAllUsers();
		return new ModelAndView("userlist", "userList", userList);
	}

	@ExceptionHandler(RuntimeException.class)
	public ModelAndView handle(final RuntimeException ex) {
		return new ModelAndView("error", "exception", ex);
	}

	public void verifyLoginAttributes(final HttpServletRequest request, final String Username,
			final HttpServletResponse response) {

		HttpSession session = request.getSession(false);
		if (session.getAttribute("username") == null) {
			try {
				response.sendRedirect("loginRedirect");
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		} else if (!(session.getAttribute("username").equals(Username))) {
			try {
				response.sendRedirect("loginRedirect");
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}
}
