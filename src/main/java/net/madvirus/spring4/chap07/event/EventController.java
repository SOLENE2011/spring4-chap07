package net.madvirus.spring4.chap07.event;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/event")
// controller와 메서드에 @RequestMapping 모두 사용하면 조합해서 매핑 경로 결정. 
public class EventController {
	private static final String REDIRECT_EVENT_LIST = "redirect:/event/list";
	private EventService eventService;

	public EventController() {
		eventService = new EventService();
	}

	@ModelAttribute("recEventList")
	public List<Event> recommend() {
		return eventService.getRecommendedEventService();
	}

	@RequestMapping("/list")
	public String list(SearchOption option, Model model) {
		// 모델에 데이터를 담을 수 있는 가장 간단한 방법은
		// Model을 파라미터로 추가하고 Model 파라미터에 데이터 추가하는 방법이다.
		// ModelMap model로 해도 동일함.
		List<Event> eventList = eventService.getOpenedEventList(option);
		model.addAttribute("eventList", eventList); //이름과 값 입력받음.
		model.addAttribute("eventTypes", EventType.values());
		return "event/list";
		// model을 사용하는 경우 뷰 이름을 리턴함.
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		CustomDateEditor dateEditor = new CustomDateEditor(new SimpleDateFormat("yyyyMMdd"), true);
		binder.registerCustomEditor(Date.class, dateEditor);
	}

	@RequestMapping("/detail")
	public String detail(HttpServletRequest request, Model model) throws IOException {
		String id = request.getParameter("id");
		if (id == null)
			return REDIRECT_EVENT_LIST;
		Long eventId = null;
		try {
			eventId = Long.parseLong(id);
		} catch (NumberFormatException e) {
			return REDIRECT_EVENT_LIST;
		}
		Event event = getEvent(eventId);
		if (event == null)
			return REDIRECT_EVENT_LIST;

		model.addAttribute("event", event);
		return "event/detail";
	}

	private Event getEvent(Long eventId) {
		return eventService.getEvent(eventId);
	}

	@RequestMapping("/list2")
	public ModelAndView list2(SearchOption option) {
		List<Event> eventList = eventService.getOpenedEventList(option);
		ModelAndView modelView = new ModelAndView();
		modelView.setViewName("event/list"); // 뷰 이름지정(setViewName 이용)
		modelView.addObject("eventList", eventList);
		modelView.addObject("eventTypes", EventType.values());
		return modelView;
	}

	@RequestMapping("/detail2")
	public String detail2(@RequestParam("id") long eventId, Model model) {
		Event event = getEvent(eventId);
		if (event == null)
			return REDIRECT_EVENT_LIST;
		model.addAttribute("event", event);
		return "event/detail";
	}
}
