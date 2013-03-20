/**
 * Copyright 2012, Board of Regents of the University of
 * Wisconsin System. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Board of Regents of the University of Wisconsin
 * System licenses this file to you under the Apache License,
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.wisc.cypress.dao.MockProxyResponse;
import edu.wisc.cypress.dm.advrpt.AdvisorReport;
import edu.wisc.cypress.dm.advrpt.AdvisorReports;

/**
 * 
 * @version $Id: AdvisorReportDaoIT.java,v 1.3 2011/12/16 21:31:36 dalquist Exp $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/integrationCypressTestContext.xml")
public class AdvisorReportDaoIT {
    @Autowired
    private RestAdvisorReportDao client;
    
    @Test
    public void testGetStatement() throws Exception {
        MockProxyResponse response = new MockProxyResponse();
        
        client.getAdvisorReport("UW162R478", "8589695", response);
        
        verifyStatement(response);
    }

    protected void verifyStatement(MockProxyResponse response) {
        final byte[] content = response.getContentAsByteArray();
        assertNotNull(content);
        assertEquals(138096, content.length);
    }
    
    @Test
    public void testGetStatements() throws Exception {
        final AdvisorReports advisorReports = client.getAdvisorReports("UW162R478");
        assertNotNull(advisorReports);
        final List<AdvisorReport> reports = advisorReports.getAdvisorReports();
        assertNotNull(reports);
        assertEquals(1, reports.size());
    }
    
    @Test
    public void testNoStatements() throws Exception {
        final AdvisorReports advisorReports = client.getAdvisorReports("00000000");
        assertNotNull(advisorReports);
        final List<AdvisorReport> reports = advisorReports.getAdvisorReports();
        assertNotNull(reports);
        assertEquals(0, reports.size());
    }
    
    @Test
    public void testBadEmplId() throws Exception {
        final AdvisorReports advisorReports = client.getAdvisorReports("");
        assertNotNull(advisorReports);
        final List<AdvisorReport> reports = advisorReports.getAdvisorReports();
        assertNotNull(reports);
        assertEquals(0, reports.size());
    }
}
