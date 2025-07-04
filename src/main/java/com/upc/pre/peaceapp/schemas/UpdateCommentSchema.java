package com.upc.pre.peaceapp.schemas;

public record UpdateCommentSchema(String content) {
    public String getContent() { return content; }
}
