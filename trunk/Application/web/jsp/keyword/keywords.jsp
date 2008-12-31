
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net/el" %>

<div class="content">
    <div id="left">
        <div class="sidepanel">
            <form id="viewPreferences" method="post" action="<c:url value="/preferences.htm"/>">
                <fieldset>
                    <legend><fmt:message key="preferences"/></legend>
                    <div>
                        <spring:bind path="viewPreferences.maxResults">
                        <label for="${status.expression}"><fmt:message key="max.results"/></label>
                        <select name="<c:out value="${status.expression}"/>" id="<c:out value="${status.expression}"/>" size="1">
                            <c:forEach var="size" begin="20" end="100" step="20">
                            <option <c:if test="${size == status.value}">selected="selected"</c:if> value="${size}"><c:out value="${size}"/></option>
                            </c:forEach>
                        </select>
                        </spring:bind>
                    </div>
                    <div id="languages">
                        <h3><fmt:message key="languages"/></h3>
                        <spring:bind path="viewPreferences.selectedLanguages">
                            <c:forEach items="${languages}" var="language" varStatus="langIndex">
                                <c:forEach items="${status.value}" var="currentLanguage">
                                   <c:if test="${currentLanguage.id == language.id}">
                                       <c:set var="selected" value="true"/>
                                   </c:if>
                               </c:forEach>
                        <div class="checkbox">
                            <input type="checkbox" id="${status.expression}${langIndex.index}" name="${status.expression}" value="${language.id}" <c:if test="${selected}">checked="checked"</c:if>/>
                            <input type="hidden" name="_${status.expression}"/>
                            <label for="${status.expression}${langIndex.index}"><c:out value="${language.name}"/></label>
                        </div>
                                <c:remove var="selected"/>
                            </c:forEach>
                        </spring:bind>
                    </div>
                    <div id="countries">
                        <h3><fmt:message key="countries"/></h3>
                        <spring:bind path="viewPreferences.selectedCountries">
                            <c:forEach items="${countries}" var="country" varStatus="countryIndex">
                                <c:forEach items="${status.value}" var="currentCountry">
                                   <c:if test="${currentCountry.id == country.id}">
                                       <c:set var="selected" value="true"/>
                                   </c:if>
                               </c:forEach>
                        <div class="checkbox">
                            <input type="checkbox" id="${status.expression}${countryIndex.index}" name="${status.expression}" value="${country.id}" <c:if test="${selected}">checked="checked"</c:if>/>
                            <input type="hidden" name="_${status.expression}"/>
                            <label for="${status.expression}${countryIndex.index}"><c:out value="${country.name}"/></label>
                        </div>
                                <c:remove var="selected"/>
                            </c:forEach>
                        </spring:bind>
                    </div>
                    <div id="bundles">
                        <h3><fmt:message key="bundles"/></h3>
                        <spring:bind path="viewPreferences.selectedBundles">
                            <c:forEach items="${bundles}" var="bundle" varStatus="bundleIndex">
                                <c:forEach items="${status.value}" var="currentBundle">
                                   <c:if test="${currentBundle.id == bundle.id}">
                                       <c:set var="selected" value="true"/>
                                   </c:if>
                               </c:forEach>
                        <div class="checkbox">
                            <input type="checkbox" id="${status.expression}${bundleIndex.index}" name="${status.expression}" value="${bundle.id}" <c:if test="${selected}">checked="checked"</c:if>/>
                            <input type="hidden" name="_${status.expression}"/>
                            <label for="${status.expression}${bundleIndex.index}"><c:out value="${bundle.name}"/></label>
                        </div>
                                <c:remove var="selected"/>
                            </c:forEach>
                        </spring:bind>
                    </div>
                </fieldset>
                <div class="submit">
                    <input type="submit" id="submitBtn" name="submitBtn" value="<fmt:message key="submit"/>" class="button"/>
                </div>
            </form>
        </div>
        <div class="sidepanel">
            <c:url value="/keywordSearch.htm" var="searchAction" scope="page"/>
            <form:form id="searchParameters" method="post" action="${searchAction}" commandName="searchParameters">
                <fieldset>
                    <legend><fmt:message key="search"/></legend>
                    <div>
                        <form:label path="keywordKey" cssClass="sidepanel"><fmt:message key="keyword"/></form:label>
                        <form:input path="keywordKey"/>
                    </div>
                    <div>
                        <form:label path="bundle" cssClass="sidepanel"><fmt:message key="bundle"/></form:label>
                        <form:select path="bundle" size="1" cssClass="sidepanel">
                            <form:option value=""><fmt:message key="please.select"/></form:option>
                            <form:options items="${bundles}" itemValue="id" itemLabel="name"/>
                        </form:select>
                    </div>
                    <div>
                        <form:label path="country" cssClass="sidepanel"><fmt:message key="country"/></form:label>
                        <form:select path="country" size="1" cssClass="sidepanel">
                            <form:option value=""><fmt:message key="please.select"/></form:option>
                            <form:options items="${countries}" itemValue="id" itemLabel="name"/>
                        </form:select>
                    </div>
                    <div>
                        <form:label path="language" cssClass="sidepanel"><fmt:message key="language"/></form:label>
                        <form:select path="language" size="1" cssClass="sidepanel">
                            <form:option value=""><fmt:message key="please.select"/></form:option>
                            <form:options items="${languages}" itemValue="id" itemLabel="name"/>
                        </form:select>
                    </div>
                    <div>
                        <form:label path="translationState" cssClass="sidepanel"><fmt:message key="translation.state"/></form:label>
                        <form:select path="translationState" size="1" cssClass="sidepanel">
                            <form:option value=""><fmt:message key="please.select"/></form:option>
                            <c:forEach items="${states}" var="state">
                                <c:if test="${translationState == state}">
                                    <c:set var="selected" value="true"/>
                                </c:if>
                                <form:option value="${state}"><fmt:message key="${state}"/></form:option>
                                <c:remove var="selected"/>
                            </c:forEach>
                        </form:select>
                    </div>
                    <div>
                        <form:label path="translatedText" cssClass="sidepanel"><fmt:message key="translated.text"/></form:label>
                        <form:input path="translatedText"/>
                    </div>
                    <div class="checkbox">
                        <form:checkbox path="ignoreCase" id="ignoreCase"/>
                        <form:label path="ignoreCase"><fmt:message key="ignore.case"/></form:label>
                    </div>
                </fieldset>
                <div class="submit">
                    <input type="submit" id="searchBtn" name="searchBtn" value="<fmt:message key="search"/>" class="button"/>
                    <input type="reset" id="reset" name="reset" value="<fmt:message key="reset"/>" class="button"/>
                </div>
            </form:form>
        </div>
    </div>

    <div class="contentPanel">
        <a href="<c:url value="keywords.htm"><c:param name="showAll" value="true"/></c:url>" title="<fmt:message key="get.all.keywords"/>"><fmt:message key="all.keywords"/></a>
        <display:table name="translations" id="translation" sort="page" requestURI="">
            <display:column titleKey="action" group="1" class="actions">
                <c:url value="deleteKeyword.htm" var="deleteKeywordUrl" scope="page"><c:param name="keywordId" value="${translation.keyword.id}"/></c:url>
                <fmt:message key="confirm.keyword.delete" var="confirmDeleteKeywordMsg" scope="page" ><fmt:param value="${translation.keyword.keyword}"/></fmt:message>
                <a href="${deleteKeywordUrl}" onclick="return confirm('${fn:escapeXml(confirmDeleteKeywordMsg)}')">
                    <img src="<c:url value="images/delete.png"/>" alt="<fmt:message key="delete"/>" title="<fmt:message key="delete"/>" class="imgLink"/>
                </a>
            </display:column>
            <display:column titleKey="keyword" group="2" class="keyword" sortable="true">
                <a href="<c:url value="keyword.htm"><c:param name="keywordId" value="${translation.keyword.id}"/></c:url>">
                    <c:out value="${translation.keyword.keyword}"/>
                </a>
            </display:column>
            <display:column property="keyword.context" maxLength="60" titleKey="context" group="3" class="context"/>
            <display:column property="bundle.name" titleKey="bundle" class="bundle"/>
            <display:column property="language.name" titleKey="language" class="language"/>
            <display:column titleKey="country" class="country">
                <c:if test="${not empty translation.country.code and translation.country.code != \"DEFAULT\"}">
                <img src="<c:url value="/images/flags/${fn:toLowerCase(translation.country.code)}.png"/>" alt="" title="${translation.country.name}"/>
                </c:if>
                <c:out value="${translation.country.name}"/>
            </display:column>
            <display:column titleKey="translation">
                <c:out value="${translation.value}"/>
            </display:column>
            <display:column titleKey="state" sortable="true">
                <fmt:message key="${translation.state}"/>
            </display:column>
            <display:setProperty name="basic.empty.showtable" value="true" />
        </display:table>
    </div>
</div>
