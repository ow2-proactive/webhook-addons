package manualtasks;

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


public class WebHookTask extends JavaExecutable {

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
