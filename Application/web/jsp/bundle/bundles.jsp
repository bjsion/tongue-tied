<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

    <div class="content">
        <display:table name="bundles" id="bundle" sort="page" requestURI="">
            <display:column sortable="true" titleKey="bundle.name" url="/bundle.htm" paramId="bundleId" paramProperty="id">
                <c:out value="${bundle.name}"/>
                <c:if test="${bundle.default}">
                <img src="<c:url value="/images/asterisk_yellow.png"/>" alt="${bundle.default}" title="<fmt:message key="bundle.default"/>" class="imgLink"/>
                </c:if>
            </display:column>
            <display:column property="resourceName" titleKey="bundle.resource.name" sortable="true"/>
            <display:column titleKey="action"/>
        </display:table>
    </div>
