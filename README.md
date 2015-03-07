# Decaf Compiler

This is a compiler for Brown Decaf, a subset of Java. Written in Computer Science 4 at Commonwealth.

# Notes on running

This uses Maven for dependencies and build automation. You will need to install Java 8 and Maven if you have not already. Once you have Maven installed, run the tests with `mvn test`.

If you are on a mac, and you get the error "invalid target release: 1.8" you may have to set `JAVA_HOME`. I solved it by setting `JAVA_HOME` to `/Library/Java/JavaVirtualMachines/jdk1.8.0_40.jdk/Contents/Home/`
