<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

    <div class="content">
        <display:table name="auditLog" id="record" sort="page" requestURI="">
            <display:column property="username" titleKey="changed.by" sortable="true"/>
            <display:column property="oldValue" titleKey="old.value" sortable="false"/>
            <display:column property="newValue" titleKey="new.value" sortable="false"/>
            <display:column property="created" titleKey="created" sortable="true"/>
            <display:column property="message" titleKey="action" sortable="false"/>
        </display:table>
    </div>