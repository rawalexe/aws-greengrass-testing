def pytest_addoption(parser):
    parser.addoption(
        "--ggTestAccount",
        action="store",
        default="",
        help="The AWS Account ID to be used for running the tests.",
    )
    parser.addoption(
        "--ggTestBucket",
        action="store",
        default="",
        help="The S3 test bucket used to store artifacts.",
    )
    parser.addoption(
        "--ggTestRegion",
        action="store",
        default="us-west-2",
        help="The region to be used with AWS account.",
    )
    parser.addoption(
        "--ggTestThingGroup",
        action="store",
        default="",
        help="The thing group that the tests will deploy to.",
    )
