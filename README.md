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

 So only a single level hierarchy.

  
 Started Maven 3.0.5 via:

 ```
  mvnDebug clean
 ```

 Get the Maven Source Code and set a break point in Eclipse at
 [`MavenCli.java` line 140][maven-3.0.5-start-debug]
  and an other one at [`MavenCli.java` line 543][maven-3.0.5-stop-debug].

 For this i have checked out the appropriate tag in Git repo.

 After i got the break in the first break point i started JConsole and
 connected to the PID of the maven process and than let done the maven 
 process it's work. After some time the maven process ended i got the 
 first [result picture about][maven-3.0.5-memory] memory evolvement 
 during run time.


 Started Maven 3.1.1 like above.
 Get the Maven Source Code and set a break point in Eclipse at
 [`MavenCli.java` line 157][maven-3.1.1-start-debug]
  and an other one at [`MavenCli.java` line 561][maven-3.1.1-stop-debug].

 Started Maven 3.2.5 like above.
 Get the Maven Source Code and set a break point in Eclipse at
 [`MavenCli.java` line 159][maven-3.2.5-start-debug]
  and an other one at [`MavenCli.java` line 590][maven-3.2.5-stop-debug].

 Started Maven 3.3.9 like above.
 Get the Maven Source Code and set a break point in Eclipse at
 [`MavenCli.java` line 198][maven-3.3.9-start-debug]
  and an other one at [`MavenCli.java` line 896][maven-3.3.9-stop-debug].

  * [Result Maven 3.0.5][maven-3.0.5-memory]
  * [Result Maven 3.1.1][maven-3.1.1-memory]
  * [Result Maven 3.2.5][maven-3.2.5-memory]
  * [Result Maven 3.3.9][maven-3.3.9-memory]

 The following result is after applying the simple patch from MNG-6030:

  * [Result Maven 3.4.0-Patch][maven-3.4.0-memory-patch]


 For further analysis it's worth to get heap dumps which can easily created
 by using the following command:

```
jmap -dump:live,format=b,file=maven-3.0.5-5000.hprof PID
```
 This can be simplest be done at the second break point and of course with the
 correct pid which can be seen in JConsole.


 During the above tests i had configured `$HOME/.mavenrc/` like this:

```
export MAVEN_OPTS="-Xmx6g -Xms4096m -XX:MaxPermSize=512m -Djava.awt.headless=true"
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.7.0_79.jdk/Contents/Home
```

[maven-3.0.5-memory]: https://github.com/khmarbaise/maven-test-project-generator/blob/master/Maven305-5000.png
[maven-3.1.1-memory]: https://github.com/khmarbaise/maven-test-project-generator/blob/master/Maven311-5000.png
[maven-3.2.5-memory]: https://github.com/khmarbaise/maven-test-project-generator/blob/master/Maven325-5000.png
[maven-3.3.9-memory]: https://github.com/khmarbaise/maven-test-project-generator/blob/master/Maven339-5000.png
[maven-3.4.0-memory-patch]: https://github.com/khmarbaise/maven-test-project-generator/blob/master/Maven340-with-patch-5000.png
[maven-3.0.5-start-debug]: https://git-wip-us.apache.org/repos/asf?p=maven.git;a=blob;f=maven-embedder/src/main/java/org/apache/maven/cli/MavenCli.java;h=5acd54a1156bea4033c4b443c1aa82dcb9e9927a;hb=01de14724cdef164cd33c7c8c2fe155faf9602da#l140
[maven-3.0.5-stop-debug]: https://git-wip-us.apache.org/repos/asf?p=maven.git;a=blob;f=maven-embedder/src/main/java/org/apache/maven/cli/MavenCli.java;h=5acd54a1156bea4033c4b443c1aa82dcb9e9927a;hb=01de14724cdef164cd33c7c8c2fe155faf9602da#l543
[maven-3.1.1-start-debug]: https://git-wip-us.apache.org/repos/asf?p=maven.git;a=blob;f=maven-embedder/src/main/java/org/apache/maven/cli/MavenCli.java;h=5acd54a1156bea4033c4b443c1aa82dcb9e9927a;hb=01de14724cdef164cd33c7c8c2fe155faf9602da#l14://git-wip-us.apache.org/repos/asf?p=maven.git;a=blob;f=maven-embedder/src/main/java/org/apache/maven/cli/MavenCli.java;h=1c142c4ec902936464b1fd515acdafa5feca945e;hb=0728685237757ffbf44136acec0402957f723d9#l157 
[maven-3.1.1-stop-debug]: https://git-wip-us.apache.org/repos/asf?p=maven.git;a=blob;f=maven-embedder/src/main/java/org/apache/maven/cli/MavenCli.java;h=1c142c4ec902936464b1fd515acdafa5feca945e;hb=0728685237757ffbf44136acec0402957f723d9#l561
[maven-3.2.5-start-debug]: https://git-wip-us.apache.org/repos/asf?p=maven.git;a=blob;f=maven-embedder/src/main/java/org/apache/maven/cli/MavenCli.java;h=ef81408e51fdf11a2f47d5d41b09fbf403021e2d;hb=12a6b3acb947671f09b81f49094c53f426d8cea1#l159
[maven-3.2.5-stop-debug]: https://git-wip-us.apache.org/repos/asf?p=maven.git;a=blob;f=maven-embedder/src/main/java/org/apache/maven/cli/MavenCli.java;h=ef81408e51fdf11a2f47d5d41b09fbf403021e2d;hb=12a6b3acb947671f09b81f49094c53f426d8cea1#l590 
[maven-3.3.9-start-debug]: https://git-wip-us.apache.org/repos/asf?p=maven.git;a=blob;f=maven-embedder/src/main/java/org/apache/maven/cli/MavenCli.java;h=176ce4d843f2e31b472c8e7f4efd594b5f76fa8a;hb=bb52d8502b132ec0a5a3f4c09453c07478323dc5#l198
[maven-3.3.9-stop-debug]: https://git-wip-us.apache.org/repos/asf?p=maven.git;a=blob;f=maven-embedder/src/main/java/org/apache/maven/cli/MavenCli.java;h=176ce4d843f2e31b472c8e7f4efd594b5f76fa8a;hb=bb52d8502b132ec0a5a3f4c09453c07478323dc5#l869

