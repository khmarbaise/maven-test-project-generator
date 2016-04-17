#!/usr/bin/env groovy
class PomFile {
  String groupId
  String artifactId
  String version
  File folder

  PomFile (File folder, String groupId, String artifactId, String version) {
    this.folder = folder
    this.groupId = groupId
    this.artifactId = artifactId
    this.version = version
  }

  def writePomFile () {
    new File(this.folder,'pom.xml').withWriter('utf-8') { writer ->
        def xml = new groovy.xml.MarkupBuilder(writer).project {
            groupId(this.groupId)
            artifactId(this.artifactId)
            version(this.version)
        }
    }    
  }
}


def folder = new File (".", "reactor")

if ( folder.exists() ) {
  folder.deleteDir()
}
folder.mkdirs()

def levelList = []
(1..10).each {
  level ->
  def levelFormat = sprintf ("%03d", level)
  def levelModuleName = 'level-' + levelFormat
  println "Level: ${levelModuleName}"

  levelFolder = new File (folder, levelModuleName);
  levelFolder.mkdirs()

  PomFile pf = new PomFile (levelFolder, "org.test", levelModuleName, "1.0-SNAPSHOT")

  pf.writePomFile()
   
  levelList << levelModuleName 
}


/*
(1..1000).each {
  module ->
  println module

  def moduleFolder = new File ( folder, "module" + module)
  moduleFolder.mkdirs()
  
}
*/
