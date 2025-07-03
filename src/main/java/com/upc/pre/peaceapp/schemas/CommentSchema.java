package com.upc.pre.peaceapp.schemas;

public record CommentSchema(
        Long reportId,
        String userId,
        String content
) {
    public CommentSchema {
        if (reportId == null || reportId <= 0) {
            throw new IllegalArgumentException("Report ID must be valid.");
        }
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID cannot be null or empty.");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be null or empty.");
        }
    }
}
