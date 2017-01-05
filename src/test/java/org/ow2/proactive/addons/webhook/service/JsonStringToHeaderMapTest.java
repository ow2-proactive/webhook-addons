/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
package org.ow2.proactive.addons.webhook.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Map;

import org.junit.Test;


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
