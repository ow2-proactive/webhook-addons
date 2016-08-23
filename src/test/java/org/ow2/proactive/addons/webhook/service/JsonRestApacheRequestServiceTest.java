package org.ow2.proactive.addons.webhook.service;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.ow2.proactive.addons.webhook.model.RestResponse;

import java.io.IOException;
import java.util.HashMap;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JsonRestApacheRequestServiceTest {
    @SuppressWarnings("CanBeFinal")
    @Mock
    private JsonStringToHeaderMap mockJsonStringToHeaderMap;
    @Mock
    private ApacheHttpClientRequestGetter apacheHttpClientRequestGetter;

    private JsonRestApacheRequestService jsonRestApacheRequestService;

    @Before
    public void setUp() throws IOException {
        when(mockJsonStringToHeaderMap.convert(anyString()))
                .thenReturn(new HashMap<String, String>());
        this.jsonRestApacheRequestService =
                spy(new JsonRestApacheRequestService(mockJsonStringToHeaderMap, apacheHttpClientRequestGetter));

        // Don't actually do the rest call - mock the protected method which executes the rest call
        //noinspection unchecked
        doReturn(new RestResponse(HttpStatus.SC_ACCEPTED, "Okay")).
                when(this.jsonRestApacheRequestService)
                .executeRequest(any(Request.class));

        // Don't test the string to request mapping
        doReturn(Request.Get("")).when(apacheHttpClientRequestGetter)
                .getHttpRequestByString(any(String.class), any(String.class));
    }


    @Test
    public void testThatNoInstanceIsNullWhenExecutingRestCalls() throws IOException {
        // Reset spy
        this.jsonRestApacheRequestService =
                spy(new JsonRestApacheRequestService(
                        mockJsonStringToHeaderMap,
                        apacheHttpClientRequestGetter));

        //noinspection unchecked
        doReturn(new RestResponse(HttpStatus.SC_ACCEPTED, "Okay")).
                when(this.jsonRestApacheRequestService)
                .executeRequest(any(Request.class));

        this.jsonRestApacheRequestService.doRequest("GET", "", "", "");
    }


    @Test
    public void testThatRequestExecuteMethodIsExecuted() throws IOException {
        Request spyRequest = spy(Request.Get(""));

        Response mockResponse = mock(Response.class);
        HttpResponse mockHttpResponse = mock(HttpResponse.class);
        StatusLine mockStatusLine = mock(StatusLine.class);
        Content mockContent = mock(Content.class);
        when(mockStatusLine.getStatusCode()).thenReturn(200);
        when(mockHttpResponse.getStatusLine()).thenReturn(mockStatusLine);
        when(mockResponse.returnResponse()).thenReturn(mockHttpResponse);

        when(mockContent.asString()).thenReturn("Ok");
        when(mockResponse.returnContent()).thenReturn(mockContent);
        doReturn(mockResponse)
                .when(spyRequest)
                .execute();

        this.jsonRestApacheRequestService =
                new JsonRestApacheRequestService(
                        this.mockJsonStringToHeaderMap,
                        this.apacheHttpClientRequestGetter);

        this.jsonRestApacheRequestService.executeRequest(spyRequest);

        verify(spyRequest).execute();
    }


}