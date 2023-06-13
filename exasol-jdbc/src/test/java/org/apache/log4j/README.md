# Reason for these files

Spark uses very old Log4j dependency. We don't want to include it since it is not maintained and full of vulnerabilities.

However, while running tests using Java 11, we are getting these errors:

```
Exception in thread "Executor task launch worker-0" java.lang.NoSuchFieldError: mdc
        at org.apache.log4j.MDCFriend.fixForJava9(MDCFriend.java:11)
        at org.slf4j.impl.Log4jMDCAdapter.<clinit>(Log4jMDCAdapter.java:38)
        at org.slf4j.impl.StaticMDCBinder.getMDCA(StaticMDCBinder.java:59)
        at org.slf4j.MDC.bwCompatibleGetMDCAdapterFromBinder(MDC.java:99)
        at org.slf4j.MDC.<clinit>(MDC.java:108)
        at org.apache.spark.executor.Executor.org$apache$spark$executor$Executor$$setMDCForTask(Executor.scala:749)
        at org.apache.spark.executor.Executor$TaskRunner.run(Executor.scala:441)
        at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
        at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
        at java.base/java.lang.Thread.run(Thread.java:829)
```

To fix this issue, we create required files locally for tests.
