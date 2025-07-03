package com.upc.pre.peaceapp.controllers;

import com.upc.pre.peaceapp.models.Comment;
import com.upc.pre.peaceapp.schemas.CommentSchema;
import com.upc.pre.peaceapp.services.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/comments")
@Tag(name = "Comments", description = "Comments Endpoints")
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity<?> createComment(@RequestBody CommentSchema schema) {
        try {
            Comment newComment = service.createComment(schema);
            URI location = URI.create(String.format("/api/v1/comments/%d", newComment.getId()));
            return ResponseEntity.created(location).body(newComment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/report/{reportId}")
    public ResponseEntity<?> getCommentsByReportId(@PathVariable Long reportId) {
        try {
            List<Comment> comments = service.getCommentsByReportId(reportId);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            String content = request.get("content");
            Comment updated = service.updateComment(id, content);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        try {
            service.deleteComment(id);
            return ResponseEntity.ok(Map.of("message", "Comment deleted"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
