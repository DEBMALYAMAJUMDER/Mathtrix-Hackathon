package org.mathtrix.hackathon.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mathtrix.hackathon.client.GreptileClient;
import org.mathtrix.hackathon.constant.APIConstant;
import org.mathtrix.hackathon.exception.GreptileServerException;
import org.mathtrix.hackathon.util.MessageBodyUtil;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GreptileCodeReviewServiceImplTest {

    @Mock
    private GreptileClient greptileClient;

    @InjectMocks
    private GreptileCodeReviewServiceImpl greptileCodeReviewService;

    private static final String TEST_API_KEY = "test-api-key";

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        setPrivateField(greptileCodeReviewService, "greptileApiKey", TEST_API_KEY);
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    void testRepoIndexing_Success() {
        String branch = "main";
        String owner = "testOwner";
        String expectedBody = "{\"action\":\"index\"}";
        String expectedResponse = "Indexing started";

        try (MockedStatic<MessageBodyUtil> mockedMessageBodyUtil = mockStatic(MessageBodyUtil.class)) {
            mockedMessageBodyUtil.when(() -> MessageBodyUtil.getIndexRepoMessageBody(branch, owner))
                    .thenReturn(expectedBody);

            when(greptileClient.indexRepo(eq(APIConstant.BEARER + TEST_API_KEY), eq(expectedBody)))
                    .thenReturn(expectedResponse);

            String result = greptileCodeReviewService.repoIndexing(branch, owner);

            assertEquals(expectedResponse, result);
            mockedMessageBodyUtil.verify(() -> MessageBodyUtil.getIndexRepoMessageBody(branch, owner));
            verify(greptileClient).indexRepo(eq(APIConstant.BEARER + TEST_API_KEY), eq(expectedBody));
        }
    }

    @Test
    void testRepoIndexing_Exception() {
        String branch = "main";
        String owner = "testOwner";
        RuntimeException cause = new RuntimeException("API Error");

        try (MockedStatic<MessageBodyUtil> mockedMessageBodyUtil = mockStatic(MessageBodyUtil.class)) {
            mockedMessageBodyUtil.when(() -> MessageBodyUtil.getIndexRepoMessageBody(branch, owner))
                    .thenThrow(cause);

            GreptileServerException exception = assertThrows(GreptileServerException.class,
                    () -> greptileCodeReviewService.repoIndexing(branch, owner));

            assertEquals(APIConstant.LOGGER_FORMATTED_EXCEPTION, exception.getMessage());
            assertEquals(cause, exception.getCause());
        }
    }

    @Test
    void testReviewSingleRepoWithPrompt_Success() {
        String prompt = "Check for bugs";
        String branch = "develop";
        String owner = "testOwner";
        String expectedBody = "{\"prompt\":\"Check for bugs\"}";
        String expectedResponse = "Review result";

        try (MockedStatic<MessageBodyUtil> mockedMessageBodyUtil = mockStatic(MessageBodyUtil.class)) {
            mockedMessageBodyUtil.when(() -> MessageBodyUtil.getQueryMessageBody(prompt, branch, owner))
                    .thenReturn(expectedBody);

            when(greptileClient.queryRepo(eq(APIConstant.BEARER + TEST_API_KEY), eq(expectedBody)))
                    .thenReturn(expectedResponse);

            String result = greptileCodeReviewService.reviewSingleRepoWithPrompt(prompt, branch, owner);

            assertEquals(expectedResponse, result);
            mockedMessageBodyUtil.verify(() -> MessageBodyUtil.getQueryMessageBody(prompt, branch, owner));
            verify(greptileClient).queryRepo(eq(APIConstant.BEARER + TEST_API_KEY), eq(expectedBody));
        }
    }

    @Test
    void testReviewSingleRepoWithPrompt_Exception() {
        String prompt = "Check for bugs";
        String branch = "develop";
        String owner = "testOwner";
        RuntimeException cause = new RuntimeException("Client Error");

        try (MockedStatic<MessageBodyUtil> mockedMessageBodyUtil = mockStatic(MessageBodyUtil.class)) {
            mockedMessageBodyUtil.when(() -> MessageBodyUtil.getQueryMessageBody(prompt, branch, owner))
                    .thenReturn("some body");

            when(greptileClient.queryRepo(anyString(), anyString())).thenThrow(cause);

            GreptileServerException exception = assertThrows(GreptileServerException.class,
                    () -> greptileCodeReviewService.reviewSingleRepoWithPrompt(prompt, branch, owner));

            assertEquals(APIConstant.LOGGER_FORMATTED_EXCEPTION, exception.getMessage());
            assertEquals(cause, exception.getCause());
        }
    }

    @Test
    void testReviewRepositories_Success() {
        String prompt = "Multi repo review";
        List<String> repos = Arrays.asList("repo1", "repo2");
        List<String> branches = Arrays.asList("main", "dev");
        String expectedBody = "{\"prompt\":\"Multi repo review\",\"repos\":[\"repo1\",\"repo2\"]}";
        String expectedResponse = "Batch review result";

        try (MockedStatic<MessageBodyUtil> mockedMessageBodyUtil = mockStatic(MessageBodyUtil.class)) {
            mockedMessageBodyUtil.when(() -> MessageBodyUtil.getQueryMessageBody(prompt, repos, branches))
                    .thenReturn(expectedBody);

            when(greptileClient.queryRepo(eq(APIConstant.BEARER + TEST_API_KEY), eq(expectedBody)))
                    .thenReturn(expectedResponse);

            String result = greptileCodeReviewService.reviewRepositories(prompt, repos, branches);

            assertEquals(expectedResponse, result);
            mockedMessageBodyUtil.verify(() -> MessageBodyUtil.getQueryMessageBody(prompt, repos, branches));
            verify(greptileClient).queryRepo(eq(APIConstant.BEARER + TEST_API_KEY), eq(expectedBody));
        }
    }

    @Test
    void testReviewRepositories_Exception() {
        String prompt = "Multi repo review";
        List<String> repos = Arrays.asList("repo1", "repo2");
        List<String> branches = Arrays.asList("main", "dev");
        RuntimeException cause = new RuntimeException("Connection Error");

        try (MockedStatic<MessageBodyUtil> mockedMessageBodyUtil = mockStatic(MessageBodyUtil.class)) {
            mockedMessageBodyUtil.when(() -> MessageBodyUtil.getQueryMessageBody(prompt, repos, branches))
                    .thenThrow(cause);

            GreptileServerException exception = assertThrows(GreptileServerException.class,
                    () -> greptileCodeReviewService.reviewRepositories(prompt, repos, branches));

            assertEquals(APIConstant.LOGGER_FORMATTED_EXCEPTION, exception.getMessage());
            assertEquals(cause, exception.getCause());
        }
    }
}