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

import org.ow2.proactive.addons.webhook.model.RestResponse;
import org.ow2.proactive.addons.webhook.service.ApacheHttpClientRequestGetter;
import org.ow2.proactive.addons.webhook.service.JsonRestApacheRequestService;
import org.ow2.proactive.addons.webhook.service.JsonStringToHeaderMap;


@SuppressWarnings("WeakerAccess")
public class Webhook {

    @SuppressWarnings("CanBeFinal")
    private static WebhookExecutor webhookExecutor = new WebhookExecutor(new JsonRestApacheRequestService(new JsonStringToHeaderMap(),
                                                                                                          new ApacheHttpClientRequestGetter()));

    // Example with GET
    // method: "GET"
    // url: "http://www.activeeon.com"
    // headers: "{User-Agent = Mozilla/5.0}"

    // Example with POST
    // method: "POST"
    // url: "http://trydev.activeeon.com:8080/connector-iaas/infrastructures"
    // headers: "{User-Agent = Mozilla/5.0, Content-Type = application/json}"
    // content: "{\"id\": \"demo-aws\",\"type\": \"aws-ec2\",\"credentials\": {\"username\": \"AKIAIXZCJACIJA7YL3AQ\",\"password\": \"fMWyE93klwSIzLxO8wTAnGlQNdNHWForaN6hMOq\"}}"

    public static RestResponse execute(String method, String url, String headers, String content) throws Throwable {
        RestResponse response = webhookExecutor.execute(method, url, headers, content);
        System.out.println(response.getResponse());
        return response;
    }

}
