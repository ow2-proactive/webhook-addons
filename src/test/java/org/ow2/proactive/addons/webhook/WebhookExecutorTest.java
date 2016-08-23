package org.ow2.proactive.addons.webhook;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.ow2.proactive.addons.webhook.exception.UnsuccessfulRequestException;
import org.ow2.proactive.addons.webhook.model.RestResponse;
import org.ow2.proactive.addons.webhook.service.JsonRestApacheRequestService;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WebhookExecutorTest {

    @SuppressWarnings("CanBeFinal")
    @Mock
    JsonRestApacheRequestService mockJsonRestApacheRequestService;

    @Test(expected = UnsuccessfulRequestException.class)
    public void testThatErrorResponseThrowsExceptionBadGateWay() throws Throwable {
        when(mockJsonRestApacheRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new RestResponse(HttpStatus.SC_BAD_GATEWAY, "BAD"));

        new WebhookExecutor(mockJsonRestApacheRequestService).execute("", "", "", "");
    }

    @Test(expected = UnsuccessfulRequestException.class)
    public void testThatErrorResponseThrowsExceptionUNAUTHORIZED() throws Throwable {
        when(mockJsonRestApacheRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new RestResponse(HttpStatus.SC_UNAUTHORIZED, "UNAUTHORIZED"));

        new WebhookExecutor(mockJsonRestApacheRequestService).execute("POST", "", "", "");
    }

    @Test(expected = UnsuccessfulRequestException.class)
    public void testThatErrorResponseThrowsExceptionFORBIDDEN() throws Throwable {
        when(mockJsonRestApacheRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new RestResponse(HttpStatus.SC_FORBIDDEN, "FORBIDDEN"));

        new WebhookExecutor(mockJsonRestApacheRequestService).execute("", "", "", "");
    }

    @Test(expected = UnsuccessfulRequestException.class)
    public void testThatErrorResponseThrowsExceptionNOT_FOUND() throws Throwable {
        when(mockJsonRestApacheRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new RestResponse(HttpStatus.SC_NOT_FOUND, "NOT_FOUND"));

        new WebhookExecutor(mockJsonRestApacheRequestService).execute("", "", "", "");
    }

    @Test(expected = UnsuccessfulRequestException.class)
    public void testThatErrorResponseThrowsExceptionSERVICE_UNAVAILABLE() throws Throwable {
        when(mockJsonRestApacheRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new RestResponse(HttpStatus.SC_SERVICE_UNAVAILABLE, "SERVICE_UNAVAILABLE"));

        new WebhookExecutor(mockJsonRestApacheRequestService).execute("", "", "", "");
    }

    @Test
    public void testThatErrorResponseSucceedsACCEPTED() throws Throwable {
        when(mockJsonRestApacheRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new RestResponse(HttpStatus.SC_ACCEPTED, "ACCEPTED"));

        new WebhookExecutor(mockJsonRestApacheRequestService).execute("", "", "", "");
    }

    @Test
    public void testThatErrorResponseSucceedsCREATED() throws Throwable {
        when(mockJsonRestApacheRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new RestResponse(HttpStatus.SC_CREATED, "CREATED"));

        new WebhookExecutor(mockJsonRestApacheRequestService).execute("", "", "", "");
    }

    @Test
    public void testThatErrorResponseSucceedsOK() throws Throwable {
        when(mockJsonRestApacheRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new RestResponse(HttpStatus.SC_OK, "OK"));

        new WebhookExecutor(mockJsonRestApacheRequestService).execute("", "", "", "");
    }

    @Test
    public void testThatErrorResponseSucceedsNON_AUTHORITATIVE_INFORMATION() throws Throwable {
        when(mockJsonRestApacheRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new RestResponse(HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION, "NON_AUTHORITATIVE_INFORMATION"));

        new WebhookExecutor(mockJsonRestApacheRequestService).execute("", "", "", "");
    }

}