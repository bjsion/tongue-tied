<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="authz" uri="http://acegisecurity.org/authz" %>

    <div class="content">
    <c:url value="/keyword.htm" var="keywordAction" scope="page"/>
    <form:form id="keywordForm" method="post" action="${keywordAction}" commandName="keyword">
        <%-- We need to bind the id as the form is in the request and would be lost when submitted --%>
        <form:hidden path="id"/>
        <form:hidden path="version"/>
        <fmt:message key="confirmKeywordDelete" var="confirmDeleteKeywordMsg" scope="page">
            <fmt:param value="${keyword.keyword}"/>
        </fmt:message>
        <fieldset>
            <legend><fmt:message key="keywordDetails"/></legend>
            <div>
                <form:label path="keyword" cssClass="content"><fmt:message key="keywordId"/></form:label>
                <authz:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">
                <form:input path="keyword"/>
                <form:errors path="keyword" cssClass="error"/>
                </authz:authorize>
                <authz:authorize ifNotGranted="ROLE_USER,ROLE_ADMIN">
                ${keyword.keyword}
                </authz:authorize>
            </div>
            <div>
                <form:label path="context" cssClass="content"><fmt:message key="keywordContext"/></form:label>
                <form:textarea path="context" rows="2" cols="100%"/>
                <form:errors path="context" cssClass="error"/>
            </div>
            <table>
                <caption><fmt:message key="translations"/></caption>
                <colgroup>
                    <col class="fixme"/>
                    <col width="language"/>
                    <col class="country"/>
                    <col class="bundle"/>
                    <col class="translation"/>
                    <col class="state"/>
                </colgroup>
                <thead>
                    <tr>
                        <th><fmt:message key="action"/></th>
                        <th><fmt:message key="language"/></th>
                        <th><fmt:message key="country"/></th>
                        <th><fmt:message key="bundle"/></th>
                        <th><fmt:message key="translation"/></th>
                        <th><fmt:message key="state"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${keyword.translations}" var="translation" varStatus="index">
                    <c:choose>
                        <c:when test="${index.count % 2 == 0}">
                    <tr>
                        </c:when>
                        <c:otherwise>
                    <tr class="altRow">
                        </c:otherwise>
                    </c:choose>
                        <td>
                            <form:hidden path="translations[${index.index}].id" id="translation${index.index}.id"/>
                            <form:hidden path="translations[${index.index}].version" id="translation${index.index}.version"/>
                            <authz:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">
                            <input type="image" src="<c:url value="images/delete.png"/>" alt="<fmt:message key="delete"/>" title="<fmt:message key="delete"/>" name="deleteTranslation" value="${translation.id}"/>
                            </authz:authorize>
                        </td>
                        <td>
                            <authz:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">
                            <form:select path="translations[${index.index}].language" id="translation${index.index}.language" size="1">
                                <form:option value=""><fmt:message key="pleaseSelect"/></form:option>
                                <form:options items="${languages}" itemValue="id" itemLabel="name"/>
                            </form:select>
                            </authz:authorize>
                            <authz:authorize ifNotGranted="ROLE_USER,ROLE_ADMIN">
                            ${translation.language.name}
                            </authz:authorize>
                        </td>
                        <td>
                            <authz:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">
                            <form:select path="translations[${index.index}].country" id="translation${index.index}.country" size="1">
                                <form:option value=""><fmt:message key="pleaseSelect"/></form:option>
                                <form:options items="${countries}" itemValue="id" itemLabel="name"/>
                            </form:select>
                            </authz:authorize>
                            <authz:authorize ifNotGranted="ROLE_USER,ROLE_ADMIN">
                            ${translation.country.name}
                            </authz:authorize>
                        </td>
                        <td>
                            <authz:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">
                            <form:select path="translations[${index.index}].bundle" id="translation${index.index}.bundle" size="1">
                                <form:option value=""><fmt:message key="pleaseSelect"/></form:option>
                                <form:options items="${bundles}" itemValue="id" itemLabel="name"/>
                            </form:select>
                            </authz:authorize>
                            <authz:authorize ifNotGranted="ROLE_USER,ROLE_ADMIN">
                            ${translation.bundle.name}
                            </authz:authorize>
                        </td>
                        <td>
                            <authz:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">
                            <form:textarea path="translations[${index.index}].value" id="translation${index.index}.value" rows="4" cols="100%"/>
                            </authz:authorize>
                            <authz:authorize ifNotGranted="ROLE_USER,ROLE_ADMIN">
                            <c:out value="${translation.value}" escapeXml="true"/>
                            </authz:authorize>
                        </td>
                        <td>
                            <authz:authorize ifAnyGranted="ROLE_VERIFIER">
                            <form:select path="translations[${index.index}].state" id="translation${index.index}.state" size="1">
                                <form:option value=""><fmt:message key="pleaseSelect"/></form:option>
                                <c:forEach items="${states}" var="state">
                                    <c:if test="${translation.state == state}">
                                        <c:set var="selected" value="true"/>
                                    </c:if>
                                    <form:option value="${state}"><fmt:message key="${state}"/></form:option>
                                    <c:remove var="selected"/>
                                </c:forEach>
                            </form:select>
                            </authz:authorize>
                            <authz:authorize ifNotGranted="ROLE_VERIFIER">
                            <fmt:message key="${translation.state}"/>
                            </authz:authorize>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </fieldset>
        <div class="submit">
            <authz:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">
            <input type="submit" id="add" name="add" value="<fmt:message key="addTranslation"/>" class="button"/>
            </authz:authorize>
            <input type="submit" id="save" name="save" value="<fmt:message key="save"/>" class="button"/>
            <input type="submit" id="_cancel" name="_cancel" value="<fmt:message key="cancel"/>" class="button"/>
            <input type="reset" id="reset" name="reset" value="<fmt:message key="reset"/>" class="button"/>
            <authz:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">
            <input type="submit" id="delete" name="delete" value="<fmt:message key="removeKeyword"/>" class="button" onclick="return confirm('${confirmDeleteKeywordMsg}')"/>
            </authz:authorize>
        </div>
    </form:form>
    </div>
