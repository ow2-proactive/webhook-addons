package org.ow2.proactive.addons.webhook.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JsonRestRequestServiceTest {
    @Mock
    JsonStringToRestHttpHeaders mockJsonStringToRestHttpHeaders;

    JsonRestRequestService jsonRestRequestService;

    @Before
    public void setUp() {
        when(mockJsonStringToRestHttpHeaders.convert(anyString()))
                .thenReturn(new HttpHeaders());
        this.jsonRestRequestService =
                spy(new JsonRestRequestService(mockJsonStringToRestHttpHeaders));

        // Don't actually do the rest call - mock the protected method which executes the rest call
        doReturn(new ResponseEntity(HttpStatus.ACCEPTED)).
                when(this.jsonRestRequestService).executeRestTemplateExchangeWaitStringResponse(
                any(String.class),
                any(HttpMethod.class),
                any(RestTemplate.class),
                any(HttpEntity.class));
    }

    @Test
    public void testValidRestMethods() {
        String[] restMethods = {
                "GET",
                "POST",
                "HEAD",
                "PUT",
                "PATCH",
                "DELETE",
                "OPTIONS",
                "TRACE"};

        for (String method : restMethods) {
            this.jsonRestRequestService.doRequest(method, "", "", "");
        }
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testInvalidRestMethodThrowsException() {
        this.jsonRestRequestService.doRequest("Invalid", "", "", "");
    }

    @Test
    public void testThatNoInstanceIsNullWhenExecutingRestCalls() {
        // Reset spy
        this.jsonRestRequestService =
                spy(new JsonRestRequestService(mockJsonStringToRestHttpHeaders));
        doReturn(new ResponseEntity(HttpStatus.ACCEPTED)).
                when(this.jsonRestRequestService).executeRestTemplateExchangeWaitStringResponse(
                notNull(String.class),
                notNull(HttpMethod.class),
                notNull(RestTemplate.class),
                notNull(HttpEntity.class));

        this.jsonRestRequestService.doRequest("GET", "", "", "");
    }

    @Test(expected = NullPointerException.class)
    public void testThatNullpointerExceptionIsThrownIfMethodIsNull() {
        this.jsonRestRequestService.doRequest(null, "", "", "");
    }

    @Test
    public void testThatRestTemplateExchangeIsExecuted() {
        RestTemplate spyRestTemplate = spy(new RestTemplate());
        doReturn(ResponseEntity.ok("Ok"))
                .when(spyRestTemplate)
                .exchange(
                        notNull(String.class),
                        notNull(HttpMethod.class),
                        notNull(RequestEntity.class),
                        notNull(Class.class));

        this.jsonRestRequestService = new JsonRestRequestService(this.mockJsonStringToRestHttpHeaders);
        this.jsonRestRequestService.executeRestTemplateExchangeWaitStringResponse(
                "",
                HttpMethod.GET,
                spyRestTemplate,
                (HttpEntity<String>) HttpEntity.EMPTY);
    }

}