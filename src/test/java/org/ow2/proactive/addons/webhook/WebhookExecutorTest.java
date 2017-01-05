/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
package org.ow2.proactive.addons.webhook;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.ow2.proactive.addons.webhook.exception.UnsuccessfulRequestException;
import org.ow2.proactive.addons.webhook.model.RestResponse;
import org.ow2.proactive.addons.webhook.service.JsonRestApacheRequestService;


@RunWith(MockitoJUnitRunner.class)
public class WebhookExecutorTest {

    @SuppressWarnings("CanBeFinal")
    @Mock
    JsonRestApacheRequestService mockJsonRestApacheRequestService;

    @Test(expected = UnsuccessfulRequestException.class)
    public void testThatErrorResponseThrowsExceptionBadGateWay() throws Throwable {
        when(mockJsonRestApacheRequestService.doRequest(anyString(),
                                                        anyString(),
                                                        anyString(),
                                                        anyString())).thenReturn(new RestResponse(HttpStatus.SC_BAD_GATEWAY,
                                                                                                  "BAD"));

        new WebhookExecutor(mockJsonRestApacheRequestService).execute("", "", "", "");
    }

    @Test(expected = UnsuccessfulRequestException.class)
    public void testThatErrorResponseThrowsExceptionUNAUTHORIZED() throws Throwable {
        when(mockJsonRestApacheRequestService.doRequest(anyString(),
                                                        anyString(),
                                                        anyString(),
                                                        anyString())).thenReturn(new RestResponse(HttpStatus.SC_UNAUTHORIZED,
                                                                                                  "UNAUTHORIZED"));

        new WebhookExecutor(mockJsonRestApacheRequestService).execute("POST", "", "", "");
    }

    @Test(expected = UnsuccessfulRequestException.class)
    public void testThatErrorResponseThrowsExceptionFORBIDDEN() throws Throwable {
        when(mockJsonRestApacheRequestService.doRequest(anyString(),
                                                        anyString(),
                                                        anyString(),
                                                        anyString())).thenReturn(new RestResponse(HttpStatus.SC_FORBIDDEN,
                                                                                                  "FORBIDDEN"));

        new WebhookExecutor(mockJsonRestApacheRequestService).execute("", "", "", "");
    }

    @Test(expected = UnsuccessfulRequestException.class)
    public void testThatErrorResponseThrowsExceptionNOT_FOUND() throws Throwable {
        when(mockJsonRestApacheRequestService.doRequest(anyString(),
                                                        anyString(),
                                                        anyString(),
                                                        anyString())).thenReturn(new RestResponse(HttpStatus.SC_NOT_FOUND,
                                                                                                  "NOT_FOUND"));

        new WebhookExecutor(mockJsonRestApacheRequestService).execute("", "", "", "");
    }

    @Test(expected = UnsuccessfulRequestException.class)
    public void testThatErrorResponseThrowsExceptionSERVICE_UNAVAILABLE() throws Throwable {
        when(mockJsonRestApacheRequestService.doRequest(anyString(),
                                                        anyString(),
                                                        anyString(),
                                                        anyString())).thenReturn(new RestResponse(HttpStatus.SC_SERVICE_UNAVAILABLE,
                                                                                                  "SERVICE_UNAVAILABLE"));

        new WebhookExecutor(mockJsonRestApacheRequestService).execute("", "", "", "");
    }

    @Test
    public void testThatErrorResponseSucceedsACCEPTED() throws Throwable {
        when(mockJsonRestApacheRequestService.doRequest(anyString(),
                                                        anyString(),
                                                        anyString(),
                                                        anyString())).thenReturn(new RestResponse(HttpStatus.SC_ACCEPTED,
                                                                                                  "ACCEPTED"));

        new WebhookExecutor(mockJsonRestApacheRequestService).execute("", "", "", "");
    }

    @Test
    public void testThatErrorResponseSucceedsCREATED() throws Throwable {
        when(mockJsonRestApacheRequestService.doRequest(anyString(),
                                                        anyString(),
                                                        anyString(),
                                                        anyString())).thenReturn(new RestResponse(HttpStatus.SC_CREATED,
                                                                                                  "CREATED"));

        new WebhookExecutor(mockJsonRestApacheRequestService).execute("", "", "", "");
    }

    @Test
    public void testThatErrorResponseSucceedsOK() throws Throwable {
        when(mockJsonRestApacheRequestService.doRequest(anyString(),
                                                        anyString(),
                                                        anyString(),
                                                        anyString())).thenReturn(new RestResponse(HttpStatus.SC_OK,
                                                                                                  "OK"));

        new WebhookExecutor(mockJsonRestApacheRequestService).execute("", "", "", "");
    }

    @Test
    public void testThatErrorResponseSucceedsNON_AUTHORITATIVE_INFORMATION() throws Throwable {
        when(mockJsonRestApacheRequestService.doRequest(anyString(),
                                                        anyString(),
                                                        anyString(),
                                                        anyString())).thenReturn(new RestResponse(HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION,
                                                                                                  "NON_AUTHORITATIVE_INFORMATION"));

        new WebhookExecutor(mockJsonRestApacheRequestService).execute("", "", "", "");
    }

}
