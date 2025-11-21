package org.mathtrix.hackathon.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mathtrix.hackathon.constant.APIConstant;
import org.mathtrix.hackathon.entity.RepoIndexEntity;
import org.mathtrix.hackathon.entity.RepoQueryEntity;
import org.mathtrix.hackathon.service.CodeReviewService;
import org.mathtrix.hackathon.util.MessageBodyUtil;
import org.mathtrix.hackathon.util.ResponseUtil;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CodeReviewControllerTest {

    @Mock
    private CodeReviewService codeReviewService;

    @InjectMocks
    private CodeReviewController codeReviewController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIndexRepository() {
        RepoIndexEntity repoIndexEntity = RepoIndexEntity.builder()
                .githubUrl("https://github.com/owner/repo")
                .branch("main")
                .project("project")
                .build();

        try (MockedStatic<MessageBodyUtil> messageBodyUtilMock = mockStatic(MessageBodyUtil.class);
             MockedStatic<ResponseUtil> responseUtilMock = mockStatic(ResponseUtil.class)) {

            messageBodyUtilMock.when(() -> MessageBodyUtil.getOwner(repoIndexEntity.getGithubUrl()))
                    .thenReturn("owner");
            messageBodyUtilMock.when(() -> MessageBodyUtil.getProjectName(repoIndexEntity.getProject()))
                    .thenReturn("project_name");

            when(codeReviewService.repoIndexing("main", "owner")).thenReturn("indexing_success");

            responseUtilMock.when(() -> ResponseUtil.getResponseMessage("indexing_success"))
                    .thenReturn("json_response");

            ResponseEntity<String> result = codeReviewController.indexRepository(repoIndexEntity);

            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals("json_response", result.getBody());
            verify(codeReviewService).repoIndexing("main", "owner");
        }
    }

    @Test
    void testQueryRepository() {
        RepoIndexEntity repoIndex1 = RepoIndexEntity.builder().githubUrl("url1").branch("b1").build();
        List<RepoIndexEntity> repoEntities = List.of(repoIndex1);
        RepoQueryEntity repoQueryEntity = RepoQueryEntity.builder()
                .repoEntities(repoEntities)
                .Query("my query")
                .build();

        List<String> owners = List.of("owner1");
        List<String> branches = List.of("b1");
        Map<String, List<String>> ownerBranchMap = Map.of(
                APIConstant.OWNER, owners,
                APIConstant.BRANCH, branches
        );

        try (MockedStatic<MessageBodyUtil> messageBodyUtilMock = mockStatic(MessageBodyUtil.class);
             MockedStatic<ResponseUtil> responseUtilMock = mockStatic(ResponseUtil.class)) {

            messageBodyUtilMock.when(() -> MessageBodyUtil.getOwnerAndBranch(repoEntities))
                    .thenReturn(ownerBranchMap);

            when(codeReviewService.reviewRepositories("my query", owners, branches))
                    .thenReturn("review_success");

            responseUtilMock.when(() -> ResponseUtil.getResponseMessage("review_success"))
                    .thenReturn("final_response");

            ResponseEntity<String> result = codeReviewController.queryRepository(repoQueryEntity);

            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals("final_response", result.getBody());
            verify(codeReviewService).reviewRepositories("my query", owners, branches);
        }
    }

    @Test
    void testQueryRepository_MissingOwnerOrBranch() {
        RepoIndexEntity repoIndex1 = RepoIndexEntity.builder().githubUrl("url1").branch("b1").build();
        List<RepoIndexEntity> repoEntities = List.of(repoIndex1);
        RepoQueryEntity repoQueryEntity = RepoQueryEntity.builder()
                .repoEntities(repoEntities)
                .Query("my query")
                .build();

        // Map missing APIConstant.OWNER
        Map<String, List<String>> ownerBranchMap = Map.of(
                APIConstant.BRANCH, List.of("b1")
        );

        try (MockedStatic<MessageBodyUtil> messageBodyUtilMock = mockStatic(MessageBodyUtil.class);
             MockedStatic<ResponseUtil> responseUtilMock = mockStatic(ResponseUtil.class)) {

            messageBodyUtilMock.when(() -> MessageBodyUtil.getOwnerAndBranch(repoEntities))
                    .thenReturn(ownerBranchMap);

            // Expect service NOT to be called
            // Expect response to be based on empty string

            responseUtilMock.when(() -> ResponseUtil.getResponseMessage(""))
                    .thenReturn("empty_response");

            ResponseEntity<String> result = codeReviewController.queryRepository(repoQueryEntity);

            assertEquals(HttpStatus.OK, result.getStatusCode());
            assertEquals("empty_response", result.getBody());
            verify(codeReviewService, never()).reviewRepositories(any(), any(), any());
        }
    }
}