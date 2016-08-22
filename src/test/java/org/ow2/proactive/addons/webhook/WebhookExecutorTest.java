package org.ow2.proactive.addons.webhook;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.ow2.proactive.addons.webhook.exception.UnsuccessfulRequestException;
import org.ow2.proactive.addons.webhook.service.JsonRestRequestService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WebhookExecutorTest {

    @SuppressWarnings("CanBeFinal")
    @Mock
    JsonRestRequestService mockJsonRestRequestService;

    @Test(expected = UnsuccessfulRequestException.class)
    public void testThatErrorResponseThrowsExceptionBadGateWay() throws Throwable {
        when(mockJsonRestRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new ResponseEntity<>("Hello World", new HttpHeaders(), HttpStatus.BAD_GATEWAY));

        new WebhookExecutor(mockJsonRestRequestService).execute("", "", "", "");
    }

    @Test(expected = UnsuccessfulRequestException.class)
    public void testThatErrorResponseThrowsExceptionUNAUTHORIZED() throws Throwable {
        when(mockJsonRestRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new ResponseEntity<>("Hello World", new HttpHeaders(), HttpStatus.UNAUTHORIZED));

        new WebhookExecutor(mockJsonRestRequestService).execute("POST", "", "", "");
    }

    @Test(expected = UnsuccessfulRequestException.class)
    public void testThatErrorResponseThrowsExceptionFORBIDDEN() throws Throwable {
        when(mockJsonRestRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new ResponseEntity<>("Hello World", new HttpHeaders(), HttpStatus.FORBIDDEN));

        new WebhookExecutor(mockJsonRestRequestService).execute("", "", "", "");
    }

    @Test(expected = UnsuccessfulRequestException.class)
    public void testThatErrorResponseThrowsExceptionNOT_FOUND() throws Throwable {
        when(mockJsonRestRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new ResponseEntity<>("Hello World", new HttpHeaders(), HttpStatus.NOT_FOUND));

        new WebhookExecutor(mockJsonRestRequestService).execute("", "", "", "");
    }

    @Test(expected = UnsuccessfulRequestException.class)
    public void testThatErrorResponseThrowsExceptionSERVICE_UNAVAILABLE() throws Throwable {
        when(mockJsonRestRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new ResponseEntity<>("Hello World", new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE));

        new WebhookExecutor(mockJsonRestRequestService).execute("", "", "", "");
    }

    @Test
    public void testThatErrorResponseSucceedsACCEPTED() throws Throwable {
        when(mockJsonRestRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new ResponseEntity<>("Hello World", new HttpHeaders(), HttpStatus.ACCEPTED));

        new WebhookExecutor(mockJsonRestRequestService).execute("", "", "", "");
    }

    @Test
    public void testThatErrorResponseSucceedsCREATED() throws Throwable {
        when(mockJsonRestRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new ResponseEntity<>("Hello World", new HttpHeaders(), HttpStatus.CREATED));

        new WebhookExecutor(mockJsonRestRequestService).execute("", "", "", "");
    }

    @Test
    public void testThatErrorResponseSucceedsOK() throws Throwable {
        when(mockJsonRestRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new ResponseEntity<>("Hello World", new HttpHeaders(), HttpStatus.OK));

        new WebhookExecutor(mockJsonRestRequestService).execute("", "", "", "");
    }

    @Test
    public void testThatErrorResponseSucceedsNON_AUTHORITATIVE_INFORMATION() throws Throwable {
        when(mockJsonRestRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new ResponseEntity<>("Hello World", new HttpHeaders(), HttpStatus.NON_AUTHORITATIVE_INFORMATION));

        new WebhookExecutor(mockJsonRestRequestService).execute("", "", "", "");
    }
}