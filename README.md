
# Frontend CLI
## Overview
The CLI acts as a lightweight tool for local fishers and customers to interact with the FirstCatch ecosystem. It currently only supports searches, but, once finished, fishers will be able to manage their listings, and customers will be able to browse and reserve fish freshly caught from local waters, ensuring a transparent and sustainable seafood supply chain.

## Technical Details
A Java console-based client application that communicates with the firstcatch-api backend to provide a simple text interface for customers, fishers, and admins. Supports currently include viewing available catches, placing orders, and managing profiles.

### Build Structure

### How to Run
1. Run backend server (available here: https://github.com/FIrstCatchCrew/restapi)
1. Navigate to the CLI directory: ```cd firstcatch-cli```
1. Build the project: ```mvn clean install```
1. Run the application: ```java -jar target/firstcatch-cli.jar```
1. Follow on-screen prompts to interact with the backend

***Note:*** make sure backend server is running and accessible at the configured URL in the CLI's RESTClient.java.


## Project Requirements

### Unit tests

### Github Actions

### 4 queries
