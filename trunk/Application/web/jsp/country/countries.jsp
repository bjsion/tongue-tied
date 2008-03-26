<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>


    <div class="content">
        <display:table name="countries" id="country" sort="page" requestURI="">
            <display:column>
                <c:if test="${country.code != \"DEFAULT\"}">
                <img src="<c:url value="/images/flags/${fn:toLowerCase(country.code)}.png"/>" alt="${country.name}" title="${country.name}"/>
                </c:if>
            </display:column>
            <display:column sortable="true" titleKey="countryName" url="/country.htm" paramId="countryId" paramProperty="id">
                <c:out value="${country.name}"/>
            </display:column>
            <display:column property="code" titleKey="countryCode" sortable="true"/>
            <display:column titleKey="action"/>
        </display:table>
    </div>
