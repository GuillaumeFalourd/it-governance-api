# IT Governance API - Postman Collection

This folder contains Postman files for testing all endpoints of the IT Governance API.

## Files Structure

```
postman/
├── IT-Governance-API.postman_collection.json          # Main collection file
├── IT-Governance-API.postman_environment.json         # Environment variables
├── Accounts/                                          # Account-related requests
│   ├── Get-All-Accounts.postman_request.json
│   ├── Get-Account-by-ID.postman_request.json
│   ├── Create-Account.postman_request.json
│   ├── Update-Account.postman_request.json
│   └── Delete-Account.postman_request.json
├── Permissions/                                       # Permission-related requests
│   ├── Get-All-Permissions.postman_request.json
│   ├── Get-Permission-by-ID.postman_request.json
│   ├── Get-Permissions-by-Account-Type.postman_request.json
│   ├── Create-Permission.postman_request.json
│   ├── Update-Permission.postman_request.json
│   └── Delete-Permission.postman_request.json
└── Users/                                             # User-related requests
    ├── Get-All-Users.postman_request.json
    ├── Get-User-by-ID.postman_request.json
    ├── Create-User.postman_request.json
    ├── Update-User.postman_request.json
    └── Delete-User.postman_request.json
```

## How to Use

1. **Import the Collection:**
   - Open Postman
   - Click "Import" button
   - Select "File" tab
   - Choose `IT-Governance-API.postman_collection.json`

2. **Import the Environment:**
   - In Postman, click "Import" again
   - Select `IT-Governance-API.postman_environment.json`
   - Make sure the environment is selected in the top-right dropdown

3. **Start the Application:**
   - Make sure your Spring Boot application is running on `http://localhost:8080`
   - The application will automatically load test data from `data.sql`

4. **Test the Endpoints:**
   - All requests are pre-configured with appropriate URLs and sample data
   - Variables like `{{accountId}}`, `{{userId}}`, etc. are set with sample UUIDs from the test data

## Available Endpoints

### Accounts (`/accounts`)
- `GET /accounts` - Get all accounts
- `GET /accounts/{id}` - Get account by ID
- `POST /accounts` - Create a new account
- `PUT /accounts/{id}` - Update an existing account
- `DELETE /accounts/{id}` - Delete an account

### Permissions (`/permissions`)
- `GET /permissions` - Get all permissions
- `GET /permissions/{id}` - Get permission by ID
- `GET /permissions/account-type/{accountType}` - Get permissions by account type (AWS, GITHUB, STACKSPOT)
- `POST /permissions` - Create a new permission
- `PUT /permissions/{id}` - Update an existing permission
- `DELETE /permissions/{id}` - Delete a permission

### Users (`/users`)
- `GET /users` - Get all users
- `GET /users/{id}` - Get user by ID
- `POST /users` - Create a new user
- `PUT /users/{id}` - Update an existing user
- `DELETE /users/{id}` - Delete a user

## Sample Data

The application loads test data automatically, including:
- 5 sample accounts (AWS, GitHub, StackSpot)
- 9 sample permissions (3 for each account type)
- 3 sample users with complex relationships (GitHub orgs/teams, AWS accounts/roles)

## Environment Variables

- `baseUrl`: API base URL (default: http://localhost:8080)
- `accountId`: Sample account UUID
- `permissionId`: Sample permission UUID
- `userId`: Sample user UUID
- `accountType`: Account type for filtering (AWS, GITHUB, STACKSPOT)

## Request Examples

### Create Account
```json
{
  "type": "AWS",
  "identifier": "123456789012",
  "description": "Production AWS Account"
}
```

### Create User (Complex)
```json
{
  "name": "John Doe",
  "companyEmail": "john.doe@company.com",
  "githubAccount": "johndoe",
  "githubOrganizations": ["my-org"],
  "githubTeamsPerOrganization": {
    "my-org": ["backend-team", "devops-team"]
  },
  "awsOrganizationUnits": ["Production-OU"],
  "awsAccounts": ["123456789012"],
  "awsRolesPerAccount": {
    "123456789012": ["Developer", "DevOps"]
  }
}
```