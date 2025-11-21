package org.mathtrix.hackathon.util;

import org.junit.jupiter.api.Test;
import org.mathtrix.hackathon.constant.APIConstant;
import org.mathtrix.hackathon.entity.RepoIndexEntity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MessageBodyUtilTest {

    @Test
    void testConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<MessageBodyUtil> constructor = MessageBodyUtil.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        MessageBodyUtil instance = constructor.newInstance();
        assertNotNull(instance);
    }

    @Test
    void testGetQueryMessageBodyWithLists_Valid() {
        String prompt = "Find bug";
        List<String> repos = Arrays.asList("user/repo1", "user/repo2");
        List<String> branches = Arrays.asList("main", "dev");

        String result = MessageBodyUtil.getQueryMessageBody(prompt, repos, branches);

        assertNotNull(result);
        assertTrue(result.contains("\"remote\": \"github\""));
        assertTrue(result.contains("\"repository\": \"user/repo1\""));
        assertTrue(result.contains("\"branch\": \"main\""));
        assertTrue(result.contains("\"repository\": \"user/repo2\""));
        assertTrue(result.contains("\"branch\": \"dev\""));
        // Check the modified prompt
        String expectedPromptEnd = String.format(APIConstant.ENDING_PROMPT, 2);
        assertTrue(result.contains(expectedPromptEnd));
    }

    @Test
    void testGetQueryMessageBodyWithLists_Invalid() {
        String prompt = "test";
        List<String> list = Collections.singletonList("val");

        assertThrows(IllegalArgumentException.class, () -> MessageBodyUtil.getQueryMessageBody(prompt, null, list));
        assertThrows(IllegalArgumentException.class, () -> MessageBodyUtil.getQueryMessageBody(prompt, list, null));
        assertThrows(IllegalArgumentException.class, () -> MessageBodyUtil.getQueryMessageBody(prompt, list, Collections.emptyList()));
    }

    @Test
    void testGetQueryMessageBodySingleRepo() {
        String prompt = "Analyze this";
        String branch = "feature";
        String owner = "user/my-repo";

        String result = MessageBodyUtil.getQueryMessageBody(prompt, branch, owner);

        assertNotNull(result);
        assertTrue(result.contains("\"repository\": \"user/my-repo\""));
        assertTrue(result.contains("\"branch\": \"feature\""));
        assertTrue(result.contains("\"query\": \"Analyze this\""));
    }

    @Test
    void testGetIndexRepoMessageBody() {
        String branch = "hotfix";
        String owner = "org/repo";

        String result = MessageBodyUtil.getIndexRepoMessageBody(branch, owner);

        assertNotNull(result);
        assertTrue(result.contains("\"repository\": \"org/repo\""));
        assertTrue(result.contains("\"branch\": \"hotfix\""));
        assertTrue(result.contains("\"remote\": \"github\""));
    }

    @Test
    void testGetOwnerAndBranch() {
        RepoIndexEntity entity1 = new RepoIndexEntity();
        entity1.setGithubUrl("https://github.com/user1/repo1");
        entity1.setBranch("main");

        RepoIndexEntity entity2 = new RepoIndexEntity();
        entity2.setGithubUrl("https://github.com/user2/repo2");
        entity2.setBranch("develop");

        List<RepoIndexEntity> entities = Arrays.asList(entity1, entity2);

        Map<String, List<String>> result = MessageBodyUtil.getOwnerAndBranch(entities);

        assertNotNull(result);
        assertTrue(result.containsKey(APIConstant.OWNER));
        assertTrue(result.containsKey(APIConstant.BRANCH));

        List<String> owners = result.get(APIConstant.OWNER);
        List<String> branches = result.get(APIConstant.BRANCH);

        assertEquals(2, owners.size());
        assertEquals("user1/repo1", owners.get(0));
        assertEquals("user2/repo2", owners.get(1));

        assertEquals(2, branches.size());
        assertEquals("main", branches.get(0));
        assertEquals("develop", branches.get(1));
    }

    @Test
    void testGetOwner() {
        String url = "https://github.com/someuser/somerepo";
        String owner = MessageBodyUtil.getOwner(url);
        assertEquals("someuser/somerepo", owner);
    }

    @Test
    void testGetProjectName() {
        assertEquals("MyProject", MessageBodyUtil.getProjectName("MyProject"));
        assertEquals(APIConstant.DEFAULT, MessageBodyUtil.getProjectName(null));
        assertEquals(APIConstant.DEFAULT, MessageBodyUtil.getProjectName(""));
    }
}