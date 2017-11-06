package net.madvirus.spring4.chap07.member;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/member/regist")
public class RegistrationController {
	private static final String MEMBER_REGISTRATION_FORM = "member/registrationForm";
	
	private MemberService memberService;

	@RequestMapping(method = RequestMethod.GET)
	public String form(@ModelAttribute("memberInfo") MemberRegistRequest memRegReq) {
		return MEMBER_REGISTRATION_FORM;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String regist(
			@ModelAttribute("memberInfo") MemberRegistRequest memRegReq,
			BindingResult bindingResult) {
			// BindingResult : 에러 정보를 보관.
		// BindingResult와 Errors 는 객체 바로 뒤에 위치해야 한다.
		new MemberRegistValidator().validate(memRegReq, bindingResult);
		// validate() 메서드를 실행한 후 오류가 존재하면 bindingResult.hasErrors() 메서드는 true 리턴
		if (bindingResult.hasErrors()) {
			return MEMBER_REGISTRATION_FORM;
		}
		memberService.registNewMember(memRegReq);
		return "member/registered";
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

}
