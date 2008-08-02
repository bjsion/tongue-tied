<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<fmt:bundle basename="tonguetied">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" media="all" type="text/css" href="<c:url value="/css/tonguetied.css"/>"/>
<link rel="icon" type="image/png" href="<c:url value="/images/favicon.ico"/>"/>
<title><fmt:message key="application.short.name"/></title>
</head>

<body>
    <img src="<c:url value="/images/application_logo.png"/>" alt="<fmt:message key="application.logo"/>" title="<fmt:message key="application.logo"/>" class="logo"/>
    <h1><fmt:message key="logout.successful"/></h1>
    <p>
        <fmt:message key="logout.successful.text"/>
        <a href="<c:url value="/index.jsp"/>"><fmt:message key="login.again"/></a>
    </p>
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