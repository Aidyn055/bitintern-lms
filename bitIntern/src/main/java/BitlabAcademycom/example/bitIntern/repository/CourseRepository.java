package BitlabAcademycom.example.bitIntern.repository;

import BitlabAcademycom.example.bitIntern.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
