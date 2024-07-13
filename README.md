1. Number of Apps Involved:
There are 2 main applications involved:
- Student App
- Lecturer App

2. Brief Explanation of Each App:

a) Student App:
- A Java Swing-based desktop application for students.
- Allows students to log in and view their course scores.
- Communicates with a PHP web service to authenticate and retrieve data.

b) Lecturer App:
- A Java Swing-based desktop application for lecturers.
- Enables lecturers to log in and view information about students in their courses.
- Communicates with a PHP web service for authentication and data retrieval.

3. Architecture/Layer Diagram:

For both Student and Lecturer Apps:

//pics here

Client Layer: Java Swing GUI (StudentApp.java / LecturerApp.java)
Middleware Layer: PHP Web Services (StudentWebService.php / LecturerWebService.php)
Data Layer: MySQL Database (accessed via RedmineDB.php)

4. URL Endpoints (RESTful):

Student Web Service:
- Login: http://localhost/DADProject/StudentWebService.php?action=login&studentId=[ID]&password=[PASSWORD]
- Get Scores: http://localhost/DADProject/StudentWebService.php?action=getScoresByStudentId&studentId=[ID]

Lecturer Web Service:
- Login: http://localhost/DADProject/LecturerWebService.php?action=login&lecturer_id=[ID]&password=[PASSWORD]
- Get Students Data: http://localhost/DADProject/LecturerWebService.php?action=getStudentsData&lecturerId=[ID]

*change respective bracket
[ID] -> INSERT ID (STUDENT/LECTURER)
[PASSWORD] -> INSERT PASSWORD.

5. Functions/Features in the Middleware:

StudentWebService.php:
- login(): Authenticates student credentials
- getScoresByStudentId(): Retrieves course scores for a specific student

LecturerWebService.php:
- login(): Authenticates lecturer credentials
- getStudentsDataByLecturer(): Retrieves student data for courses taught by a specific lecturer

6. Database and Tables:

Database: dadprojectdb

Tables (inferred from the code):
- students: Stores student information (student_id, name, email, password)
- lecturers: Stores lecturer information (lecturer_id, password, potentially name and other details)
- courses: Stores course information (course_id, course_name, lecturer_id)
- results: Stores student scores (student_id, course_id, score)
