package net.madvirus.spring4.chap07.file;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
// 여러 응답 코드를 위한 열거 값을 정의하고 있다.
// (HttpStatus.NOT_FOUND) 404에러 해당
// 에러를 직접 정하고 싶을때
// 500은 Internal_Server_Error 
public class NoFileInfoException extends Exception {

	private static final long serialVersionUID = 1L;

}
