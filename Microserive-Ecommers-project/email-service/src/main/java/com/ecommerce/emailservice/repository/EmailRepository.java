package com.ecommerce.emailservice.repository;

import com.ecommerce.emailservice.models.EmailLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmailRepository extends MongoRepository<EmailLog,String> {
}
