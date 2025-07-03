package com.upc.pre.peaceapp.services;

import com.upc.pre.peaceapp.models.Comment;
import com.upc.pre.peaceapp.repositories.CommentRepository;
import com.upc.pre.peaceapp.schemas.CommentSchema;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public Comment createComment(CommentSchema schema) {
        Comment comment = new Comment(schema.reportId(), schema.userId(), schema.content());
        comment.setUpdateDate(null);
        return repository.save(comment);
    }

    public List<Comment> getCommentsByReportId(Long reportId) {
        return repository.findByReportId(reportId);
    }

    public Comment updateComment(Long id, String content) {
        Comment comment = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        comment.setContent(content);
        comment.setUpdateDate(LocalDateTime.now());
        return repository.save(comment);
    }

    public void deleteComment(Long id) {
        repository.deleteById(id);
    }
}
