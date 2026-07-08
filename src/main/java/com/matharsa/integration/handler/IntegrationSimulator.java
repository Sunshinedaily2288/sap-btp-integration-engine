package com.matharsa.integration.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.matharsa.integration.dto.SapBusinessEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class IntegrationSimulator {

    public static void main(String[] args) {
        System.out.println("=== INITIALIZING JSON PARSING SIMULATION ===\n");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        SapEventHandler engineHandler = new SapEventHandler();

        try {
            // Uses your exact all-dots filename configuration
            File targetFile = new File("src/main/resources/sap.event.payload.json");

            if (!targetFile.exists()) {
                System.err.println("[CRITICAL ERROR] File missing at path: " + targetFile.getAbsolutePath());
                return;
            }

            System.out.println("[FILE FOUND] Successfully located payload dataset.");
            InputStream jsonStream = new FileInputStream(targetFile);

            SapBusinessEvent deserializedEvent = mapper.readValue(jsonStream, SapBusinessEvent.class);

            System.out.println("\n[PARSER SUCCESS] Raw JSON successfully converted into a Type-Safe Java Record!");
            System.out.println("-> Event Origin System: " + deserializedEvent.triggeredBy());
            System.out.println("-> Extracted Payload Timestamp: " + deserializedEvent.timestamp() + "\n");

            System.out.println("--- Executing Validation Framework on Parsed Payload ---");
            engineHandler.validateAndRouteEvent(deserializedEvent);

        } catch (Exception e) {
            System.err.println("[PARSING EXCEPTION] The JSON structure inside the file is corrupt or empty.");
            e.printStackTrace();
        }

        System.out.println("\n=== SIMULATION COMPLETED SUCCESSFULLY ===");
    }
}
