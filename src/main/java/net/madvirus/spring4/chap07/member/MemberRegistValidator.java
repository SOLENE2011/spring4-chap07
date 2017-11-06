package net.madvirus.spring4.chap07.member;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class MemberRegistValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return MemberRegistRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MemberRegistRequest regReq = (MemberRegistRequest) target;
		if (regReq.getEmail() == null || regReq.getEmail().trim().isEmpty())
			errors.rejectValue("email", "required");
			// 잘못된 프로퍼티 등록.
			// rejectValue 한번 이상 호출하면 bindingResult.hasErrors() 메서드 true 리턴
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "required");
		if (regReq.hasPassword()) {
			if (regReq.getPassword().length() < 5)
				errors.rejectValue("password", "shortPassword");
			else if (!regReq.isSamePasswordConfirmPassword())
				errors.rejectValue("confirmPassword", "notSame");
		}
		Address address = regReq.getAddress();
		if (address == null) {
			errors.rejectValue("address", "required");
			// "address" 값이 잘못 되었고 에러코드로 required를 사용한다는 의미
		} else {
			errors.pushNestedPath("address");
			try {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address1", "required");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address2", "required");
				// target 객체의 name 프로퍼트 값이 null 이거나 길이가 0인 경우
				// errors 객체에 name 프로퍼티의 에러코드로 required를 사용한다.
			} finally {
				errors.popNestedPath();
			}
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthday", "required");
	}

}
