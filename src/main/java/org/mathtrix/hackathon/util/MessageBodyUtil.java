package org.mathtrix.hackathon.util;

public class MessageBodyUtil {
    private MessageBodyUtil() {

    }

    public static String getQueryMessageBody(String prompt, String branch, String owner) {
        var bodyJson = String.format("""
                {
                  "repositories": [
                    {
                      "remote": "github",
                      "repository": "%s",
                      "branch": "%s"
                    }
                  ],
                  "query": "%s"
                }
                """, owner, branch, prompt);
        return bodyJson;
    }

    public static String getIndexRepoMessageBody(String branch, String owner) {
        var bodyJson = String.format("""
                {
                   "remote": "github",
                   "repository": "%s",
                   "branch": "%s"
                }
                """, owner, branch);
        return bodyJson;
    }
}
