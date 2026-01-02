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
INSERT INTO permissions (id, account_id, name, description) VALUES
('550e8400-e29b-41d4-a716-446655440011', '550e8400-e29b-41d4-a716-446655440001', 'READ', 'Read-only access to AWS resources'),
('550e8400-e29b-41d4-a716-446655440012', '550e8400-e29b-41d4-a716-446655440001', 'WRITE', 'Read-write access to AWS resources'),
('550e8400-e29b-41d4-a716-446655440013', '550e8400-e29b-41d4-a716-446655440001', 'ADMIN', 'Administrative access to AWS resources'),
('550e8400-e29b-41d4-a716-446655440014', '550e8400-e29b-41d4-a716-446655440003', 'READ', 'Read-only access to GitHub repositories'),
('550e8400-e29b-41d4-a716-446655440015', '550e8400-e29b-41d4-a716-446655440003', 'WRITE', 'Read-write access to GitHub repositories'),
('550e8400-e29b-41d4-a716-446655440016', '550e8400-e29b-41d4-a716-446655440003', 'ADMIN', 'Administrative access to GitHub organization'),
('550e8400-e29b-41d4-a716-446655440017', '550e8400-e29b-41d4-a716-446655440005', 'READ', 'Read-only access to StackSpot resources'),
('550e8400-e29b-41d4-a716-446655440018', '550e8400-e29b-41d4-a716-446655440005', 'WRITE', 'Read-write access to StackSpot resources'),
('550e8400-e29b-41d4-a716-446655440019', '550e8400-e29b-41d4-a716-446655440005', 'ADMIN', 'Administrative access to StackSpot organization');

-- Insert sample teams
INSERT INTO teams (id, name, description) VALUES
('550e8400-e29b-41d4-a716-446655440031', 'DevOps Team', 'Team responsible for infrastructure and deployments'),
('550e8400-e29b-41d4-a716-446655440032', 'Backend Team', 'Team handling server-side development'),
('550e8400-e29b-41d4-a716-446655440033', 'Frontend Team', 'Team handling client-side development');

-- Insert team accounts
INSERT INTO team_accounts (team_id, account_id) VALUES
('550e8400-e29b-41d4-a716-446655440031', '550e8400-e29b-41d4-a716-446655440001'), -- DevOps - AWS Prod
('550e8400-e29b-41d4-a716-446655440031', '550e8400-e29b-41d4-a716-446655440002'), -- DevOps - AWS Dev
('550e8400-e29b-41d4-a716-446655440032', '550e8400-e29b-41d4-a716-446655440003'), -- Backend - GitHub
('550e8400-e29b-41d4-a716-446655440033', '550e8400-e29b-41d4-a716-446655440003'); -- Frontend - GitHub

-- Insert team permissions
INSERT INTO team_permissions (team_id, permission_id) VALUES
('550e8400-e29b-41d4-a716-446655440031', '550e8400-e29b-41d4-a716-446655440011'), -- DevOps - AWS Read
('550e8400-e29b-41d4-a716-446655440031', '550e8400-e29b-41d4-a716-446655440012'), -- DevOps - AWS Write
('550e8400-e29b-41d4-a716-446655440032', '550e8400-e29b-41d4-a716-446655440014'), -- Backend - GitHub Read
('550e8400-e29b-41d4-a716-446655440032', '550e8400-e29b-41d4-a716-446655440015'), -- Backend - GitHub Write
('550e8400-e29b-41d4-a716-446655440033', '550e8400-e29b-41d4-a716-446655440014'); -- Frontend - GitHub Read

-- Insert sample users
INSERT INTO users (id, name, company_email, github_account) VALUES
('550e8400-e29b-41d4-a716-446655440021', 'John Doe', 'john.doe@company.com', 'johndoe'),
('550e8400-e29b-41d4-a716-446655440022', 'Jane Smith', 'jane.smith@company.com', 'janesmith'),
('550e8400-e29b-41d4-a716-446655440023', 'Bob Johnson', 'bob.johnson@company.com', 'bobjohnson');

-- Insert user teams
INSERT INTO user_teams (user_id, team_id) VALUES
('550e8400-e29b-41d4-a716-446655440021', '550e8400-e29b-41d4-a716-446655440031'), -- John - DevOps
('550e8400-e29b-41d4-a716-446655440021', '550e8400-e29b-41d4-a716-446655440032'), -- John - Backend
('550e8400-e29b-41d4-a716-446655440022', '550e8400-e29b-41d4-a716-446655440033'), -- Jane - Frontend
('550e8400-e29b-41d4-a716-446655440023', '550e8400-e29b-41d4-a716-446655440031'); -- Bob - DevOps