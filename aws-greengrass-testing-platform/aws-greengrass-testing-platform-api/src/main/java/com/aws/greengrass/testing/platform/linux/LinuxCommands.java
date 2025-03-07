/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package com.aws.greengrass.testing.platform.linux;

import com.aws.greengrass.testing.api.device.Device;
import com.aws.greengrass.testing.api.device.exception.CommandExecutionException;
import com.aws.greengrass.testing.api.device.model.CommandInput;
import com.aws.greengrass.testing.api.model.PillboxContext;
import com.aws.greengrass.testing.platform.NucleusLiteInstallationParameters;
import com.aws.greengrass.testing.platform.UnixCommands;


import java.util.ArrayList;
import java.util.List;

public class LinuxCommands extends UnixCommands {
    public LinuxCommands(final Device device, final PillboxContext pillboxContext) {
        super(device, pillboxContext);
    }

    @Override
    public void installNucleusLite(NucleusLiteInstallationParameters installationParameters) throws CommandExecutionException {
        List<String> arguments = new ArrayList<>();
        if (installationParameters.getInstallerArgs() != null) {
            installationParameters.getInstallerArgs().forEach((k, v) -> {
                StringBuilder sb = new StringBuilder();
                sb.append(k).append(" ").append(v);
                arguments.add(sb.toString());
            });
        }

        executeInBackgroundAsRoot(CommandInput.builder()
                .workingDirectory(installationParameters.getGreengrassRootDirectoryPath())
                .line(installationParameters.getGreengrassRootDirectoryPath().resolve("install-greengrass-lite.sh").toString())
                .addArgs(arguments.toArray(new String[0]))
                .timeout(30L)
                .build());
        try {
            Thread.sleep(60_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executeAsRoot(CommandInput.builder()
                .line("")
                .addArgs("systemctl","restart", "greengrass-lite.target")
                .timeout(30L)
                .build());
    }

    @Override
    public void stopGreengrassService() throws CommandExecutionException {
        executeAsRoot(CommandInput.builder()
                .line("systemctl stop greengrass-lite.target")
                .build());
    }
}
