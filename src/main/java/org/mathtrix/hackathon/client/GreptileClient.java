package org.mathtrix.hackathon.client;

import org.mathtrix.hackathon.constant.APIConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "greptileClient", url = "${GREPTILE_BASE_URL}")
public interface GreptileClient {
    @PostMapping(value = "/query", consumes = MediaType.APPLICATION_JSON_VALUE)
    String queryRepo(
            @RequestHeader(APIConstant.AUTHORIZATION) String authHeader,
            @RequestBody String requestBody
    );

    @PostMapping(value = "/repositories", consumes = MediaType.APPLICATION_JSON_VALUE)
    String indexRepo(
            @RequestHeader(APIConstant.AUTHORIZATION) String authHeader,
            @RequestBody String requestBody
    );

}
