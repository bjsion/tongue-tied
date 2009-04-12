<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

    <div class="content">
    <c:url value="/user.htm" var="userUrl" scope="page"/>
    <form:form id="userForm" method="post" action="${userUrl}" commandName="user">
        <%-- We need to bind the id as the form is in the request and would be lost when submitted --%>
        <fieldset>
            <form:hidden path="id"/>
            <legend><fmt:message key="user.details"/></legend>
            <div>
                <fmt:message key="username"/>&nbsp;:&nbsp;${user.username}
            </div>
            <div>
                <fmt:message key="first.name"/>&nbsp;:&nbsp;${user.firstName}
            </div>
            <div>
                <fmt:message key="last.name"/>&nbsp;:&nbsp;${user.lastName}
            </div>
            <div>
                <fmt:message key="email"/>&nbsp;:&nbsp;${user.email}
            </div>
            <div>
                <form:label path="enabled" cssClass="content"><fmt:message key="is.enabled"/></form:label>
                <form:checkbox path="enabled" id="enabled" disabled="true"/>
            </div>
        </fieldset>
        <fieldset>
            <legend><fmt:message key="authorities"/></legend>
            <c:forEach items="${user.userRights}" var="userRight" varStatus="index">
            <form:hidden path="userRights[${index.index}].user.id" id="userRight${index.index}.user.id"/>
            <div>
                <fmt:message key="user.role"/>&nbsp;:&nbsp;<fmt:message key="${userRight.permission}"/>
            </div>
            </c:forEach>
        </fieldset>
        <div class="submit">
            <input type="submit" id="_cancel" name="_cancel" value="<fmt:message key="cancel"/>" class="button"/>
        </div>
    </form:form>
    </div>
