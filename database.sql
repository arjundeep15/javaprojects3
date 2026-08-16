- schema.sql
CREATE DATABASE IF NOT EXISTS bugtracker_db;
USE bugtracker_db;

CREATE TABLE IF NOT EXISTS bug_reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50) DEFAULT 'OPEN',     -- OPEN, IN_PROGRESS, RESOLVED
    severity VARCHAR(50) DEFAULT 'LOW',    -- LOW, MEDIUM, HIGH, CRITICAL
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Let's drop a dummy bug in there so your GET request isn't empty
INSERT INTO bug_reports (title, description, status, severity) 
VALUES ('UI Glitch on Login', 'The submit button disappears on mobile screens', 'OPEN', 'HIGH');
