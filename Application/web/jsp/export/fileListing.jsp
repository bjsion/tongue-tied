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
            /<a href="${parentUrl}">${parent}</a>
        </c:forEach>
            /${baseDir.name}
            </span>
        </div>
        <div class="dirListing">
            <ul>
                <li><a href="${parentUrl}"><fmt:message key="up.one.level"/></a></li>
            <c:forEach items="${baseDir.files}" var="child">
                <c:choose>
                    <c:when test="${child.directory}">
                        <c:url value="${baseDir.name}/${child.name}${suffix}" var="fileUrl"/>
                        <c:set var="liClass" value="directory" scope="page"/>
                    </c:when>
                    <c:otherwise>
                        <c:url value="${baseDir.name}/${child.name}" var="fileUrl"/>
                        <c:set var="liClass" value="${child.fileType}" scope="page"/>
                    </c:otherwise>
                </c:choose>
                <li class="fileView ${liClass}"><a href="${fileUrl}">${child.name}</a> length = ${child.size}</li>
            </c:forEach>
            </ul>
        </div>
    </div>
