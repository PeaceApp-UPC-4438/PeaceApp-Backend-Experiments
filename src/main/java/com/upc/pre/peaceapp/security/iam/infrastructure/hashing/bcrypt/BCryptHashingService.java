package com.upc.pre.peaceapp.security.iam.infrastructure.hashing.bcrypt;

import com.upc.pre.peaceapp.security.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface BCryptHashingService extends HashingService, PasswordEncoder {

}