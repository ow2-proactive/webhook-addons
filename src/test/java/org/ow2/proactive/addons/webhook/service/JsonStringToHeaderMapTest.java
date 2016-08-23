package org.ow2.proactive.addons.webhook.service;

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JsonStringToHeaderMapTest {

    @SuppressWarnings("CanBeFinal")
    private JsonStringToHeaderMap jsonStringToHeaderMap = new JsonStringToHeaderMap();

    @Test
    public void testThatEmptyJsonConvertsToEmptyHeader() {
        Map<String, String> emptyMap = this.jsonStringToHeaderMap.convert("{}");
        assertThat(emptyMap.size(), is(0));
    }

    @Test
    public void testThatJsonElementIsContainedInsideHeaders() {
        Map<String, String> emptyHeaders = this.jsonStringToHeaderMap.convert("{\"Content-Type\":\"application/json\"}");
        assertThat(emptyHeaders.get("Content-Type"), is("application/json"));
    }

    @Test
    public void testThatTwoJsonElementsAreContainedInsideHeaders() {
        Map<String, String> emptyHeaders = this.jsonStringToHeaderMap.convert("{\"Content-Type\":\"application/json\", \"secret\":\"mySecret\"}");
        assertThat(emptyHeaders.get("Content-Type"), is("application/json"));
        assertThat(emptyHeaders.get("secret"), is("mySecret"));
    }

    @Test
    public void testThatTwoJsonElementSeparatedByNewLineAreContainedInsideHeaders() {
        Map<String, String> emptyHeaders = this.jsonStringToHeaderMap.convert("{\"Content-Type\":\"application/json\", \n \"secret\":\"mySecret\"}");
        assertThat(emptyHeaders.get("Content-Type"), is("application/json"));
        assertThat(emptyHeaders.get("secret"), is("mySecret"));
    }

    @Test(expected = org.json.JSONException.class)
    public void testThatMalformedJsonThrowsException() {
        this.jsonStringToHeaderMap.convert("{\"Content-Type\":}}\"application/json\", \n \"secret\":\"mySecret\"}");
    }

}