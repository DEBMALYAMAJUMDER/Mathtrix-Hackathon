package org.mathtrix.hackathon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.mathtrix.hackathon.constant.APIConstant;
import org.mathtrix.hackathon.entity.RepoIndexEntity;
import org.mathtrix.hackathon.service.CodeReviewService;
import org.mathtrix.hackathon.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
@Slf4j
public class CodeReviewController {
    private final CodeReviewService codeReviewService;
    private final static String INDEX_REPO = "repo_index_";

    @PostMapping(path = "/index/repository")
    public ResponseEntity<String> indexRepository(@Valid @RequestBody RepoIndexEntity repoIndexEntity) {
        var githubUrl = repoIndexEntity.getGithubUrl();
        var branch = repoIndexEntity.getBranch();
        var projectName = repoIndexEntity.getProject();
        var owner = githubUrl.substring(githubUrl.indexOf(APIConstant.GITHUB_BASE_URL) + APIConstant.GITHUB_BASE_URL.length());
        projectName = ObjectUtils.isEmpty(projectName) ? "DEFAULT" : projectName;
        var response = codeReviewService.repoIndexing(branch, owner);
        var saveResponse = ResponseUtil.saveResponseToFile(response, INDEX_REPO + projectName + "_" + branch + ".json");
        return new ResponseEntity<>(saveResponse, HttpStatus.OK);
    }
}
