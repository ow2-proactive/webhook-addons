/*
 *  *
 * ProActive Parallel Suite(TM): The Java(TM) library for
 *    Parallel, Distributed, Multi-Core Computing for
 *    Enterprise Grids & Clouds
 *
 * Copyright (C) 1997-2016 INRIA/University of
 *                 Nice-Sophia Antipolis/ActiveEon
 * Contact: proactive@ow2.org or contact@activeeon.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; version 3 of
 * the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 *
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s): ActiveEon Team - http://www.activeeon.com
 *
 *  * $$ACTIVEEON_CONTRIBUTOR$$
 */
package org.ow2.proactive.addons.webhook.service;


import lombok.AllArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.ow2.proactive.addons.webhook.model.RestResponse;

import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
public class JsonRestApacheRequestService {

    private JsonStringToHeaderMap jsonStringToHeaderMap;
    private ApacheHttpClientRequestGetter apacheHttpClientRequestGetter;


    public RestResponse doRequest(String restMethod, String jsonHeader, String url, String content) throws IOException {
        Request request = apacheHttpClientRequestGetter.getHttpRequestByString(restMethod, url);

        for (Map.Entry<String, String> entry : jsonStringToHeaderMap.convert(jsonHeader).entrySet()) {
            request.addHeader(entry.getKey(), entry.getValue());
        }

        if(content != null && !content.isEmpty()) {
            request.bodyString(content, ContentType.TEXT_PLAIN);
        }

        return executeRequest(request);
    }

    @SuppressWarnings("WeakerAccess")
    protected RestResponse executeRequest(final Request request) throws IOException {
        Response requestResponse = request.execute();
        HttpResponse responseObject = requestResponse.returnResponse();
        return new RestResponse(
                responseObject.getStatusLine().getStatusCode(),
                EntityUtils.toString(responseObject.getEntity()));
    }
}
