package com.matharsa.integration.handler;

import com.matharsa.integration.dto.SapBusinessEvent;
import java.util.Objects;

/**
 * Core transformation and filtering pipeline for business events
 * intercepted from cloud middleware landscapes.
 */
public class SapEventHandler {

    /**
     * Ingests a raw SAP event and evaluates it against strict structural
     * integrity guidelines before execution.
     */
    public boolean validateAndRouteEvent(SapBusinessEvent event) {
        System.out.println("[INTEGRATION ENGINE] Intercepted event envelope: " + event.eventId());

        // 1. Structural Guardrail Validation
        if (Objects.isNull(event.eventId()) || event.eventId().isBlank()) {
            System.err.println("[VALIDATION ERROR] Event rejected: Missing unique event identifiers.");
            return false;
        }

        // 2. Routing Logic (Filtering only specific actions to process)
        if ("CREATE".equalsIgnoreCase(event.actionType())) {
            System.out.println("[ROUTING ENGINE] Forwarding " + event.entityType() + " payload to relational storage pipelines.");
            return true;
        } else {
            System.out.println("[ROUTING ENGINE] Action type " + event.actionType() + " skipped (No processing required).");
            return false;
        }
    }
}
