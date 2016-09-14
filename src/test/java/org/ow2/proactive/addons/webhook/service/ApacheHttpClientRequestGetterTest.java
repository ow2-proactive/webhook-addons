package org.ow2.proactive.addons.webhook.service;

import java.io.IOException;

import org.junit.Test;


public class ApacheHttpClientRequestGetterTest {

    @SuppressWarnings("CanBeFinal")
    private ApacheHttpClientRequestGetter apacheHttpClientRequestGetter = new ApacheHttpClientRequestGetter();

    @Test
    public void testValidRestMethods() throws IOException {
        String[] restMethods = { "GET", "POST", "HEAD", "PUT", "DELETE", "OPTIONS", "TRACE" };

        for (String method : restMethods) {
            this.apacheHttpClientRequestGetter.getHttpRequestByString(method, "");
        }
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testInvalidRestMethodThrowsException() throws IOException {
        this.apacheHttpClientRequestGetter.getHttpRequestByString("Invalid", "");
    }

}