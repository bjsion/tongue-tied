<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

    <div class="content">
        <div class="dirBreadcrumbs">
            <p><fmt:message key="listing.for"/></p>
            <span>
        <c:forEach items="${parents}" var="parent">
            <c:set value="${urlString}/${parent}" var="urlString" scope="page"/>
            <c:url value="${urlString}${suffix}" var="parentUrl" />
            /<a href="${parentUrl}" title="<fmt:message key="go.to"><fmt:param value="${parent}"/></fmt:message>">${parent}</a>
        </c:forEach>
            /${baseDir.name}
            </span>
        </div>
        <div class="dirListing">
            <ul>
                <li class="upOneLevel"><a href="${parentUrl}" title="<fmt:message key="go.up.one.level"/>">..</a></li>
            <c:forEach items="${baseDir.files}" var="child">
                <c:choose>
                    <c:when test="${child.directory}">
                        <c:url value="${baseDir.name}/${child.name}${suffix}" var="fileUrl"/>
                        <c:set var="liClass" value="directory" scope="page"/>
                        <fmt:message key="go.to" var="itemTitle" scope="page">
                            <fmt:param value="${child.name}"/>
                        </fmt:message>
                    </c:when>
                    <c:otherwise>
                        <c:url value="${baseDir.name}/${child.name}" var="fileUrl"/>
                        <c:set var="liClass" value="${child.fileType}" scope="page"/>
                        <fmt:message key="download.file" var="itemTitle" scope="page"/>
                    </c:otherwise>
                </c:choose>
                <li class="fileView ${liClass}">
                    <a href="${fileUrl}" title="${itemTitle}">${child.name}</a>
                    <span class="fileAttributes">
                        <c:if test="${child.file}"><fmt:formatNumber type="number" value="${child.size div 1000}" maxFractionDigits="1" /><fmt:message key="kilo.byte.suffix"/>&nbsp</c:if>
                        <fmt:formatDate value="${child.lastModifiedDate}" pattern="EEE dd MMM yyyy hh:mm a"/>
                    </span>
                </li>
            </c:forEach>
            </ul>
        </div>
    </div>
