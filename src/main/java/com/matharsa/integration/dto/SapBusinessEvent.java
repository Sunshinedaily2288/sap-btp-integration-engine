package com.matharsa.integration.dto;

import java.time.Instant;

/**
 * Immutable schema blueprint representing a business event payload
 * dispatched from the SAP BTP eventing mesh architecture.
 */
public record SapBusinessEvent(
        String eventId,
        String entityType,
        String actionType,
        Instant timestamp,
        String triggeredBy
) {}
