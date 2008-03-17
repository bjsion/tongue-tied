<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

    <div class="content">
    <c:url value="/export.htm" var="exportAction" scope="page"/>
    <form:form id="exportForm" method="post" action="${exportAction}" commandName="export">
        <fieldset>
            <legend><fmt:message key="exportDetails"/></legend>
            <div>
                <form:label path="countries" cssClass="content"><fmt:message key="country"/></form:label>
                <form:select path="countries" multiple="true" size="5">
                    <form:options items="${countries}" itemValue="id" itemLabel="name"/>
                </form:select>
                <form:errors path="countries" cssClass="error"/>
            </div>
            <div>
                <form:label path="languages" cssClass="content"><fmt:message key="language"/></form:label>
                <form:select path="languages" multiple="true" size="5">
                    <form:options items="${languages}" itemValue="id" itemLabel="name"/>
                </form:select>
                <form:errors path="languages" cssClass="error"/>
            </div>
            <div>
                <form:label path="bundles" cssClass="content"><fmt:message key="bundle"/></form:label>
                <form:select path="bundles" multiple="true" size="5">
                    <form:options items="${bundles}" itemValue="id" itemLabel="name"/>
                </form:select>
                <form:errors path="bundles" cssClass="error"/>
            </div>
            <div>
                <form:label path="formatType" cssClass="content"><fmt:message key="exportType"/></form:label>
                <form:select path="formatType" size="1">
                    <form:option value=""><fmt:message key="pleaseSelect"/></form:option>
                    <form:options items="${formatTypes}"/>
                </form:select>
            </div>
        </fieldset>
        <div class="submit">
            <input type="submit" id="export" name="export" value="<fmt:message key="export"/>" class="button"/>
            <input type="submit" id="_cancel" name="_cancel" value="<fmt:message key="cancel"/>" class="button"/>
            <input type="reset" id="reset" name="reset" value="<fmt:message key="reset"/>" class="button"/>
        </div>
    </form:form>
    </div>
