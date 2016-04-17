#!/usr/bin/env groovy
def folder = new File (".", "reactor")

if ( folder.exists()) {
  folder.deleteDir()
}
folder.mkdirs()

(1..1000).each {
  module ->
  println module

  def moduleFolder = new File ( folder, "module" + module)
  moduleFolder.mkdirs()
  
}
