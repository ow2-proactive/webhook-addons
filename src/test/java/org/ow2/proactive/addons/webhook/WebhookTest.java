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
class WebhookTest {

    @Mock
    JsonRestRequestService mockJsonRestRequestService;


    @Test(expected = UnsuccessfulRequestException.class)
    public void testThatErrorResponseThrowsExceptionBadGateWay() throws Throwable {
        when(mockJsonRestRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new ResponseEntity<>("Hello World", new HttpHeaders(), HttpStatus.BAD_GATEWAY));

        Webhook.execute("", "", "", "");
    }

    @Test(expected = UnsuccessfulRequestException.class)
    public void testThatErrorResponseThrowsExceptionUNAUTHORIZED() throws Throwable {
        when(mockJsonRestRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new ResponseEntity<>("Hello World", new HttpHeaders(), HttpStatus.UNAUTHORIZED));

        Webhook.execute("", "", "", "");
    }

    @Test(expected = UnsuccessfulRequestException.class)
    public void testThatErrorResponseThrowsExceptionFORBIDDEN() throws Throwable {
        when(mockJsonRestRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new ResponseEntity<>("Hello World", new HttpHeaders(), HttpStatus.FORBIDDEN));

        Webhook.execute("", "", "", "");
    }

    @Test(expected = UnsuccessfulRequestException.class)
    public void testThatErrorResponseThrowsExceptionNOT_FOUND() throws Throwable {
        when(mockJsonRestRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new ResponseEntity<>("Hello World", new HttpHeaders(), HttpStatus.NOT_FOUND));

        Webhook.execute("", "", "", "");
    }

    @Test(expected = UnsuccessfulRequestException.class)
    public void testThatErrorResponseThrowsExceptionSERVICE_UNAVAILABLE() throws Throwable {
        when(mockJsonRestRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new ResponseEntity<>("Hello World", new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE));

        Webhook.execute("", "", "", "");
    }

    @Test
    public void testThatErrorResponseSucceedsACCEPTED() throws Throwable {
        when(mockJsonRestRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new ResponseEntity<>("Hello World", new HttpHeaders(), HttpStatus.ACCEPTED));

        Webhook.execute("", "", "", "");
    }

    @Test
    public void testThatErrorResponseSucceedsCREATED() throws Throwable {
        when(mockJsonRestRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new ResponseEntity<>("Hello World", new HttpHeaders(), HttpStatus.CREATED));

        Webhook.execute("", "", "", "");
    }

    @Test
    public void testThatErrorResponseSucceedsOK() throws Throwable {
        when(mockJsonRestRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new ResponseEntity<>("Hello World", new HttpHeaders(), HttpStatus.OK));

        Webhook.execute("", "", "", "");
    }

    @Test
    public void testThatErrorResponseSucceedsNON_AUTHORITATIVE_INFORMATION() throws Throwable {
        when(mockJsonRestRequestService.doRequest(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(new ResponseEntity<>("Hello World", new HttpHeaders(), HttpStatus.NON_AUTHORITATIVE_INFORMATION));

        Webhook.execute("", "", "", "");
    }
}