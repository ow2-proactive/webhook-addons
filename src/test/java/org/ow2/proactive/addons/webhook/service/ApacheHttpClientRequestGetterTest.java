package org.ow2.proactive.addons.webhook.service;

import org.junit.Test;

import java.io.IOException;


public class ApacheHttpClientRequestGetterTest {

    private ApacheHttpClientRequestGetter apacheHttpClientRequestGetter = new ApacheHttpClientRequestGetter();

    @Test
    public void testValidRestMethods() throws IOException {
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
            this.apacheHttpClientRequestGetter.getHttpRequestByString(method, "");
        }
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testInvalidRestMethodThrowsException() throws IOException {
        this.apacheHttpClientRequestGetter.getHttpRequestByString("Invalid", "");
    }


}