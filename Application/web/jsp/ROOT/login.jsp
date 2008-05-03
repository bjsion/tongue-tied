<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="org.springframework.security.ui.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.AuthenticationException" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<fmt:bundle basename="tonguetied">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" media="all" type="text/css" href="<c:url value="/css/tonguetied.css"/>"/>
<link rel="icon" type="image/png" href="<c:url value="/images/favicon.ico"/>"/>
<title><fmt:message key="application.short.name"/></title>
</head>

<body>
    <div>
        <img src="<c:url value="/images/application_logo.png"/>" alt="<fmt:message key="application.logo"/>" title="<fmt:message key="application.logo"/>"/>
    </div>
    <h1><fmt:message key="login"/></h1>
    <%-- this form-login-page form is also used as the 
         form-error-page to ask for a login again.
         --%>

    <form id="loginForm" action="<c:url value='j_spring_security_check'/>" method="post">
        <fieldset>
            <legend><fmt:message key="login"/></legend>
            <c:if test="${not empty param.login_error}">
              <span class="error">
                <fmt:message key="login.unsuccessful"/>
                FIXME Reason: <%= ((AuthenticationException) session.getAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY)).getMessage() %>
              </span>
            </c:if>
            <div>
                <label id="labelUsername" for="j_username"><fmt:message key="username"/></label>
                <input type="text" id="j_username" name="j_username" <c:if test="${not empty param.login_error}">value="<c:out value="${SPRING_SECURITY_LAST_USERNAME_KEY}"/>"</c:if>/>
            </div>
            <div>
                <label id="labelPassword" for="j_password"><fmt:message key="password"/></label>
                <input type="password" id="j_password" name="j_password"/>
            </div>
            <div>
                <input type="checkbox" id="_spring_security_remember_me" name="_spring_security_remember_me"/>
                <label id="labelRememberMe" for="_spring_security_remember_me"><fmt:message key="rememberMe"/></label>
            </div>
        </fieldset>
        <div>
    	   <input name="submit" type="submit" value="<fmt:message key="login"/>"/>
           <input name="reset" type="reset" value="<fmt:message key="reset"/>"/>
        </div>
    </form>
    <div class="pagefooter">
        <fmt:message key="application.short.name"/> 
        (<fmt:message key="version"/> : 
        <fmt:bundle basename="buildNumber">
        <fmt:message key="version"/>&nbsp;#<fmt:message key="build.number"/>
        </fmt:bundle>)
    </div>
</body>
</fmt:bundle>
</html>