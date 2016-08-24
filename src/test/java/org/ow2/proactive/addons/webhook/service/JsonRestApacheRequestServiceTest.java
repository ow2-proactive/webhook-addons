package org.ow2.proactive.addons.webhook.service;

import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.ow2.proactive.addons.webhook.model.RestResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        when(mockJsonStringToHeaderMap.convert(anyString()))
                .thenReturn(new HashMap<>());
        this.jsonRestApacheRequestService =
                spy(new JsonRestApacheRequestService(mockJsonStringToHeaderMap, mockApacheHttpClientRequestGetter));

        // Don't actually do the rest call - mock the protected method which executes the rest call
        //noinspection unchecked
        doReturn(new RestResponse(HttpStatus.SC_ACCEPTED, "Okay")).
                when(this.jsonRestApacheRequestService)
                .executeRequest(any(Request.class));

        // Don't test the string to request mapping
        doReturn(Request.Get("")).when(mockApacheHttpClientRequestGetter)
                .getHttpRequestByString(any(String.class), any(String.class));
    }


    @Test
    public void testThatNoInstanceIsNullWhenExecutingRestCalls() throws IOException {
        // Reset spy
        this.jsonRestApacheRequestService =
                spy(new JsonRestApacheRequestService(
                        mockJsonStringToHeaderMap,
                        mockApacheHttpClientRequestGetter));

        //noinspection unchecked
        doReturn(new RestResponse(HttpStatus.SC_ACCEPTED, "Okay")).
                when(this.jsonRestApacheRequestService)
                .executeRequest(any(Request.class));

        this.jsonRestApacheRequestService.doRequest("GET", "", "", "");
    }


    @Test
    public void testThatRequestExecuteMethodIsExecuted() throws IOException {
        TestThatExecuteMethodIsExecuted testThatExecuteMethodIsExecuted =
                spy(new TestThatExecuteMethodIsExecuted());

        RestResponse response = testThatExecuteMethodIsExecuted
                .doRequest("", "", "", "");

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