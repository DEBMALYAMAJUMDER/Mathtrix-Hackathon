package org.mathtrix.hackathon.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepoQueryEntity {
    @Valid
    @Size(min = 1, message = "You must provide atleast 1 repository")
    private List<RepoIndexEntity> repoEntities;
    @NotBlank(message = "Please Provide a Valid Query")
    private String Query;

}
