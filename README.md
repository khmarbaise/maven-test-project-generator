Memory Analysis for Maven
=========================

What i did
----------


 Created a large multi module build which contains 5000 modules
 via the [test script](generate.groovy).
 
 The script creates a structure like this:

     parent (pom.xml)
       +--- mod0001 (pom.xml)
       +--- mod0002 (pom.xml)
       .
       +--- mod5000 (pom.xml)

 So only a single lever of hierarchy.

  
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


 Started Maven 3.1.1 like above.
 Get the Maven Source Code and set a break point in Eclipse at
 [`MavenCli.java` line 157][maven-3.1.1-start-debug]
  and an other one at [`MavenCli.java` line 561][maven-3.1.1-stop-debug].

[maven-3.0.5-memory]: https://github.com/khmarbaise/maven-test-project-generator/blob/master/Maven305-5000.png
[maven-3.0.5-start-debug]: https://git-wip-us.apache.org/repos/asf?p=maven.git;a=blob;f=maven-embedder/src/main/java/org/apache/maven/cli/MavenCli.java;h=5acd54a1156bea4033c4b443c1aa82dcb9e9927a;hb=01de14724cdef164cd33c7c8c2fe155faf9602da#l140
[maven-3.0.5-stop-debug]: https://git-wip-us.apache.org/repos/asf?p=maven.git;a=blob;f=maven-embedder/src/main/java/org/apache/maven/cli/MavenCli.java;h=5acd54a1156bea4033c4b443c1aa82dcb9e9927a;hb=01de14724cdef164cd33c7c8c2fe155faf9602da#l543
[maven-3.1.1-start-debug]: https://git-wip-us.apache.org/repos/asf?p=maven.git;a=blob;f=maven-embedder/src/main/java/org/apache/maven/cli/MavenCli.java;h=5acd54a1156bea4033c4b443c1aa82dcb9e9927a;hb=01de14724cdef164cd33c7c8c2fe155faf9602da#l14://git-wip-us.apache.org/repos/asf?p=maven.git;a=blob;f=maven-embedder/src/main/java/org/apache/maven/cli/MavenCli.java;h=1c142c4ec902936464b1fd515acdafa5feca945e;hb=0728685237757ffbf44136acec0402957f723d9#l157 
[maven-3.1.1-stop-debug]: https://git-wip-us.apache.org/repos/asf?p=maven.git;a=blob;f=maven-embedder/src/main/java/org/apache/maven/cli/MavenCli.java;h=1c142c4ec902936464b1fd515acdafa5feca945e;hb=0728685237757ffbf44136acec0402957f723d9#l561