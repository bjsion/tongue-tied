<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

    <div class="content">
        <display:table name="languages" id="language" sort="page" requestURI="">
            <display:column sortable="true" titleKey="language.name" url="/language.htm" paramId="languageId" paramProperty="id">
                <c:out value="${language.name}"/>
            </display:column>
            <display:column property="code" titleKey="language.code" sortable="true"/>
            <display:column titleKey="action"/>
        </display:table>
    </div>
