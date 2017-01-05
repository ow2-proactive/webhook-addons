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

import org.apache.http.client.fluent.Request;


public class ApacheHttpClientRequestGetter {

    public Request getHttpRequestByString(String method, String url) {
        switch (method) {
            case "GET":
                return Request.Get(url);
            case "POST":
                return Request.Post(url);
            case "HEAD":
                return Request.Head(url);
            case "PUT":
                return Request.Put(url);
            case "DELETE":
                return Request.Delete(url);
            case "OPTIONS":
                return Request.Options(url);
            case "TRACE":
                return Request.Trace(url);
            default:
                throw new IllegalArgumentException(method + " is not supported as a rest method");
        }
    }
}
