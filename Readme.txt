Dropbox-like Service
Overview
This project implements a simplified Dropbox-like service with the ability to upload, retrieve, update, delete, and list files through RESTful APIs. Metadata for each file is stored in an H2 in-memory database.Used h2 database

Features
Upload File API

Endpoint: POST /files/upload
Description: Upload a file to the server.
Request Body: Multipart form-data with the file.
Response: A JSON object containing the file metadata.
Read File API

Endpoint: GET /files/{fileId}
Description: Retrieve a file based on its unique identifier.
Path Parameter: fileId - The ID of the file to retrieve.
Response: The binary data of the requested file.
Update File API

Endpoint: PUT /files/{fileId}
Description: Update an existing file or its metadata.
Path Parameter: fileId - The ID of the file to update.
Request Body: Multipart form-data with the new file.
Response: Updated file metadata.
Delete File API

Endpoint: DELETE /files/{fileId}
Description: Delete a specific file based on its unique identifier.
Path Parameter: fileId - The ID of the file to delete.
Response: Success or failure message.
List Files API

Endpoint: GET /files
Description: List all files and their metadata.
Response: A JSON array of file metadata objects.
Technologies Used
Backend: Spring Boot (Java)
Database: H2 (In-memory)
Build Tool: Maven
Setup and Running
Clone the Repository:

bash
Copy code
git clone <repository-url>
Navigate to the Project Directory:

bash
Copy code
cd dropbox-like-service
Build and Run the Application:

bash
Copy code
./mvnw spring-boot:run
Access H2 Console:

URL: http://localhost:8080/h2-console
JDBC URL :  jdbc:h2:file:C:/Users/Dheer Varyani/testdb
User Name: sa
Password: password
Testing APIs with Postman
Here are the details for the Postman requests:

1. Upload File API
URL: http://localhost:8080/files/upload
Type: POST
Headers: Content-Type: multipart/form-data
Body: Form-data with a file key.
2. Read File API
URL: http://localhost:8080/files/{fileId}
Type: GET
Path Parameter: fileId
3. Update File API
URL: http://localhost:8080/files/{fileId}
Type: PUT
Headers: Content-Type: multipart/form-data
Body: Form-data with a file key.
Path Parameter: fileId
4. Delete File API
URL: http://localhost:8080/files/{fileId}
Type: DELETE
Path Parameter: fileId
5. List Files API
URL: http://localhost:8080/files
Type: GET
Notes
Ensure that the H2 console is enabled in the application.properties for viewing database contents.
The H2 in-memory database will reset on application restart, so data will not persist.
License


