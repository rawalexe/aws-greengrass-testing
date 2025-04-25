## How to run the tests?

These tests assume that you have greengrass-lite installled and running.

The tests can be run by executing the following command

```
pytest -q -s  --ggTestAccount=<YOUR_AWS_ACCOUNT_NUMBER> --ggTestBucket=<YOUR_TEST_BUCKET> --ggTestRegion=<TEST_REGION> test1.py
```
