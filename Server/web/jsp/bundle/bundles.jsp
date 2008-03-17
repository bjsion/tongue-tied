<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

    <div class="content">
        <display:table name="bundles" id="bundle" sort="page" requestURI="">
            <display:column sortable="true" titleKey="bundleName" url="/bundle.htm" paramId="bundleId" paramProperty="id">
                <c:out value="${bundle.name}"/>
            </display:column>
            <display:column property="resourceName" titleKey="bundleResourceName" sortable="true"/>
            <display:column property="resourceDestination" titleKey="bundleResourceDestination"/>
            <display:column titleKey="action"/>
        </display:table>
    </div>
