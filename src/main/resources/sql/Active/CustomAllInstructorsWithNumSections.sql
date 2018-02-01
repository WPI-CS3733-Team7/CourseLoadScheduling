SELECT instructor_id, COUNT(id) FROM course_sections
WHERE calendar_info_id IN
(
	SELECT id FROM calendar_info
	WHERE cal_year = :year AND cal_term = :term
)
GROUP BY instructor_id;