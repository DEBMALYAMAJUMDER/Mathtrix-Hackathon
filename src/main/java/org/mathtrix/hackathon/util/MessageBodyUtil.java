package org.mathtrix.hackathon.util;

import org.mathtrix.hackathon.constant.APIConstant;
import org.mathtrix.hackathon.entity.RepoIndexEntity;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MessageBodyUtil {
    private MessageBodyUtil() {

    }

    public static String getQueryMessageBody(String prompt,
                                             List<String> repos,
                                             List<String> branches) {

        if (repos == null || branches == null || repos.size() != branches.size()) {
            throw new IllegalArgumentException("Repos and branches must be non-null and of same size");
        }

        String repositoriesJson = IntStream.range(0, repos.size())
                .mapToObj(i -> String.format("""
                        {
                          "remote": "github",
                          "repository": "%s",
                          "branch": "%s"
                        }
                        """, repos.get(i), branches.get(i)))
                .collect(Collectors.joining(",\n"));

        return String.format("""
                {
                  "repositories": [
                    %s
                  ],
                  "query": "%s"
                }
                """, repositoriesJson, prompt);
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

    public static Map<String, List<String>> getOwnerAndBranch(List<RepoIndexEntity> repoIndexEntities) {
        Map<String, List<String>> ownerRequestMap = new HashMap<>();
        ownerRequestMap.put(APIConstant.OWNER, repoIndexEntities.stream()
                .map(repoIndexEntity -> getOwner(repoIndexEntity.getGithubUrl()))
                .toList());
        ownerRequestMap.put(APIConstant.BRANCH, repoIndexEntities.stream()
                .map(RepoIndexEntity::getBranch)
                .toList());
        return ownerRequestMap;
    }

    public static String getOwner(String gitUrl) {
        return gitUrl.substring(gitUrl.indexOf(APIConstant.GITHUB_BASE_URL) + APIConstant.GITHUB_BASE_URL.length());
    }

    public static String getProjectName(String projectName) {
        return ObjectUtils.isEmpty(projectName) ? APIConstant.DEFAULT : projectName;
    }
}
