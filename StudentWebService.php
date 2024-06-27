<?php
require_once 'RedmineDB.php';

class StudentWebService {
    private $db;

    public function __construct() {
        $this->db = new RedmineDb();
    }

	public function login($studentId, $password) {
        $query = "SELECT * FROM students WHERE student_id = ? AND password = ?";
        $result = $this->db->executeQuery($query, [$studentId, $password]);
        return !empty($result) ? $result[0] : null;
    }
	
    public function getScoresByStudentId($studentId) {
        $query = "SELECT c.course_id, c.course_name, r.score FROM courses c 
	              INNER JOIN results r ON c.course_id = r.course_id
	              WHERE r.student_id = ?";
        return $this->db->executeQuery($query, [$studentId]);
    }
}

$service = new StudentWebService();

if ($_SERVER["REQUEST_METHOD"] == 'GET' && isset($_GET['action'])) {
    if ($_GET['action'] == 'login') {
        $studentId = $_GET['studentId'];
        $password = $_GET['password'];
        $student = $service->login($studentId, $password);
        if ($student) {
            echo json_encode(['success' => true, 'student' => $student]);
        } else {
            echo json_encode(['success' => false, 'message' => 'Invalid credentials']);
        }
    } elseif ($_GET['action'] == 'getScoresByStudentId') {
        $studentId = $_GET['studentId'];
		$scores = $service->getScoresByStudentId($studentId);

		// Check if scores are found
		if ($scores) {
			echo json_encode($scores); // Return scores as JSON
		} else {
			http_response_code(404);
			echo json_encode(['message' => 'Scores not found for the student']);
		}
	} 
} else {
    http_response_code(404);
    echo json_encode(['message' => 'Not Found']);
}
?>