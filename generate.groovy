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

  def writePomFile (Map parentPar) {
    writePomFile(parentPar, null)
  }
  def writePomFile () {
    writePomFile(null, null)
  }
  def writePomFile (Map parentPar, modulesPar) {
    new File(this.folder,'pom.xml').withWriter('utf-8') { writer ->
        def builder = new groovy.xml.MarkupBuilder(writer)
        builder.getMkp().xmlDeclaration( 'version':'1.0', 'encoding':'UTF-8')
        builder.project ( 
              'xmlns':"http://maven.apache.org/POM/4.0.0",
              'xmlns:xsi':"http://www.w3.org/2001/XMLSchema-instance",
              'xsi:schemaLocation':"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd"
            ) {
            modelVersion("4.0.0")
            if (parentPar != null) { 
              parent {
                groupId(parentPar['groupId'])
                artifactId(parentPar['artifactId'])
                version(this.version)
              }
            } else {
              version(this.version)
            }
            groupId(this.groupId)
            artifactId(this.artifactId)

            if (modulesPar != null) {
              packaging('pom')
              modules {
                modulesPar.each {
                  module(it)
                }
              }
            }
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
(1..3).each { level ->

  def levelFormat = sprintf ("%03d", level)
  def levelModuleName = 'level-' + levelFormat
  println "Level: ${levelModuleName}"

  levelFolder = new File (folder, levelModuleName);
  levelFolder.mkdirs()

  PomFile pf = new PomFile (levelFolder, "org.test.level", levelModuleName, "1.0-SNAPSHOT")

  pf.writePomFile(['groupId':'org.test.parent', 'artifactId':'reactor-parent'])
   
  levelList << levelModuleName 

}

pf = new PomFile (folder, "org.test.parent", "reactor-parent", "1.0-SNAPSHOT")

pf.writePomFile( null, levelList)

/*
(1..1000).each {
  module ->
  println module

  def moduleFolder = new File ( folder, "module" + module)
  moduleFolder.mkdirs()
  
}
*/
