package net.madvirus.spring4.chap07.exhandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("net.madvirus.spring4.chap07")
// 설정한 하위 패키지에 속한 컨트롤러의 공통기능
// 익셉션 처리 중복 없애기 위한.
public class CommonExceptionHandler {
	
	@ExceptionHandler(RuntimeException.class)
	public String handleException() {
		return "error/commonException";
	}
}
