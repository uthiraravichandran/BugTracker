# Bug Tracker System

A console application to track software bugs, built with Java, JDBC and MySQL.
I built it to learn full-cycle development: database design, SQL, and Java database connectivity.

## Features

- View all bugs
- Add a new bug (title and priority)
- Update bug status (OPEN, IN_PROGRESS, CLOSED)
- Delete a bug
- Input validation for menu choices, priority and status

## Tech Stack

- Java 17
- MySQL 8
- JDBC (MySQL Connector/J)
- VS Code

## Database Design

Four related tables:

| Table | Purpose |
|---|---|
| `users` | Developers, testers and admins |
| `projects` | Projects that bugs belong to |
| `bugs` | Bug title, status, priority, project and assignee |
| `comments` | Comments on bugs |

Foreign keys connect bugs to projects and users, and comments to bugs and users.
The full schema and sample data are in `database/bugtracker_schema.sql`.

## How to Run

1. Install JDK 17 and MySQL 8.
2. Run `database/bugtracker_schema.sql` in MySQL Workbench.
3. Download MySQL Connector/J from the MySQL website and place the `.jar` file in a `lib` folder.
4. Copy `db.properties.example` to `db.properties` and enter your MySQL password.
5. Compile and run:

```
javac -cp "lib/*" -d bin src/Main.java
java -cp "bin;lib/*" Main
```

(On Mac or Linux, use `:` instead of `;` in the classpath.)

## Sample Output

```
===== BUG TRACKER =====
1. View all bugs
2. Add bug
3. Update bug status
4. Delete bug
5. Exit
Enter your choice: 1

ID  | Title                     | Status      | Priority
1   | Login button not working  | CLOSED      | HIGH
2   | Wrong total price         | IN_PROGRESS | HIGH
3   | Typo in homepage          | OPEN        | LOW
```

## What I Learned

- Designing a relational database with primary and foreign keys
- Writing SQL with JOIN, GROUP BY and aggregate functions
- Connecting Java to MySQL using JDBC and PreparedStatement
- Keeping credentials out of source code with a properties file and `.gitignore`

## Roadmap

- [x] Phase 1: Java, MySQL and JDBC console app
- [ ] Phase 2: Spring Boot REST API and web UI
- [ ] Phase 3: Automated tests (JUnit, Postman, Selenium) and CI with GitHub Actions