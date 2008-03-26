<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

    <div class="content">
    <c:url value="/import.htm" var="importAction" scope="page"/>
    <form:form id="importForm" method="post" action="${exportAction}" commandName="import" enctype="multipart/form-data">
        <fieldset>
            <legend><fmt:message key="importDetails"/></legend>
            <div>
                <form:label path="file" cssClass="content"><fmt:message key="file"/></form:label>
                <input type="file" name="file">
            </div>
            <div>
                <form:label path="formatType" cssClass="content"><fmt:message key="fileType"/></form:label>
                <form:select path="formatType" size="1">
                    <form:option value=""><fmt:message key="pleaseSelect"/></form:option>
                    <form:options items="${formatTypes}"/>
                </form:select>
            </div>
        </fieldset>
        <div class="submit">
            <input type="submit" id="import" name="import" value="<fmt:message key="import"/>" class="button"/>
            <input type="submit" id="_cancel" name="_cancel" value="<fmt:message key="cancel"/>" class="button"/>
            <input type="reset" id="reset" name="reset" value="<fmt:message key="reset"/>" class="button"/>
        </div>
    </form:form>
    </div>
