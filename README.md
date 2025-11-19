## 1. Project Overview

I created this repository to provide a backend service for automated code review and repository indexing using external APIs. The application exposes endpoints to index GitHub repositories and process code review queries, returning concise review messages. Perfect for developers and teams who need to automate code review workflows and manage repository indexing efficiently.

## 2. Tech Stack

Built with Spring Boot, Java

## 3. Installation & Setup

**Prerequisites**  
- Java  
- Docker  
- (Optional) Spring Boot CLI  

**Quick Start**  
1. Clone the repository  
2. Build the project  
   $ ./mvnw clean install  
3. Run the application  
   $ java -jar target/*.jar  
4. Example environment setup:  
   $ export GREPTILE_API_KEY=your_api_key

## 4. Project Structure

/src/main/java/org/mathtrix/hackathon/HackathonApplication.java: Main application entry point  
/src/main/java/org/mathtrix/hackathon/client/GreptileClient.java: Feign client for external API calls  
/src/main/java/org/mathtrix/hackathon/config/CrosConfig.java: CORS configuration for allowed origins  
/src/main/java/org/mathtrix/hackathon/constant/APIConstant.java: API-related constants  
/src/main/java/org/mathtrix/hackathon/controller/CodeReviewController.java: REST controller for code review endpoints  
/src/main/java/org/mathtrix/hackathon/entity/BranchName.java: Enum for branch names  
/src/main/java/org/mathtrix/hackathon/entity/RepoIndexEntity.java: Model for repository indexing requests  
/src/main/java/org/mathtrix/hackathon/entity/RepoQueryEntity.java: Model for repository query requests  
/src/main/java/org/mathtrix/hackathon/exception/GreptileServerException.java: Custom exception for API errors  
/src/main/java/org/mathtrix/hackathon/service/CodeReviewService.java: Interface for code review services  
/src/main/java/org/mathtrix/hackathon/service/impl/GreptileCodeReviewServiceImpl.java: Implementation of code review service  
/src/main/java/org/mathtrix/hackathon/util/MessageBodyUtil.java: Utility for request message formatting  
/src/main/java/org/mathtrix/hackathon/util/ResponseUtil.java: Utility for response formatting and saving  
/src/main/java/org/mathtrix/hackathon/validation/ValidateBranch.java: Annotation for branch validation  
/src/main/java/org/mathtrix/hackathon/validation/ValidateUrl.java: Annotation for URL validation  
/src/main/java/org/mathtrix/hackathon/validation/validator/BranchValidator.java: Validator for branch names  
/src/main/java/org/mathtrix/hackathon/validation/validator/GitUrlValidator.java: Validator for Git repository URLs  
/src/main/resources/application.properties: Application configuration properties  

## 5. License

License: Not currently specified