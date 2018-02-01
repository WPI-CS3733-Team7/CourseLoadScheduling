SELECT DISTINCT * FROM course_sections
WHERE calendar_info_id IN
(
	SELECT id FROM calendar_info
	WHERE cal_year = :year AND cal_term = :term
)
AND instructor_id = :instructorId