# PTLauncher

Custom GUI launcher for ProTanki

The idea of this launcher was inspired by [this launcher](https://github.com/protanki-re/client-launcher).
Launcher comes with AIR (Windows only) and slightly modified [client-chainloader](https://github.com/protanki-re/client-chainloader). <br>
This launcher will only be useful for you if you are playing on custom server, otherwise you would probably use the official launcher.

In the beginning, I made this launcher for my experiments, but since people have reverse-engineered the new ProTanki protocol, I decided to publish it.

Custom servers:
 - https://github.com/Assasans/protanki-server (Currently unmaintained)
 - https://github.com/DaniloPalmeira/protanki-server
 - https://github.com/WolverinDEV/protanki-protocol-rs (This one is not a server, but still may be useful)
 - https://github.com/Luminate-D/protanki-server - A pretty new server, implemented in Java

### Quick start
```shell
git clone https://github.com/TheEntropyShard/PTLauncher.git
cd PTLauncher
mvn clean compile assembly:single
java -jar target/ptlauncher-1.0-jar-with-dependencies.jar
```
In the default profile do not forget to edit path to library.swf or create new profile

![Screenshot.png](Screenshot.png)

The UI sucks, I know