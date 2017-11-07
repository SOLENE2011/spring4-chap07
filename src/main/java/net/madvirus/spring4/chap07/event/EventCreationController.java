package net.madvirus.spring4.chap07.event;

import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("eventForm")
// 각 화면 간 모델 데이터 공유하기 위한 annotation
// 임시 목적으로 사용될 객체를 세션에 보관할 때
public class EventCreationController {

	private static final String EVENT_CREATION_STEP1 = "event/creationStep1";
	private static final String EVENT_CREATION_STEP2 = "event/creationStep2";
	private static final String EVENT_CREATION_STEP3 = "event/creationStep3";
	private static final String EVENT_CREATION_DONE = "event/creationDone";

//	@RequestMapping("/newevent/step1")
//	public String step1(Model model) {
//		model.addAttribute("eventForm", new EventForm());
// SessionAttributes가 제대로 동작하려면 모델에 같은 이름을 갖는 객체 추가해야함
// 보통 첫번째 단계를 처리하는 컨트롤러 메서드에서 모델에 객체를 추가한다.
//		return EVENT_CREATION_STEP1;
//	}

	// 또 다른 방법
	// 모델 객체를 추가할 필요가 없음.
	// 세션에 동일한 이름을 갖는 객체가 존재하면
	// 기존 것을 모델 객체로 사용한다.
	@ModelAttribute("eventForm")
	public EventForm formData() {
		return new EventForm();
	}

	@RequestMapping("/newevent/step1")
	public String step1() {
		return EVENT_CREATION_STEP1;
	}
		
	@RequestMapping(value = "/newevent/step2", method = RequestMethod.POST)
	public String step2(@ModelAttribute("eventForm") EventForm formData, BindingResult result) {
		new EventFormStep1Validator().validate(formData, result);
		if (result.hasErrors())
			return EVENT_CREATION_STEP1;
		return EVENT_CREATION_STEP2;
	}

	@RequestMapping(value = "/newevent/step2", method = RequestMethod.GET)
	public String step2FromStep3(@ModelAttribute("eventForm") EventForm formData) {
		return EVENT_CREATION_STEP2;
	}

	@RequestMapping(value = "/newevent/step3", method = RequestMethod.POST)
	public String step3(@ModelAttribute("eventForm") EventForm formData, BindingResult result) {
		ValidationUtils.rejectIfEmpty(result, "target", "required");
		if (result.hasErrors())
			return EVENT_CREATION_STEP2;
		return EVENT_CREATION_STEP3;
	}

	@RequestMapping(value = "/newevent/done", method = RequestMethod.POST)
	public String done(@ModelAttribute("eventForm") EventForm formData, SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		// 세션에서 객체를 제거함.
		// 하지만 세션에서만 제거할뿐 모델의 값으로는 계속 사용할 수 있다.
		return EVENT_CREATION_DONE;
	}

}
