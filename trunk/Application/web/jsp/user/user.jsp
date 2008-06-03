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
                <form:label path="username" cssClass="content"><fmt:message key="username"/></form:label>
                <form:input path="username"/>
                <form:errors path="username" cssClass="error"/>
            </div>
            user id = ${user.id eq null}
            <c:choose>
                <c:when test="${user.id eq null}">
                    <div>
                        <form:label path="password" cssClass="content"><fmt:message key="password"/></form:label>
                        <form:password path="password"/>
                        <form:errors path="password" cssClass="error"/>
                    </div>
                    <div>
                        <form:label path="repeatedPassword" cssClass="content"><fmt:message key="repeated.password"/></form:label>
                        <form:password path="repeatedPassword"/>
                        <form:errors path="repeatedPassword" cssClass="error"/>
                    </div>
                </c:when>
            </c:choose>
            <div>
                <form:label path="firstName" cssClass="content"><fmt:message key="first.name"/></form:label>
                <form:input path="firstName"/>
                <form:errors path="firstName" cssClass="error"/>
            </div>
            <div>
                <form:label path="lastName" cssClass="content"><fmt:message key="last.name"/></form:label>
                <form:input path="lastName"/>
                <form:errors path="lastName" cssClass="error"/>
            </div>
            <div>
                <form:label path="email" cssClass="content"><fmt:message key="email"/></form:label>
                <form:input path="email"/>
                <form:errors path="email" cssClass="error"/>
            </div>
            <div>
                <form:label path="accountNonExpired" cssClass="content"><fmt:message key="is.account.non.expired"/></form:label>
                <form:checkbox path="accountNonExpired" id="accountNonExpired"/>
            </div>
            <div>
                <form:label path="accountNonLocked" cssClass="content"><fmt:message key="is.account.non.locked"/></form:label>
                <form:checkbox path="accountNonLocked" id="accountNonLocked"/>
            </div>
            <div>
                <form:label path="enabled" cssClass="content"><fmt:message key="is.enabled"/></form:label>
                <form:checkbox path="enabled" id="enabled"/>
            </div>
            <div>
                <form:label path="credentialsNonExpired" cssClass="content"><fmt:message key="is.credentials.non.expired"/></form:label>
                <form:checkbox path="credentialsNonExpired" id="credentialsNonExpired"/>
            </div>
        </fieldset>
        <fieldset>
            <legend><fmt:message key="authorities"/></legend>
            <c:forEach items="${user.userRights}" var="userRight" varStatus="index">
            <form:hidden path="userRights[${index.index}].user.id" id="userRight${index.index}.user.id"/>
            <div>
                <form:label path="userRights[${index.index}].permission" id="userRight${index.index}.permission.label" for="userRight${index.index}.permission" cssClass="content"><fmt:message key="user.role"/></form:label>
                <form:select path="userRights[${index.index}].permission" id="userRight${index.index}.permission" size="1">
                    <form:option value=""><fmt:message key="please.select"/></form:option>
                    <form:options items="${permissions}"/>
                </form:select>
            </div>
            </c:forEach>
        </fieldset>
        <div class="submit">
            <input type="submit" id="save" name="save" value="<fmt:message key="save"/>" class="button"/>
            <input type="submit" id="_cancel" name="_cancel" value="<fmt:message key="cancel"/>" class="button"/>
            <input type="reset" id="reset" name="reset" value="<fmt:message key="reset"/>" class="button"/>
        </div>
    </form:form>
    </div>
