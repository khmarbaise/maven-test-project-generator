Memory Analysis for Maven
=========================

What i did
----------


 Created a large multi module build which contains 5000 modules
 via the [test script](generate.groovy).

  
 Started Maven 3.0.5 via:

 ```
  mvnDebug clean
 ```

 Get the Maven Source Code and set a break point in Eclipse at
 [`MavenCli.java` line 140][maven-3.0.5-start-debug]
  and an other one at [`MavenCli.java` line 543][maven-3.0.5-stop-debug].

 After i got the break in the first break point i started JConsole and
 connected to the PID of the maven process and than let done the maven 
 process it's work. After some time the maven process ended i got the 
 first [result picture about][maven-3.0.5-memory] memory evolvement 
 during run time.

[maven-3.0.5-memory]: https://github.com/khmarbaise/maven-test-project-generator/blob/master/Maven305-5000.png
[maven-3.0.5-start-debug]: https://git-wip-us.apache.org/repos/asf?p=maven.git;a=blob;f=maven-embedder/src/main/java/org/apache/maven/cli/MavenCli.java;h=5acd54a1156bea4033c4b443c1aa82dcb9e9927a;hb=01de14724cdef164cd33c7c8c2fe155faf9602da#l140
[maven-3.0.5-stop-debug]: https://git-wip-us.apache.org/repos/asf?p=maven.git;a=blob;f=maven-embedder/src/main/java/org/apache/maven/cli/MavenCli.java;h=5acd54a1156bea4033c4b443c1aa82dcb9e9927a;hb=01de14724cdef164cd33c7c8c2fe155faf9602da#l543
