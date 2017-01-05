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

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.ow2.proactive.addons.webhook.model.RestResponse;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class JsonRestApacheRequestService {

    private JsonStringToHeaderMap jsonStringToHeaderMap;

    private ApacheHttpClientRequestGetter apacheHttpClientRequestGetter;

    public RestResponse doRequest(String restMethod, String jsonHeader, String url, String content) throws IOException {
        Request request = apacheHttpClientRequestGetter.getHttpRequestByString(restMethod, url);

        for (Map.Entry<String, String> entry : jsonStringToHeaderMap.convert(jsonHeader).entrySet()) {
            request.addHeader(entry.getKey(), entry.getValue());
        }

        if (content != null && !content.isEmpty()) {
            request.bodyString(content, ContentType.TEXT_PLAIN);
        }

        return executeRequest(request);
    }

    @SuppressWarnings("WeakerAccess")
    protected RestResponse executeRequest(final Request request) throws IOException {
        Response requestResponse = request.execute();
        HttpResponse responseObject = requestResponse.returnResponse();
        return new RestResponse(responseObject.getStatusLine().getStatusCode(),
                                EntityUtils.toString(responseObject.getEntity()));
    }
}
