package org.ow2.proactive.addons.webhook.service;

import org.junit.Test;
import org.springframework.http.HttpHeaders;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class JsonStringToRestHttpHeadersTest {

    @SuppressWarnings("CanBeFinal")
    private JsonStringToRestHttpHeaders jsonStringToRestHttpHeaders = new JsonStringToRestHttpHeaders();

    @Test
    public void testThatEmptyJsonConvertsToEmptyHeader() {
        HttpHeaders emptyHeaders = this.jsonStringToRestHttpHeaders.convert("{}");
        assertThat(emptyHeaders.entrySet().size(), is(0));
    }

    @Test
    public void testThatJsonElementIsContainedInsideHeaders() {
        HttpHeaders emptyHeaders = this.jsonStringToRestHttpHeaders.convert("{\"Content-Type\":\"application/json\"}");
        assertThat(emptyHeaders.get("Content-Type").get(0), is("application/json"));
    }

    @Test
    public void testThatTwoJsonElementsAreContainedInsideHeaders() {
        HttpHeaders emptyHeaders = this.jsonStringToRestHttpHeaders.convert("{\"Content-Type\":\"application/json\", \"secret\":\"mySecret\"}");
        assertThat(emptyHeaders.get("Content-Type").get(0), is("application/json"));
        assertThat(emptyHeaders.get("secret").get(0), is("mySecret"));
    }

    @Test
    public void testThatTwoJsonElementSeparatedByNewLineAreContainedInsideHeaders() {
        HttpHeaders emptyHeaders = this.jsonStringToRestHttpHeaders.convert("{\"Content-Type\":\"application/json\", \n \"secret\":\"mySecret\"}");
        assertThat(emptyHeaders.get("Content-Type").get(0), is("application/json"));
        assertThat(emptyHeaders.get("secret").get(0), is("mySecret"));
    }

    @Test(expected = org.json.JSONException.class)
    public void testThatMalformedJsonThrowsException() {
        this.jsonStringToRestHttpHeaders.convert("{\"Content-Type\":}}\"application/json\", \n \"secret\":\"mySecret\"}");
    }

}