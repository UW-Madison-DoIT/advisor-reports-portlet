<%@ include file="/WEB-INF/jsp/include.jsp"%>

<portlet:renderURL var="refreshUrl" />
<div>
    <div>
      <span><spring:message code="noEmplId" /> </span>
      <a href="${refreshUrl}"><spring:message code="refresh" /></a>
    </div>
</div>
