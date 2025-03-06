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

import java.nio.file.Path;
import java.nio.file.Paths;
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
        System.out.println(">>>>>" + arguments);

        //1. TODO: print the error output of the script - script ran but failed - no log is present to debug
        //2. TODO: command run as sudo
        //Path installerScript = Paths.get("/home/ubuntu/installer/install-greengrass-lite.sh");
        //arguments.add("/home/ubuntu/GTF/installer/install-greengrass-lite.sh");
        execute(CommandInput.builder()
                .line("sudo")
                .addArgs(
                    "sh", "-c", 
                    "'/home/ubuntu/GTF/installer/install-greengrass-lite.sh -p /home/ubuntu/GTF/installer/aws-greengrass-lite-2.0.2-Linux.deb -k /home/ubuntu/GTF/installer/GTF-device-connectionKit.zip'"
                )
                .timeout(180L)
                .build());
    }

    @Override
    public void startGreengrassLiteService() throws CommandExecutionException {
        executeToString(CommandInput.builder()
                .line("sudo systemctl start greengrass-lite.target")
                .build());
    }

    @Override
    public void stopGreengrassLiteService() throws CommandExecutionException {
        executeToString(CommandInput.builder()
                .line("sudo systemctl stop greengrass.service")
                .build());
    }
}