<%@ include file="/WEB-INF/jsp/include.jsp"%>

<portlet:renderURL var="refreshUrl" />
<div>
    <div>
      <spring:message code="genericError" arguments="${refreshUrl}" htmlEscape="false" javaScriptEscape="false" />
    </div>
</div>
