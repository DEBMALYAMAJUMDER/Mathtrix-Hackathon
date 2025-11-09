package org.mathtrix.hackathon.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mathtrix.hackathon.validation.ValidateBranch;
import org.mathtrix.hackathon.validation.ValidateUrl;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepoIndexEntity {
    @ValidateUrl(message = "Please provide a valid git hub url.Ex:https://github.com/DEBMALYAMAJUMDER/Mathtrix-Hackathon")
    private String githubUrl;
    @ValidateBranch
    private String branch;
    private String project;
}
