/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package com.aws.greengrass.testing.platform;

import com.aws.greengrass.testing.api.model.TestingModel;
import org.immutables.value.Value;

import java.nio.file.Path;
import java.util.Map;

@TestingModel
@Value.Immutable
public abstract class NucleusLiteInstallationParametersModel {
    abstract Map<String, String> getInstallerArgs();

    abstract Map<String, String> getGreengrassParameters();

    abstract Path getGreengrassRootDirectoryPath();

}
