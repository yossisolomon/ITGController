## ITGController
### A tool for remotely controlling ITGSend running in daemon mode

### Prerequisites
- Install JDK & Ant

### Build
     ant

### Run
#### UNIX
     ./ITGController <config>

#### Other
     java -jar ITGController.jar <config>

### Config syntax
See *example-config*

### API
Uses a Java implementation of the D-ITG C++ API - [JITGApi](https://github.com/duncanje/jitgapi)

### License
GPL v3
