# File Upload API for Cloudflare R2

A Spring Boot REST API for uploading files (images and PDFs) to Cloudflare R2 storage using S3-compatible API.

## Features

- Upload files up to 10MB
- Support for images (JPEG, PNG, GIF, WebP, SVG) and PDF files
- Automatic file renaming with timestamp and UUID
- Organized storage in date-based folder structure (`uploads/yyyy/MM/`)
- Comprehensive error handling and validation
- Environment variable configuration for security

## Requirements

- Java 17+
- Maven 3.6+
- Cloudflare R2 account with bucket created

## Configuration

Set the following environment variables:

```bash
# Cloudflare R2 Configuration
export CLOUDFLARE_R2_ENDPOINT="https://your-account-id.r2.cloudflarestorage.com"
export CLOUDFLARE_R2_ACCESS_KEY="your-access-key"
export CLOUDFLARE_R2_SECRET_KEY="your-secret-key"
export CLOUDFLARE_R2_BUCKET_NAME="your-bucket-name"
export CLOUDFLARE_R2_REGION="auto"
```

## Running the Application

1. Clone and navigate to the project directory
2. Set the environment variables
3. Run the application:

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoint

### POST /api/upload

Uploads a file to Cloudflare R2 storage.

**Request:**
- Method: POST
- Content-Type: multipart/form-data
- Form field name: `file`

**Response (Success - 200 OK):**
```json
{
  "url": "https://your-account-id.r2.cloudflarestorage.com/your-bucket-name/uploads/2024/03/20240325_123456_abc123def.jpg",
  "fileName": "20240325_123456_abc123def.jpg",
  "size": 1024000
}
```

**Response (Error - 400 Bad Request):**
```json
{
  "message": "File size exceeds maximum limit of 10MB"
}
```

**Response (Error - 500 Internal Server Error):**
```json
{
  "message": "Upload failed: Failed to upload file to R2: Access Denied"
}
```

## Usage Examples

### Using curl

```bash
curl -X POST \
  http://localhost:8080/api/upload \
  -F "file=@/path/to/your/file.jpg"
```

### Using Postman

1. Set method to POST
2. Set URL to `http://localhost:8080/api/upload`
3. In Body tab, select "form-data"
4. Add a key named "file" with type "file"
5. Select your file and send the request

## File Validation

- **Size limit:** 10MB maximum
- **Allowed types:** 
  - `image/jpeg`, `image/jpg`
  - `image/png`
  - `image/gif`
  - `image/webp`
  - `image/svg+xml`
  - `application/pdf`

## File Naming

Uploaded files are automatically renamed using the format:
`{timestamp}_{uuid}{extension}`

Example: `20240325_123456_abc123def456.jpg`

## Storage Structure

Files are stored in Cloudflare R2 with the following structure:
```
uploads/
├── 2024/
│   ├── 01/
│   ├── 02/
│   └── 03/
│       └── 20240325_123456_abc123def.jpg
└── 2025/
    └── 01/
```

## Error Handling

The API handles various error scenarios:

- **Empty file:** Returns 400 with "File cannot be empty"
- **File too large:** Returns 400 with "File size exceeds maximum limit of 10MB"
- **Invalid file type:** Returns 400 with "Invalid file type. Only images and PDF files are allowed"
- **Upload failure:** Returns 500 with appropriate error message
- **Unexpected errors:** Returns 500 with generic error message

## Security

- Access keys are loaded from environment variables (not hardcoded)
- File content types are validated
- File size limits are enforced
- Comprehensive exception handling prevents information leakage

## Dependencies

- Spring Boot 3.2.5
- AWS SDK for Java 2.25.11 (S3)
- Spring Boot Web Starter
- Spring Boot Validation Starter
- Jackson (JSON processing)

## License

This project is open source and available under the MIT License.
