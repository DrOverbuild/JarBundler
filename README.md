JarBundler
==========

A simple tool that uses the [appbundler](https://java.net/projects/appbundler) Ant tool to create a Mac OS X native executable (.app).

Requires Ant to be installed somewhere and the above linked jar file located somewhere, with the name appbundler-1.0.jar. Otherwise build will be unsuccessful, even if the program says it was.

Update: You don't need Ant to actually be installed in the location. You just need to download the latest version of Ant, rename it to just 'ant', and move it to wherever you want. Then download appbundler to wherever you want, make sure it has the name 'appbundler-1.0.jar', and you're good. You don't have to do anything with the bash_profile or anything. Upon first run, JarBundler will ask you where ant is and appbundler-1.0.jar.

This probably does not work with the Windows operating system. It may work with Linux, and will definitely run on Mac.
