<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://struts-menu.sf.net/tag" prefix="menu" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page import="org.acegisecurity.context.SecurityContextHolder" %>
<%@ page import="org.acegisecurity.Authentication" %>
<%@ page import="org.acegisecurity.ui.AbstractProcessingFilter" %>
<%@ page import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter" %>
<%@ page import="org.acegisecurity.AuthenticationException" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<fmt:bundle basename="tonguetied">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" media="all" type="text/css" href="<c:url value="/css/tonguetied.css"/>"/>
<link rel="stylesheet" media="all" type="text/css" href="<c:url value="/css/displaytag.css"/>"/>
<link rel="stylesheet" media="screen" type="text/css" href="<c:url value="/css/tabs.css"/>"/>
<link rel="icon" type="image/png" href="<c:url value="/images/favicon.ico"/>"/>
<title><fmt:message key="application.short.name"/></title>
</head>

<body>
    <div>
        <div class="productDesc">
            <form id="langForm" method="get" action="<c:url value="/tonguetiedHome.htm"/>">
                <div>
                    <label for="siteLanguage" class="content"><fmt:message key="language"/></label>
                    <fmt:bundle basename="language">
                    <select id="siteLanguage" name="siteLanguage" size="1" onchange="document.langForm.submit();">
                        <c:forEach items="${applicationScope.supportedLanguages}" var="langCode">
                        <option value="<c:out value="${langCode}"/>" <c:if test="${langCode == rc.locale}">selected="selected"</c:if>><fmt:message key="${langCode}"/></option>
                        </c:forEach>
                    </select>
                    </fmt:bundle>
                </div>
            </form>
            <form id="logoutForm" method="post" action="<c:url value="j_acegi_exit_user"/>">
                <div>
                    <fmt:message key="currentUser"/><c:out value="${ACEGI_SECURITY_LAST_USERNAME}"/>
    <% 
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            auth.getPrincipal().toString();
        }
    %>
                    <input type="submit" id="logout" name="logout" value="<fmt:message key="logout"/>" class="button"/>
                </div>
            </form>
        </div>
    </div>
    <menu:useMenuDisplayer name="TabbedMenu" bundle="tonguetied" permissions="rolesAdapter">
        <menu:displayMenu name="TabbedKeywords"/>
        <menu:displayMenu name="TabbedCountries"/>
        <menu:displayMenu name="TabbedLanguages"/>
        <menu:displayMenu name="TabbedBundles"/>
        <menu:displayMenu name="TabbedExport"/>
        <menu:displayMenu name="TabbedUsers"/>
        <menu:displayMenu name="TabbedAuditLog"/>
    </menu:useMenuDisplayer>
    <div class="tabBody">
    <decorator:body />
    </div>
    <div class="pagefooter">
        <fmt:message key="application.short.name"/> 
        (<fmt:message key="version"/> : 
        <fmt:bundle basename="buildNumber">
        <fmt:message key="version"/>&nbsp;#<fmt:message key="build.number"/>
        </fmt:bundle>)
    </div>
    <script type="text/javascript" src="<c:url value="/scripts/tabs.js"/>"></script>
</body>
</fmt:bundle>
</html>