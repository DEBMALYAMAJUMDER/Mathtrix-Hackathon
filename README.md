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

## 4. Project Architecture
1. The seven-layer architectural model and how components are organized
2. Component interaction patterns and data flow between layers
3. Integration architecture with external services (Greptile API, GitHub)
4. Validation architecture and defensive programming strategies
5. Key design patterns employed throughout the codebase

Layered Architecture
The application follows a seven-layer architecture that enforces strict separation of concerns. Each layer has a specific responsibility and communicates only with adjacent layers.

Diagram:
<img width="1185" height="453" alt="image" src="https://github.com/user-attachments/assets/5bfebfbb-abc5-4271-a690-d42041888379" />


