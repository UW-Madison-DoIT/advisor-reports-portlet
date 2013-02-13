/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package edu.wisc.cypress.dao.advrpt;



import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.ExtendedRestOperations;
import org.springframework.web.client.ExtendedRestOperations.ProxyResponse;

import com.googlecode.ehcache.annotations.Cacheable;

import edu.wisc.cypress.dm.advrpt.AdvisorReport;
import edu.wisc.cypress.dm.advrpt.AdvisorReports;
import edu.wisc.cypress.xdm.advrpt.XmlAdvisorReport;
import edu.wisc.cypress.xdm.advrpt.XmlAdvisorReports;

/**
 * @author Eric Dalquist
 * @version $Revision: 1.1 $
 */
@Repository("restAdvisorReportDao")
public class RestAdvisorReportDao implements AdvisorReportDao {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    private ExtendedRestOperations restOperations;
    private String statementsUrl;
    private String statementUrl;
    
    @Autowired
    public void setRestOperations(ExtendedRestOperations restOperations) {
        this.restOperations = restOperations;
    }

    public void setStatementsUrl(String statementsUrl) {
        this.statementsUrl = statementsUrl;
    }
    public void setStatementUrl(String statementUrl) {
        this.statementUrl = statementUrl;
    }

    @Cacheable(cacheName="advisorReports", exceptionCacheName="cypressUnknownExceptionCache")
    @Override
    public AdvisorReports getAdvisorReports(String pvi) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("PVI", pvi);
        
        final XmlAdvisorReports xmlAdvisorReports = this.restOperations.getForObject(this.statementsUrl, XmlAdvisorReports.class, httpHeaders, pvi);
        
        return this.mapAdvisorReports(xmlAdvisorReports);
    }
    
    @Override
    public void getAdvisorReport(String pvi, String docId, ProxyResponse proxyResponse) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("PVI", pvi);
        
        this.restOperations.proxyRequest(proxyResponse, this.statementUrl, HttpMethod.GET, httpHeaders, docId);
    }
    
    protected AdvisorReports mapAdvisorReports(XmlAdvisorReports xmlAdvisorReports) {
        final AdvisorReports advisorStatements = new AdvisorReports();

        final List<AdvisorReport> advisorReports = advisorStatements.getAdvisorReports();
        for (final XmlAdvisorReport input : xmlAdvisorReports.getAdvisorReports()) {
            final AdvisorReport advisorReport = new AdvisorReport();
            
            advisorReport.setDocId(input.getDocId());
            advisorReport.setFullTitle(input.getFullTitle());
            advisorReport.setName(input.getName());
            advisorReport.setDocType(input.getDocType());
            
            advisorReports.add(advisorReport);
        }
        
        return advisorStatements;
    }
}
