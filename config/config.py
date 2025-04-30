# The AWS account ID in which the test resource will be created.
aws_account = "241411971030"

# The S3 bucket used to store the artifacts.
s3_bucket_name = "my-gg-test-bucket"

# The region of the AWS Account used for the tests.
region = "us-west-2"

# First thing group with 'thing' under test added to it.
thing_group_1 = "gglite_thing_grp"

# Second thing group with 'thing' under test added to it.
thing_group_2 = ""

# Thing which is part of the above thing groups.
thing_name = "gglite"

# Location of ggl-cli binary
ggl_cli_bin_path = "../aws-greengrass-lite/build/bin/ggl-cli"
