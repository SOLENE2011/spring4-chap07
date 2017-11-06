package net.madvirus.spring4.chap07.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/auth/login")
public class LoginController {

	private static final String LOGIN_FORM = "auth/loginForm";
	private Authenticator authenticator;

	@RequestMapping(method = RequestMethod.GET)
	public String loginForm(LoginCommand loginCommand) {
		return LOGIN_FORM;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String login(@Valid LoginCommand loginCommand, Errors errors,
			HttpServletRequest request) {
			// 상황에 따라 세션을 생성하고 싶다면 HttpSession을 파라미터로 받으면 안됨
			// HttpServletRequest 받아서 상황에 따라 직접 생성해주어야한다.
		
		if (errors.hasErrors()) {
			return LOGIN_FORM;
		}
		try {
			Auth auth = authenticator.authenticate(loginCommand.getEmail(), loginCommand.getPassword());
			HttpSession session = request.getSession();
			session.setAttribute("auth", auth);
			return "redirect:/index.jsp";
		} catch (AuthenticationException ex) {
			errors.reject("invalidIdOrPassword");
			// 객체 자체에 문제가 있는경우에 
			// 이렇게 글로벌 에러 정보를 추가할 수 있다.
			// 글로벌 에러코드를 invalidIdOrPassword로 등록했을 때
			// 에러코드 : invalidIdOrPassword 이고
			// 커맨드 객체의 모델 이름은 loginCommand 이다.
			// 따라서 messages/error.properties에
			// 1. invalidIdOrPassword.loginCommand 없으면
			// 2. invalidIdOrPassword로 메시지를 찾는다.
			return LOGIN_FORM;
		}
	}

	@InitBinder
	// 객체를 검증할지 여부는 여기서 결정함
	// 위세서는 에러가 나면 폼을 보여주는 설정만 되어있음.
	// login() 메서드를 설정하기 전에 @Valid가 붙은 커맨드 객체를 검증한다.
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new LoginCommandValidator());
	}

	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}

}
