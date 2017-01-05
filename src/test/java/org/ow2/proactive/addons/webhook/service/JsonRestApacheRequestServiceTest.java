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
package org.ow2.proactive.addons.webhook.service;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.ow2.proactive.addons.webhook.model.RestResponse;


@RunWith(MockitoJUnitRunner.class)
public class JsonRestApacheRequestServiceTest {
    @SuppressWarnings("CanBeFinal")
    @Mock
    private JsonStringToHeaderMap mockJsonStringToHeaderMap;

    @SuppressWarnings("CanBeFinal")
    @Mock
    private ApacheHttpClientRequestGetter mockApacheHttpClientRequestGetter;

    private JsonRestApacheRequestService jsonRestApacheRequestService;

    @Before
    public void setUp() throws IOException {
        when(mockJsonStringToHeaderMap.convert(anyString())).thenReturn(new HashMap<>());
        this.jsonRestApacheRequestService = spy(new JsonRestApacheRequestService(mockJsonStringToHeaderMap,
                                                                                 mockApacheHttpClientRequestGetter));

        // Don't actually do the rest call - mock the protected method which executes the rest call
        //noinspection unchecked
        doReturn(new RestResponse(HttpStatus.SC_ACCEPTED, "Okay")).when(this.jsonRestApacheRequestService)
                                                                  .executeRequest(any(Request.class));

        // Don't test the string to request mapping
        doReturn(Request.Get("")).when(mockApacheHttpClientRequestGetter).getHttpRequestByString(any(String.class),
                                                                                                 any(String.class));
    }

    @Test
    public void testThatNoInstanceIsNullWhenExecutingRestCalls() throws IOException {
        // Reset spy
        this.jsonRestApacheRequestService = spy(new JsonRestApacheRequestService(mockJsonStringToHeaderMap,
                                                                                 mockApacheHttpClientRequestGetter));

        //noinspection unchecked
        doReturn(new RestResponse(HttpStatus.SC_ACCEPTED, "Okay")).when(this.jsonRestApacheRequestService)
                                                                  .executeRequest(any(Request.class));

        this.jsonRestApacheRequestService.doRequest("GET", "", "", "");
    }

    @Test
    public void testThatRequestExecuteMethodIsExecuted() throws IOException {
        TestThatExecuteMethodIsExecuted testThatExecuteMethodIsExecuted = spy(new TestThatExecuteMethodIsExecuted());

        RestResponse response = testThatExecuteMethodIsExecuted.doRequest("", "", "", "");

        verify(testThatExecuteMethodIsExecuted).executeRequest(any(Request.class));

        assertThat(response.getResponseCode(), is(200));
        assertThat(response.getResponse(), is("Ok"));
    }

    /*************** TEST CLASSES *************************/

    private class TestThatExecuteMethodIsExecuted extends JsonRestApacheRequestService {

        TestThatExecuteMethodIsExecuted() {
            super(mockJsonStringToHeaderMap, mockApacheHttpClientRequestGetter);
        }

        @Override
        protected RestResponse executeRequest(final Request request) throws IOException {
            return new RestResponse(200, "Ok");
        }

    }
}
