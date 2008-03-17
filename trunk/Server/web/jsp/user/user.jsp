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
            <legend><fmt:message key="userDetails"/></legend>
            <div>
                <form:label path="username" cssClass="content"><fmt:message key="username"/></form:label>
                <form:input path="username"/>
                <form:errors path="username" cssClass="error"/>
            </div>
            <div>
                <form:label path="password" cssClass="content"><fmt:message key="password"/></form:label>
                <form:password path="password"/>
                <form:errors path="password" cssClass="error"/>
            </div>
            <div>
                <form:label path="repeatedPassword" cssClass="content"><fmt:message key="repeatedPassword"/></form:label>
                <form:password path="repeatedPassword"/>
                <form:errors path="repeatedPassword" cssClass="error"/>
            </div>
            <div>
                <form:label path="firstName" cssClass="content"><fmt:message key="firstName"/></form:label>
                <form:input path="firstName"/>
                <form:errors path="firstName" cssClass="error"/>
            </div>
            <div>
                <form:label path="lastName" cssClass="content"><fmt:message key="lastName"/></form:label>
                <form:input path="lastName"/>
                <form:errors path="lastName" cssClass="error"/>
            </div>
            <div>
                <form:label path="email" cssClass="content"><fmt:message key="email"/></form:label>
                <form:input path="email"/>
                <form:errors path="email" cssClass="error"/>
            </div>
            <div>
                <form:label path="accountNonExpired" cssClass="content"><fmt:message key="isAccountNonExpired"/></form:label>
                <form:checkbox path="accountNonExpired"/>
            </div>
            <div>
                <form:label path="accountNonLocked" cssClass="content"><fmt:message key="isAccountNonLocked"/></form:label>
                <form:checkbox path="accountNonLocked"/>
            </div>
            <div>
                <form:label path="enabled" cssClass="content"><fmt:message key="isEnabled"/></form:label>
                <form:checkbox path="enabled"/>
            </div>
            <div>
                <form:label path="credentialsNonExpired" cssClass="content"><fmt:message key="isCredentialsNonExpired"/></form:label>
                <form:checkbox path="credentialsNonExpired"/>
            </div>
        </fieldset>
        <fieldset>
            <legend><fmt:message key="authorities"/></legend>
            <c:forEach items="${user.userRights}" var="userRight" varStatus="index">
            <form:hidden path="userRights[${index.index}].user.id" id="userRight${index.index}.user.id"/>
            <div>
                <form:label path="userRights[${index.index}].permission" id="userRight${index.index}.permission" cssClass="content"><fmt:message key="permission"/></form:label>
                <form:select path="userRights[${index.index}].permission" id="userRight${index.index}.permission" size="1">
                    <form:option value=""><fmt:message key="pleaseSelect"/></form:option>
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
