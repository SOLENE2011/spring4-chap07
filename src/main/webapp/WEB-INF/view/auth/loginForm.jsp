<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head><title>로그인</title></head>
<body>

<form:form commandName="loginCommand">
<!-- 폼 태그에서 커맨드 객체 정보를 사용할 수 있다 -->
<form:errors element="div" />
<label for="email">이메일</label>: 
<input type="text" name="email" id="email" value="${loginCommand.email}">
<form:errors path="email"/> <br>
<!-- form:form 태그에서 지정한 커맨드 객체의 에러코드를 이용해 에러메시지 출력 -->

<label for="password">암호</label>: 
<input type="password" name="password" id="password">
<form:errors path="password"/> <br>
<br/>

<input type="submit" value="로그인">

</form:form>

<ul>
	<li>이메일/암호로 yuna@yuna.com/yuna 입력 또는 sanghwa@sanghwa.com/sanghwa 로 테스트</li>
</ul>
</body>
</html>