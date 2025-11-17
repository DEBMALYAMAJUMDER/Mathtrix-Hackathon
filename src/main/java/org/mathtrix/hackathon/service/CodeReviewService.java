package org.mathtrix.hackathon.service;

import java.io.IOException;
import java.util.List;

public interface CodeReviewService {
    String repoIndexing(String branch, String owner);

    String reviewSingleRepoWithPrompt(String prompt, String branch, String owner) throws IOException, InterruptedException;

    String reviewRepositories(String prompt, List<String> repos, List<String> branches);
}
