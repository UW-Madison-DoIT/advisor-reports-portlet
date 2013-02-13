<%@ page trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>

<div id="${n}advisor-reports" class="fl-widget portlet advisor-reports">
  <div class="dl-banner-links">
    <div class="dl-help-link">
      <a href="${helpUrl}" target="_blank">Help</a>
    </div>
  </div>
  <hrs:notification/>
    <div id="${n}advisor-report-statements" class="advisor-report-statements">
      <div class="advisor-report-header">
      </div>
      <div class="fl-pager">
        <hrs:pagerNavBar position="top" showSummary="${true}"/>
        <div class="fl-container-flex dl-pager-table-data fl-pager-data">
          <table class="dl-table">
            <thead>
              <tr rsf:id="header:">
                <th class="flc-pager-sort-header dl-col-50p" rsf:id="name"><a href="javascript:;">Report</a></th>
              </tr>
            </thead>
            <tbody>
                <tr rsf:id="row:" class="dl-clickable">
                  <td class="dl-data-text"><a href="#" target="_blank" rsf:id="name"></a></td>      
                </tr>
            </tbody>
          </table>
        </div>
        <hrs:pagerNavBar position="bottom" />
      </div>
  </div>
  
  <div id="${n}no-statements">
    No reports available
  </div>
</div>

<portlet:resourceURL var="advisorReportsUrl" id="advisorReports" escapeXml="false"/>

<portlet:resourceURL var="advisorReportPdfUrl" id="advisor_report.pdf" escapeXml="false">
    <portlet:param name="docId" value="TMPLT_*.docId_TMPLT"/>
</portlet:resourceURL>

<script type="text/javascript" language="javascript">
<rs:compressJs>
(function($, fluid, dl) {
    $(function() {
    	
    	var noStatementsDiv = $("#${n}no-statements");
        noStatementsDiv.hide();
        
        //Setup the pager with no data right away, this helps avoid page flicker when the data comes back from the ajax request
        var advisorReportUrl = dl.util.templateUrl("${advisorReportPdfUrl}");
        dl.pager.init("#${n}advisor-report-statements", {
          model: {
              sortKey: "docId",
              sortDir: -1
          },
          summary: {
              type: "fluid.pager.summary", 
              options: {
                message: "%first-%last of %total reports"
              }
          },
          columnDefs: [ 
             dl.pager.linkColDef("name", advisorReportUrl, {sortable: true})
          ],
          dataList: {
              url: "${advisorReportsUrl}",
              dataKey: "report",
              dataLoadCallback: function (data) {
                  if (data == undefined || data.length == 0) {
                      //Hide the reports block
                      $("#${n}advisor-report-statements").hide();
                  
                      //Show the no-statements block
                      noStatementsDiv.show();
                  }
                  else {
                      //Hide no statements block show the reports block
                      noStatementsDiv.hide();
                      
                      $("#${n}advisor-report-statements").show();
                  }
              },
              dataLoadErrorMsg: "<spring:message code="genericError" arguments="javascript:;" htmlEscape="false" javaScriptEscape="true" />",
              pagerContainerClass: "advisor-report-statements"
          }
        });
        
        dl.pager.show("#${n}advisor-report-statements");
        
        dl.util.clickableContainer("#${n}advisor-reports");
    });    
})(dl_v1.jQuery, dl_v1.fluid, dl_v1);
</rs:compressJs>
</script>