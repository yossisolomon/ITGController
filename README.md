## ITGController
### A tool for remotely controlling ITGSend running in daemon mode
Makes use of the D-ITG C++ API under Java using [JNA](https://github.com/twall/jna)

### Prerequisites
- Install JDK & Ant
- Compile [D-ITG](http://traffic.comics.unina.it/software/ITG/download.php)
- Copy libITG.so to lib/

### Build
     ant

### Run
#### UNIX
     ./ITGController <sender> <script>

#### Other
     java -jar ITGController.jar <sender> <script>

### License
GPL v3
