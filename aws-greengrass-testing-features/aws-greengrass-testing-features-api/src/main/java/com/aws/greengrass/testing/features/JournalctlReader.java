/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package com.aws.greengrass.testing.features;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JournalctlReader {
    public List<String> readServiceLogs(String serviceName) {
        List<String> logs = new ArrayList<>();

        // Build the journalctl command
        ProcessBuilder processBuilder = new ProcessBuilder(
                "journalctl",
                "-u",
                serviceName,
                "--no-pager"  // Prevents paging of output
        );

        // Start the process

        try {
            Process process = processBuilder.start();

            // Read the output
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    logs.add(line);
                }


                // Wait for the process to complete
                int exitCode = process.waitFor();
                if (exitCode != 0) {
                    throw new Exception("Failed to read journalctl logs. Exit code: " + exitCode);
                }

            }
        } catch (Exception e) {
        }
        return logs;
    }

    // Method to read logs in real-time (follow mode)
    public void followServiceLogs(String serviceName, LogHandler handler) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder(
                "journalctl",
                "-u",
                serviceName,
                "-f",  // Follow mode
                "--no-pager"
        );

        Process process = processBuilder.start();

        // Read the output in a separate thread
        Thread readerThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    handler.handleLog(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        readerThread.start();
    }

    // Interface for handling logs in real-time
    public interface LogHandler {
        void handleLog(String logLine);
    }
}

