# University Information System

A console-based university management system written in Java as the final project for the **Object-Oriented Programming and Design** course (KBTU, Spring 2026). Built as a team project.

Students, teachers, managers, admins and finance managers each log in to their own menu. Data persists between runs through Java serialization.

## Features

| Role | What they can do |
|---|---|
| **Student** | Register for and drop courses, view attestation marks (ATT1 + ATT2 + Final), transcript with GPA, attendance, debts, rate teachers, request to become a researcher |
| **Teacher** | View courses and students, put lesson marks, mark attendance, publish research papers (professors), check salary, messaging |
| **Manager** | Students sorted by GPA, add courses, assign teachers, approve course and researcher requests, assign research supervisors, expel students after more than 3 retakes, manage news, reports |
| **Admin** | Add and remove users, view system logs, generate reports |
| **Finance Manager** | Manage the budget, pay salaries, check student debts |

## OOP concepts and design patterns

- **Inheritance and abstraction**: `User` → `Employee` → `Teacher`, `Manager`, `Admin`, `FinanceManager`; `User` → `Student`
- **Interfaces**: `Messageable`, `Researcher`, `Comparable<ResearchPaper>`
- **Singleton**: `Database.getInstance()`, the single shared data store
- **Factory**: `UserFactory.createUser(...)` creates users by role
- **Decorator**: `ResearcherDecorator` turns any user into a researcher with papers, projects and an h-index
- **Custom exceptions**: `CreditLimitException`, `MaxFailsException`, `LowHIndexException`, `NotResearcherException`
- **Serialization**: the whole database is saved to `database.ser` on exit

## Project structure

```
src/
├── Main.java          # entry point, console menus for each role
├── Users/             # User, Employee, Student, Teacher, Manager, Admin, FinanceManager
├── Utils/             # Database, AuthService, Course, Lesson, Mark, Transcript, News, Logger...
├── Researching/       # Researcher, ResearcherDecorator, ResearchPaper, ResearchProject
├── Enums/             # Faculty, Term, TeacherTitle, LessonType, ...
└── Exceptions/        # custom checked exceptions
```

## How to run

Requires JDK 17 or newer.

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out Main
```

Or open the folder in Eclipse (project files are included) and run `Main`.

### Demo accounts

| Role | Username | Password |
|---|---|---|
| Admin | `admin` | `admin123` |
| Teacher | `teacher1` | `pass123` |
| Professor | `prof1` | `pass123` |
| Student | `student1` … `student6` | `pass123` |
| Manager | `manager1` | `pass123` |
| Finance Manager | `finance1` | `pass123` |
