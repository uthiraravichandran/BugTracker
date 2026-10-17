CREATE DATABASE bugtracker;
USE bugtracker;
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) DEFAULT 'tester'
);
CREATE TABLE IF NOT EXISTS projects (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    created_by INT,
    FOREIGN KEY (created_by) REFERENCES users(id)
);
CREATE TABLE IF NOT EXISTS bugs (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    status VARCHAR(20) DEFAULT 'OPEN',
    priority VARCHAR(20) DEFAULT 'MEDIUM',
    project_id INT NOT NULL,
    assigned_to INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (project_id) REFERENCES projects(id),
    FOREIGN KEY (assigned_to) REFERENCES users(id)
);
CREATE TABLE IF NOT EXISTS comments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    bug_id INT NOT NULL,
    user_id INT NOT NULL,
    text VARCHAR(500) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (bug_id) REFERENCES bugs(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
SHOW TABLES;
/*inserting sample data*/
INSERT INTO users (name, email, password, role) VALUES
('Uthir', 'uthir@test.com', 'pass123', 'developer'),
('Priya', 'priya@test.com', 'pass123', 'tester'),
('Arun', 'arun@test.com', 'pass123', 'admin');
/* projects */
INSERT INTO projects (name, description, created_by) VALUES
('Online Shopping App', 'E-commerce website testing', 3),
('Hospital System', 'Patient management module', 3);
/*Bugs*/
INSERT INTO bugs (title, description, status, priority, project_id, assigned_to) VALUES
('Login button not working', 'Button does nothing on click', 'OPEN', 'HIGH', 1, 1),
('Wrong total price', 'Cart total is incorrect with discount', 'IN_PROGRESS', 'HIGH', 1, 1),
('Typo in homepage', 'Welcome spelled wrong', 'OPEN', 'LOW', 1, 2),
('Patient search slow', 'Search takes 10 seconds', 'OPEN', 'MEDIUM', 2, 1),
('Report not downloading', 'PDF download fails', 'CLOSED', 'MEDIUM', 2, 2);
/* Comments*/
INSERT INTO comments (bug_id, user_id, text) VALUES
(1, 2, 'Reproduced on Chrome'),
(1, 1, 'Looking into it'),
(2, 1, 'Fix in progress');
SELECT * FROM bugs;
/* practice */
SELECT * FROM bugs WHERE status = 'OPEN'; /* Ellam OPEN bugs:*/
SELECT title, priority FROM bugs WHERE priority = 'HIGH'; /*HIGH priority bugs (title + priority mattum)*/
SELECT bugs.title, users.name AS assigned_to
FROM bugs
JOIN users ON bugs.assigned_to = users.id;/*Bug yaarukku assign (JOIN)*/
SELECT projects.name, COUNT(bugs.id) AS total_bugs
FROM projects
JOIN bugs ON projects.id = bugs.project_id
GROUP BY projects.name;/*Project wise bug count*/
SELECT status, COUNT(*) AS count
FROM bugs
GROUP BY status;/* Status wise count*/
UPDATE bugs SET status = 'CLOSED' WHERE id = 1;/* Bug status update*/
SELECT bugs.title, bugs.status
FROM bugs
JOIN users ON bugs.assigned_to = users.id
WHERE users.name = 'Priya';