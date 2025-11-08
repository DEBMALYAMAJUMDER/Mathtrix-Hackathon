package org.mathtrix.hackathon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mathtrix.hackathon.constant.APIConstant;
import org.mathtrix.hackathon.service.CodeReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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

    @PostMapping(path = "/index/repository")
    public ResponseEntity<String> indexRepository(@NonNull @RequestParam(name = "githubUrl") String githubUrl,
                                                  @NonNull @RequestParam(name = "branch") String branch) {
        var owner = githubUrl.substring(githubUrl.indexOf(APIConstant.GITHUB_BASE_URL) + APIConstant.GITHUB_BASE_URL.length());
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

//    @PostConstruct
//    public void reviewCode() throws IOException, InterruptedException {
//        String url = "https://github.com/DEBMALYAMAJUMDER/MathTrix-Student";
//        String prompt = "Review this repository for potential improvements.";
//        String branch = "master";
//        String ownerRepo = "DEBMALYAMAJUMDER/MathTrix-Student";
//        var indexResponse = codeReviewService.repoIndexing(branch, ownerRepo);
//        saveResponseToFile(indexResponse, "index-response.json");
//        var response = codeReviewService.reviewSingleRepoWithPrompt(prompt, branch, ownerRepo);
//        saveResponseToFile(response, "code-review-result.json");
//    }
}
