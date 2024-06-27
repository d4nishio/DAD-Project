<?php
require_once 'RedmineDB.php';

class LecturerWebService {
    private $db;

    public function __construct() {
        $this->db = new RedmineDB();
    }

    public function login($lecturerId, $password) {
        $query = "SELECT * FROM lecturers WHERE lecturer_id = ? AND password = ?";
        $result = $this->db->executeQuery($query, [$lecturerId, $password]);
        return !empty($result) ? $result[0] : null;
    }

    public function getStudentsDataByLecturer($lecturerId) {
        $query = "SELECT s.*, r.course_id, r.score 
                  FROM students s 
                  LEFT JOIN results r ON s.student_id = r.student_id 
                  LEFT JOIN courses c ON r.course_id = c.course_id
                  WHERE c.lecturer_id = ?";
        return $this->db->executeQuery($query, [$lecturerId]);
    }
}

$service = new LecturerWebService();

if ($_SERVER["REQUEST_METHOD"] == 'GET' && isset($_GET['action'])) {
    if ($_GET['action'] == 'login') {
        $lecturerId = $_GET['lecturer_id'];
        $password = $_GET['password'];
        $lecturer = $service->login($lecturerId, $password);
        if ($lecturer) {
            echo json_encode(['success' => true, 'lecturer' => $lecturer]);
        } else {
            echo json_encode(['success' => false, 'message' => 'Invalid credentials']);
        }
    } elseif ($_GET['action'] == 'getStudentsData' && isset($_GET['lecturerId'])) {
        $lecturerId = $_GET['lecturerId'];
        $studentsData = $service->getStudentsDataByLecturer($lecturerId);
        if ($studentsData) {
			echo json_encode($studentsData); 
		} else {
			http_response_code(404);
			echo json_encode(['message' => 'Scores not found for the student']);
		};
    } else {
        http_response_code(400);
        echo json_encode(['message' => 'Bad Request']);
    }
} else {
    http_response_code(404);
    echo json_encode(['message' => 'Not Found']);
}
?>
