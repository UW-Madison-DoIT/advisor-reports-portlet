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

package edu.wisc.portlet.advisor.web.advrpt;

import java.util.Set;

import javax.annotation.Resource;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.PortletResourceProxyResponse;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import edu.wisc.cypress.dao.advrpt.AdvisorReportDao;
import edu.wisc.cypress.dm.advrpt.AdvisorReports;
import edu.wisc.portlet.advisor.security.PviUtils;

/**
 * @author Eric Dalquist
 * @version $Revision: 1.1 $
 */
@Controller
@RequestMapping("VIEW")
public class AdvisorReportsController {
    private AdvisorReportDao advisorReportDao;
    private Set<String> ignoredProxyHeaders;
    
    @Resource(name="ignoredProxyHeaders")
    public void setIgnoredProxyHeaders(Set<String> ignoredProxyHeaders) {
        this.ignoredProxyHeaders = ignoredProxyHeaders;
    }

    @Autowired
    public void setAdvisorReportDao(AdvisorReportDao advisorReportDao) {
        this.advisorReportDao = advisorReportDao;
    }
     
    @RenderMapping
    public String viewAdvisorReports(ModelMap model, PortletRequest request) {
        return "advisorReports";
    }

    @ResourceMapping("advisorReports")
    public String getAdvisorReports(ModelMap modelMap) {
        final String pvi = PviUtils.getPvi();
        
        final AdvisorReports advisorReports = this.advisorReportDao.getAdvisorReports(pvi);

        modelMap.addAttribute("report", advisorReports.getAdvisorReports());
        
        return "reportAttrJsonView";
    }

    @ResourceMapping("advisor_report.pdf")
    public void getSabbaticalReport(
            @RequestParam("docId") String docId, 
            ResourceResponse response) {

        final String pvi = PviUtils.getPvi();
        this.advisorReportDao.getAdvisorReport(pvi, docId, new PortletResourceProxyResponse(response, ignoredProxyHeaders));
    }
}
