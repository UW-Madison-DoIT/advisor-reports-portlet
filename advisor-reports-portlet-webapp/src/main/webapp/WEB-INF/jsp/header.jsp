<%@ page trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/jsp/include.jsp"%>

<portlet:defineObjects/>
<spring:htmlEscape defaultHtmlEscape="true" />

<c:set var="n" scope="request"><portlet:namespace/></c:set>
<c:set var="prefs" scope="request" value="${renderRequest.preferences.map}" />

<rs:aggregatedResources path="/resources.xml"/>
