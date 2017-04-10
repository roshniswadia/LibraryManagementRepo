package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bean.Book;
import bean.User;
import service.UserService;

@Controller
public class UserController {

	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/user")
	public ModelAndView showform() {
		return new ModelAndView("userlogin");
	}

	@RequestMapping("/userLogout")
	public ModelAndView logout(final HttpServletRequest request) {
		final HttpSession session = request.getSession();
		session.invalidate();
		return new ModelAndView("userlogin");
	}

	@RequestMapping("/loginRedirectUser")
	public ModelAndView redirectform() {
		return new ModelAndView("userlogin");
	}

	@RequestMapping("/userhomePage")
	public ModelAndView userhomePageform(final Model model, @RequestParam(value = "userId") final String userId,
			@RequestParam(value = "userLoginName", required = true) final String userLoginName, final HttpServletRequest req,
			final HttpServletResponse res) {
		checkLoginAttributes(req, userLoginName, res);
		model.addAttribute("userLoginName", userLoginName);
		return new ModelAndView("userhomepage", "userId", userId);
	}

	@RequestMapping(value = "/userLogin", method = RequestMethod.POST)
	public ModelAndView checkAdminLogin(final Model model, @ModelAttribute("user") final User user,
			final HttpServletRequest request) {
		if (user != null && user.getUserLoginName() != null & user.getPassword() != null) {

			final int userId = userService.checkUserLogin(user.getUserLoginName(), user.getPassword());

			if (userId == 0) {
				model.addAttribute("error", "Invalid Details");
				return new ModelAndView("userlogin");
			} else {
				final HttpSession session = request.getSession();
				session.setAttribute("userLoginName", user.getUserLoginName());
				model.addAttribute("userLoginName", user.getUserLoginName());
				model.addAttribute("msg", "welcome" + user.getUserLoginName());
				return new ModelAndView("userhomepage", "userId", userId);
			}
		} else {
			model.addAttribute("error", "Please enter Details");
			return new ModelAndView("userlogin");
		}
	}

	@RequestMapping(value = "/viewAvailableBooks", method = RequestMethod.GET)
	public ModelAndView viewAvailableBooks(final Model model,
			@RequestParam(value = "userId", required = true) final String userId,
			@RequestParam(value = "userLoginName", required = true) final String userLoginName, final HttpServletRequest req,
			final HttpServletResponse res) {

		checkLoginAttributes(req, userLoginName, res);
		model.addAttribute("userLoginName", userLoginName);
		final List<Book> bookList = userService.viewAllAvailableBooks(Integer.parseInt(userId));
		model.addAttribute("userId", userId);
		model.addAttribute("userLoginName", userLoginName);

		return new ModelAndView("userbooklist", "bookList", bookList);
	}

	@RequestMapping(value = "/issue", method = RequestMethod.GET)
	public ModelAndView issueBook(final Model model,
			@RequestParam(value = "userId", required = true) final String userId,
			@RequestParam(value = "bookId", required = true) final String bookId,
			@RequestParam(value = "userLoginName", required = true) final String userLoginName, final HttpServletRequest req,
			final HttpServletResponse res) {
		checkLoginAttributes(req, userLoginName, res);
		userService.issueBook(Integer.parseInt(userId), Integer.parseInt(bookId));
		model.addAttribute("userId", userId);
		model.addAttribute("userLoginName", userLoginName);
		final List<Book> bookList = userService.viewIssuedBookList(Integer.parseInt(userId));
		return new ModelAndView("userissuedbook", "bookList", bookList);
	}

	@RequestMapping(value = "/return", method = RequestMethod.GET)
	public ModelAndView returnBook(final Model model,
			@RequestParam(value = "userId", required = true) final String userId,
			@RequestParam(value = "bookId", required = true) String bookId,
			@RequestParam(value = "userLoginName", required = true) final String userLoginName, final HttpServletRequest req,
			final HttpServletResponse res) {
		checkLoginAttributes(req, userLoginName, res);
		userService.returnBook(Integer.parseInt(userId), Integer.parseInt(bookId));
		model.addAttribute("userId", userId);
		model.addAttribute("userLoginName", userLoginName);
		final List<Book> bookList = userService.viewIssuedBookList(Integer.parseInt(userId));
		return new ModelAndView("userissuedbook", "bookList", bookList);
	}

	@RequestMapping(value = "/viewAllIssuedBooks", method = RequestMethod.GET)
	public ModelAndView viewIssuedBookList(final Model model,
			@RequestParam(value = "userId", required = true) final String userId,
			@RequestParam(value = "userLoginName", required = true) final String userLoginName,final HttpServletRequest req,
			final HttpServletResponse res) {
		checkLoginAttributes(req, userLoginName, res);
		final List<Book> bookList = userService.viewIssuedBookList(Integer.parseInt(userId));
		model.addAttribute("userId", userId);
		model.addAttribute("userLoginName", userLoginName);
		return new ModelAndView("userissuedbook", "bookList", bookList);

	}

	@ExceptionHandler(RuntimeException.class)
	public ModelAndView handle(final RuntimeException ex) {
		return new ModelAndView("error", "exception", ex);
	}

	public void checkLoginAttributes(final HttpServletRequest request,final String userLoginName, final HttpServletResponse response) {

		System.out.println("IN CHECK LOGIN");
		HttpSession session = request.getSession(false);

		if (session.getAttribute("userLoginName") == null) {
			try {
				response.sendRedirect("loginRedirectUser");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (!(session.getAttribute("userLoginName").equals(userLoginName))) {
			try {
				response.sendRedirect("loginRedirectUser");
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
	}
}
