<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="org.acegisecurity.ui.AbstractProcessingFilter" %>
<%@ page import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter" %>
<%@ page import="org.acegisecurity.AuthenticationException" %>
<%@ page import="org.acegisecurity.context.SecurityContextHolder" %>
<%@ page import="org.acegisecurity.Authentication" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<fmt:bundle basename="tonguetied">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" media="all" type="text/css" href="<c:url value="/css/tonguetied.css"/>"/>
<title><fmt:message key="application.short.name"/></title>
</head>

<body>
    <h1><fmt:message key="login"/></h1>
    <%-- this form-login-page form is also used as the 
         form-error-page to ask for a login again.
         --%>

    <form action="<c:url value='j_acegi_security_check'/>" method="post">
        <fieldset>
            <legend><fmt:message key="login"/></legend>
            <c:if test="${not empty param.login_error}">
              <span class="error">
                Your login attempt was not successful, try again.
                Reason: <%= ((AuthenticationException) session.getAttribute(AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY)).getMessage() %>
              </span>
            </c:if>
            <div>
                <label id="labelUsername" for="j_username"><fmt:message key="username"/></label>
                <input type="text" id="j_username" name="j_username" <c:if test="${not empty param.login_error}">value="<c:out value="${ACEGI_SECURITY_LAST_USERNAME}"/>"</c:if>/>
            </div>
            <div>
                <label id="labelPassword" for="j_password"><fmt:message key="password"/></label>
                <input type="password" id="j_password" name="j_password"/>
            </div>
            <div>
                <input type="checkbox" id="_acegi_security_remember_me" name="_acegi_security_remember_me"/>
                <label id="labelRememberMe" for="_acegi_security_remember_me"><fmt:message key="rememberMe"/></label>
            </div>
        </fieldset>
        <div>
    	   <input name="submit" type="submit" value="<fmt:message key="login"/>"/>
           <input name="reset" type="reset" value="<fmt:message key="reset"/>"/>
        </div>
    </form>
</body>
</fmt:bundle>
</html>