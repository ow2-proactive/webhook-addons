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
package org.ow2.proactive.addons.webhook;

import java.io.IOException;

import org.apache.http.HttpStatus;
import org.ow2.proactive.addons.webhook.exception.UnsuccessfulRequestException;
import org.ow2.proactive.addons.webhook.model.RestResponse;
import org.ow2.proactive.addons.webhook.service.JsonRestApacheRequestService;

import lombok.AllArgsConstructor;


@SuppressWarnings("WeakerAccess")
@AllArgsConstructor
public class WebhookExecutor {

    private JsonRestApacheRequestService jsonRestApacheRequestService;

    private static boolean isResponseCodeIndicatingFailure(int restCallResponseCode) {
        return restCallResponseCode >= HttpStatus.SC_BAD_REQUEST;
    }

    public RestResponse execute(String method, String url, String headers, String content)
            throws UnsuccessfulRequestException, IOException {
        RestResponse restResponse = jsonRestApacheRequestService.doRequest(method, headers, url, content);

        if (isResponseCodeIndicatingFailure(restResponse.getResponseCode())) {
            throw new UnsuccessfulRequestException(restResponse.toString());
        }

        return restResponse;
    }
}
