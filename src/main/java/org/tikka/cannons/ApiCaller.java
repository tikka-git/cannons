package org.tikka.cannons;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class ApiCaller {

    private String request = "{\n" +
            "  search(query: \"language:scala\", type: USER, first: 100) {\n" +
            "    repositoryCount\n" +
            "    \n" +
            "    edges {\n" +
            "      node {\n" +
            "        ... on Repository {\n" +
            "          nameWithOwner, mentionableUsers(first:10) {totalCount\n" +
            "            nodes{ email,organizations(first:10) {\n" +
            "              edges {\n" +
            "                node  {\n" +
            "                  name\n" +
            "                  location\n" +
            "                  id\n" +
            "                }\n" +
            "              }\n" +
            "            }\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "    \n" +
            "    edges {\n" +
            "      node {\n" +
            "        ... on User {\n" +
            "          name,\n" +
            "          email\n" +
            "          location        \n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  } \n" +
            "}";


    String callingGraph() {
        CloseableHttpClient client;
        CloseableHttpResponse response = null;

        client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://api.github.com/graphql");

        httpPost.addHeader("Authorization", "bearer xxx");
        httpPost.addHeader("Accept", "application/json");

        JSONObject jsonobj = new JSONObject();
        try {
            //jsonobj.put("query", "{repository(owner: \"wso2-extensions\", name: \"identity-inbound-auth-oauth\") { object(expression: \"83253ce50f189db30c54f13afa5d99021e2d7ece\") { ... on Commit { blame(path: \"components/org.wso2.carbon.identity.oauth.endpoint/src/main/java/org/wso2/carbon/identity/oauth/endpoint/authz/OAuth2AuthzEndpoint.java\") { ranges { startingLine, endingLine, age, commit { message url history(first: 2) { edges { node {  message, url } } } author { name, email } } } } } } } }");
            jsonobj.put("query", request);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            StringEntity entity = new StringEntity(jsonobj.toString());

            httpPost.setEntity(entity);
            response = client.execute(httpPost);

        } catch (IOException e) {
            e.printStackTrace();
        }

        assert response != null;
        try (InputStreamReader inputStreamReader = new InputStreamReader(response.getEntity().getContent());
             BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {

                builder.append(line);

            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }
}
