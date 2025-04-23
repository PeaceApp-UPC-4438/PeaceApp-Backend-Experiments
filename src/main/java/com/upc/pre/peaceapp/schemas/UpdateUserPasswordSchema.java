package com.upc.pre.peaceapp.schemas;

public record UpdateUserPasswordSchema(
        String password
) {
    public String getPassword() { return password; }
}