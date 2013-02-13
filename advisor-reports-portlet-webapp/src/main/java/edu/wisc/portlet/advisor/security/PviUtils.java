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

package edu.wisc.portlet.advisor.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * PVI access utilities
 * 
 * @author Eric Dalquist
 * @version $Revision: 1.3 $
 */
public final class PviUtils {
    /**
     * @return The current user's PVI
     */
    public static String getPvi() {
        final SecurityContext context = SecurityContextHolder.getContext();
        final Authentication authentication = context.getAuthentication();
        
        final PrimaryAttributePortletAuthenticationDetails authenticationDetails = (PrimaryAttributePortletAuthenticationDetails)authentication.getDetails();
        return authenticationDetails.getPrimaryAttribute();
    }
}
