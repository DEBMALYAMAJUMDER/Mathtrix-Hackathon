## 1. Project Overview

Created this repository to provide a backend service for automated code review and repository indexing using Greptile APIs. The application exposes endpoints to index GitHub repositories and process code review queries, returning concise review messages. Perfect for developers and teams who need to automate code review workflows and manage repository indexing efficiently.

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
