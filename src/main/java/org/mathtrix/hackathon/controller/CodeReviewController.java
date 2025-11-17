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
import org.mathtrix.hackathon.entity.RepoQueryEntity;
import org.mathtrix.hackathon.service.CodeReviewService;
import org.mathtrix.hackathon.util.MessageBodyUtil;
import org.mathtrix.hackathon.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;


@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
@Slf4j
public class CodeReviewController {
    private final CodeReviewService codeReviewService;
    private final static String INDEX_REPO = "repo_index_";
    private final static String QUERY_REPO = "query_repo_";

    @PostMapping(path = "/index/repository")
    public ResponseEntity<String> indexRepository(@Valid @RequestBody RepoIndexEntity repoIndexEntity) {
        var githubUrl = repoIndexEntity.getGithubUrl();
        var branch = repoIndexEntity.getBranch();
        var projectName = repoIndexEntity.getProject();
        var owner = MessageBodyUtil.getOwner(githubUrl);
        projectName = MessageBodyUtil.getProjectName(projectName);
        var response = codeReviewService.repoIndexing(branch, owner);
        var indexResponse = ResponseUtil.getResponseMessage(response);
        return new ResponseEntity<>(indexResponse, HttpStatus.OK);
    }

    @PostMapping(path = "/query/repository")
    @SneakyThrows
    public ResponseEntity<String> queryRepository(@Valid @RequestBody RepoQueryEntity repoQueryEntity) {
        String response = "";
        var queryEntity = repoQueryEntity.getRepoEntities();
        var query = repoQueryEntity.getQuery();
        var ownerRequestedMap = MessageBodyUtil.getOwnerAndBranch(queryEntity);
        if (ownerRequestedMap.containsKey(APIConstant.BRANCH) && ownerRequestedMap.containsKey(APIConstant.OWNER)) {
            response = codeReviewService.reviewRepositories(query, ownerRequestedMap.get(APIConstant.OWNER)
                    , ownerRequestedMap.get(APIConstant.BRANCH));
        }
        var reviewResponse = ResponseUtil.getResponseMessage(response);
        return new ResponseEntity<>(reviewResponse, HttpStatus.OK);
    }
}
