package com.upc.pre.peaceapp.repositories;

import com.upc.pre.peaceapp.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT * FROM comments WHERE report_id = ?1 ORDER BY creation_date ASC", nativeQuery = true)
    List<Comment> findByReportId(Long reportId);
}
