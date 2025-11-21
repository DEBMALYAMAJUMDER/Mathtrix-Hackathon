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

## 5. Impact Analysis Test Deliverables

## Change Query: change debitAccount to accNo and set static value CREATE to earmarkType

Based on my analysis of all three repositories, here's the impact assessment of the proposed changes:

## Impact Analysis: Changing debitAccount to accNo and Setting earmarkType to Static Value "CREATE"

### *Most Impacted Repository: debmalyamajumder/go-dummy-hackathon* 

This is the most impacted repository for the following reasons:

### *Impact Details:*

#### *1. Field Name Change: debitAccount → accNo*

*Go-Dummy-Hackathon (HIGHEST IMPACT):*
- *4 files need modification:*
  - models/EarmarkRequest.go - Change struct field and JSON/BSON tags
  - models/EarmarkStatusRequest.go - Change struct field and JSON/BSON tags  
  - controller/transaction_statement.go - Update MongoDB filter query from {"debitAccount": account} to {"accNo": account}
  - models/EarmarkStatus.go (likely needs update for database consistency)

*Balance-Inquiry-Hackathon (MEDIUM IMPACT):*
- *2 files need modification:*
  - app/services.py - Change data.get("debitAccount") to data.get("accNo") in save_transaction()
  - app/models.py - The model uses accountId field, which might need mapping logic updates

*Payment-Initiator-Hackathon (HIGH IMPACT):*
- *2 files need modification:*
  - src/main/java/org/paymentinitiator/processor/entity/TransactionDetails.java - Change field name
  - src/main/java/org/paymentinitiator/processor/entity/StatementRequest.java - Change field name

#### *2. Static Value Change: earmarkType to "CREATE"*

*Go-Dummy-Hackathon (HIGHEST IMPACT):*
- *2 files need modification:*
  - models/EarmarkRequest.go - Field exists and is used throughout
  - controller/create_earmark.go - Logic checks req.RequestType == "CREATE" which might need adjustment if earmarkType becomes static

*Balance-Inquiry-Hackathon (NO IMPACT):*
- No earmarkType field found in the models

*Payment-Initiator-Hackathon (MEDIUM IMPACT):*
- *1 file needs modification:*
  - src/main/java/org/paymentinitiator/processor/entity/TransactionDetails.java - Field exists but usage is limited

### *Why Go-Dummy-Hackathon is Most Impacted:*

1. *Database Dependencies*: It directly queries MongoDB using field names, requiring database schema changes
2. *Business Logic*: The earmarkType field interacts with business logic for CREATE/RELEASE operations
3. *Multiple Model Files*: Both changes affect multiple model files and their corresponding controllers
4. *Integration Points*: It's the core service that likely receives and processes these fields from other services
5. *Data Consistency*: All existing data in MongoDB would need migration for the field name changes

### *Recommended Change Order:*

1. *Start with Go-Dummy-Hackathon* (core service)
2. *Update Payment-Initiator-Hackathon* (upstream service)  
3. *Update Balance-Inquiry-Hackathon* (downstream service)
4. *Coordinate database migration* for existing data

The Go repository appears to be the central earmarking service that the other two repositories integrate with, making it the most critical to update first and thoroughly test
