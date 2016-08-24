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
 *  Contributor(s):
 *
 *  * $$ACTIVEEON_INITIAL_DEV$$
 */
package org.ow2.proactive.addons.webhook;

import org.ow2.proactive.addons.webhook.service.ApacheHttpClientRequestGetter;
import org.ow2.proactive.addons.webhook.service.JsonRestApacheRequestService;
import org.ow2.proactive.addons.webhook.service.JsonStringToHeaderMap;

@SuppressWarnings("WeakerAccess")
public class Webhook {

    @SuppressWarnings("CanBeFinal")
    private static WebhookExecutor webhookExecutor = new WebhookExecutor(
            new JsonRestApacheRequestService(
                    new JsonStringToHeaderMap(),
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

    public static void execute(String method, String url, String headers, String content) throws Throwable {
        String stringResponse = webhookExecutor.execute(method, url, headers, content).getResponse();
        System.out.println(stringResponse);
    }


}
