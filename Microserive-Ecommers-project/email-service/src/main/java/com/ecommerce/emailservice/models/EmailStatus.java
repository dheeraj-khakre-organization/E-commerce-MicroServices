package com.ecommerce.emailservice.models;
public enum EmailStatus {
    SENT,           // Successfully delivered
    FAILED,         // Delivery failed
    RETRY_PENDING   // Scheduled for retry
}