package inu.thebite.toryaba.repository;

import inu.thebite.toryaba.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<Notice> findByStudentIdAndYearAndMonthAndDate(Long id, String year, String month, String date);

    @Query(value = "SELECT notice_date FROM tb_notice WHERE student_seq = :id AND notice_date LIKE CONCAT(:year, '/', :month)", nativeQuery = true)
    List<String> findByStudentIdAndYearAndMonthAndDate(Long id, @Param("year") String year, @Param("month") String month);
}
