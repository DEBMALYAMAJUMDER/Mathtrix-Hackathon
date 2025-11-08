package org.mathtrix.hackathon.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mathtrix.hackathon.client.GreptileClient;
import org.mathtrix.hackathon.constant.APIConstant;
import org.mathtrix.hackathon.service.CodeReviewService;
import org.mathtrix.hackathon.util.MessageBodyUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class GreptileCodeReviewServiceImpl implements CodeReviewService {
    @Value("${GREPTILE_API_KEY}")
    private String greptileApiKey;
    private String GITHUB_TOKEN = "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";
    private final GreptileClient greptileClient;

    @Override
    public String repoIndexing(String branch, String owner) {
        var body = MessageBodyUtil.getIndexRepoMessageBody(branch, owner);
        log.info("Request Body : [{}]", body);
        return greptileClient.indexRepo(APIConstant.BEARER + greptileApiKey, body);
    }

    @Override
    public String reviewSingleRepoWithPrompt(String prompt, String branch, String owner) {
        String body = MessageBodyUtil.getQueryMessageBody(prompt, branch, owner);

        return greptileClient.queryRepo(APIConstant.BEARER + greptileApiKey, body);
    }
}
