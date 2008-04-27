<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

    <div class="content">
        <display:table name="users" id="user" sort="page" requestURI="">
            <display:column sortable="true" titleKey="username" url="/user.htm" paramId="id" paramProperty="id">
                <img src="<c:url value="/images/user.png"/>" alt="" title="${user.username}}" class="imgLink"/>
                <c:out value="${user.username}"/>
            </display:column>
            <display:column titleKey="roles" sortable="false">
                <c:forEach items="${user.userRights}" var="userRight" varStatus="index">
                ${userRight.permission}
                </c:forEach>
            </display:column>
            <display:column property="email" titleKey="email" sortable="true"/>
            <display:column titleKey="action"/>
        </display:table>
    </div>
