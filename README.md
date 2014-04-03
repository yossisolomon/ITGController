## ITGController
### A tool for remotely controlling ITGSend running in daemon mode

### Prerequisites
- Install JDK & Ant

### Build
     ant

### Run
#### UNIX
     ./ITGController <script>

#### Other
     java -jar ITGController.jar <script>

### Script syntax
See *example-script*

### API
Uses a [Java implementation](https://github.com/duncanje/j-itgapi) of the D-ITG C++ API

### License
GPL v3
