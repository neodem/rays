# rays
a test 3d game thing

This is an experimental project where I try to implement some of the ideas in the "Game Engine Black Book : Wolfenstein 3D" book by Fabien Sanglard.

I'm doing this in java which is clearly not performant nor anywhere in the spirit of the code Id wrote 20+ years ago.

The idea is to simply learn some of the concepts and problems faced when building and projecting through a 3d world.

# To Run

(requires java 11 and maven)

maven clean install

java -classpath {DEVHOME}/rays/target/classes:{M2RePo}/org/apache/commons/commons-lang3/3.1/commons-lang3-3.1.jar:{M2RePo}/org/slf4j/slf4j-api/1.7.29/slf4j-api-1.7.29.jar:{M2RePo}/ch/qos/logback/logback-core/1.2.3/logback-core-1.2.3.jar:{M2RePo}/ch/qos/logback/logback-classic/1.2.3/logback-classic-1.2.3.jar com.neodem.rays.Rays


--- 
#TODO

- Don't allow a player to walk through walls
