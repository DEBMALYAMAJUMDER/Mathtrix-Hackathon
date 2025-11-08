package org.mathtrix.hackathon.service;

import java.io.IOException;

public interface CodeReviewService {
    String repoIndexing(String branch, String owner);

    String reviewSingleRepoWithPrompt(String prompt, String branch, String owner) throws IOException, InterruptedException;
}
