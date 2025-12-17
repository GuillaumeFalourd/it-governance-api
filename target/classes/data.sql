-- Test data for IT Governance API
-- This script populates the database with sample data for testing

-- Insert sample accounts
INSERT INTO accounts (id, type, identifier, description) VALUES
('550e8400-e29b-41d4-a716-446655440001', 'AWS', '123456789012', 'Production AWS Account'),
('550e8400-e29b-41d4-a716-446655440002', 'AWS', '987654321098', 'Development AWS Account'),
('550e8400-e29b-41d4-a716-446655440003', 'GITHUB', 'my-org', 'Main GitHub Organization'),
('550e8400-e29b-41d4-a716-446655440004', 'GITHUB', 'another-org', 'Secondary GitHub Organization'),
('550e8400-e29b-41d4-a716-446655440005', 'STACKSPOT', 'stackspot-org', 'StackSpot Organization');

-- Insert sample permissions
INSERT INTO permissions (id, account_type, name, description) VALUES
('550e8400-e29b-41d4-a716-446655440011', 'AWS', 'READ', 'Read-only access to AWS resources'),
('550e8400-e29b-41d4-a716-446655440012', 'AWS', 'WRITE', 'Read-write access to AWS resources'),
('550e8400-e29b-41d4-a716-446655440013', 'AWS', 'ADMIN', 'Administrative access to AWS resources'),
('550e8400-e29b-41d4-a716-446655440014', 'GITHUB', 'READ', 'Read-only access to GitHub repositories'),
('550e8400-e29b-41d4-a716-446655440015', 'GITHUB', 'WRITE', 'Read-write access to GitHub repositories'),
('550e8400-e29b-41d4-a716-446655440016', 'GITHUB', 'ADMIN', 'Administrative access to GitHub organization'),
('550e8400-e29b-41d4-a716-446655440017', 'STACKSPOT', 'READ', 'Read-only access to StackSpot resources'),
('550e8400-e29b-41d4-a716-446655440018', 'STACKSPOT', 'WRITE', 'Read-write access to StackSpot resources'),
('550e8400-e29b-41d4-a716-446655440019', 'STACKSPOT', 'ADMIN', 'Administrative access to StackSpot organization');

-- Insert sample users
INSERT INTO users (id, name, company_email, github_account) VALUES
('550e8400-e29b-41d4-a716-446655440021', 'John Doe', 'john.doe@company.com', 'johndoe'),
('550e8400-e29b-41d4-a716-446655440022', 'Jane Smith', 'jane.smith@company.com', 'janesmith'),
('550e8400-e29b-41d4-a716-446655440023', 'Bob Johnson', 'bob.johnson@company.com', 'bobjohnson');

-- Insert user GitHub organizations
INSERT INTO user_github_organizations (user_id, organization) VALUES
('550e8400-e29b-41d4-a716-446655440021', 'my-org'),
('550e8400-e29b-41d4-a716-446655440021', 'another-org'),
('550e8400-e29b-41d4-a716-446655440022', 'my-org'),
('550e8400-e29b-41d4-a716-446655440023', 'another-org');

-- Insert user GitHub teams
INSERT INTO user_github_teams (user_id, organization, teams) VALUES
('550e8400-e29b-41d4-a716-446655440021', 'my-org', ARRAY['backend-team', 'devops-team']),
('550e8400-e29b-41d4-a716-446655440021', 'another-org', ARRAY['security-team']),
('550e8400-e29b-41d4-a716-446655440022', 'my-org', ARRAY['frontend-team']),
('550e8400-e29b-41d4-a716-446655440023', 'another-org', ARRAY['qa-team']);

-- Insert user AWS organization units
INSERT INTO user_aws_organization_units (user_id, unit) VALUES
('550e8400-e29b-41d4-a716-446655440021', 'Production-OU'),
('550e8400-e29b-41d4-a716-446655440021', 'Development-OU'),
('550e8400-e29b-41d4-a716-446655440022', 'Development-OU'),
('550e8400-e29b-41d4-a716-446655440023', 'Production-OU');

-- Insert user AWS accounts
INSERT INTO user_aws_accounts (user_id, account) VALUES
('550e8400-e29b-41d4-a716-446655440021', '123456789012'),
('550e8400-e29b-41d4-a716-446655440021', '987654321098'),
('550e8400-e29b-41d4-a716-446655440022', '987654321098'),
('550e8400-e29b-41d4-a716-446655440023', '123456789012');

-- Insert user AWS roles
INSERT INTO user_aws_roles (user_id, account, roles) VALUES
('550e8400-e29b-41d4-a716-446655440021', '123456789012', ARRAY['Developer', 'DevOps']),
('550e8400-e29b-41d4-a716-446655440021', '987654321098', ARRAY['Developer']),
('550e8400-e29b-41d4-a716-446655440022', '987654321098', ARRAY['Developer']),
('550e8400-e29b-41d4-a716-446655440023', '123456789012', ARRAY['Admin']);