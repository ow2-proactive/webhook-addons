/*
 *  *
 * ProActive Parallel Suite(TM): The Java(TM) library for
 *    Parallel, Distributed, Multi-Core Computing for
 *    Enterprise Grids & Clouds
 *
 * Copyright (C) 1997-2015 INRIA/University of
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
package org.ow2.proactive.addons.webhook.task;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.ow2.proactive.scheduler.common.task.TaskResult;
import org.ow2.proactive.scheduler.common.task.executable.JavaExecutable;

public class WebhookTask extends JavaExecutable {

    private static final String ARG_USER_AGENT = "User-Agent";
    private static final String ARG_METHOD = "method";
    private static final String ARG_URL = "url";
    private static final String ARG_POST_PARAMS = "post_params";

    private String user_agent = null;
    private String method = null;
    private String url = null;
    private String post_params = null;



    @Override
    public void init(Map<String, Serializable> args) throws Exception {

        if (args.containsKey(ARG_USER_AGENT)) {
            this.user_agent = ((String) args.get(ARG_USER_AGENT));
        }
        if (args.containsKey(ARG_METHOD)) {
            this.method = ((String) args.get(ARG_METHOD));
        }
        if (args.containsKey(ARG_URL)) {
            this.url = ((String) args.get(ARG_URL));
        }
        if (args.containsKey(ARG_POST_PARAMS)) {
            this.post_params = ((String) args.get(ARG_POST_PARAMS));
        }
    }

    @Override
    public Serializable execute(TaskResult... taskResults) throws Throwable {

        // Request construction & execution

        URL obj = new URL(this.url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod(this.method);
        con.setRequestProperty(ARG_USER_AGENT, this.user_agent);

        if (method == "POST")
        {
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(this.post_params.getBytes());
            os.flush();
            os.close();
        }

        // Request response

        int responseCode = con.getResponseCode();
        String responseCodeStr = method + " Response Code :: " + responseCode;
        System.out.println(responseCodeStr);

        if (responseCode == HttpURLConnection.HTTP_OK)
        {
            InputStream is = con.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            // print result
            System.out.println(response.toString());
            return response.toString();
        } else {
            System.out.println(method + " request not worked");
            return responseCodeStr;
        }
    }

}
