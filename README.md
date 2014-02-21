# ITGController
## A tool for remotely controlling ITGSend running in daemon mode
Makes use of the D-ITG C++ API under Java using [JNA](https://github.com/twall/jna)

### Build
#### Prerequisites
- Install JDK
- Compile [D-ITG](http://traffic.comics.unina.it/software/ITG/download.php)
- Copy libITG.so to lib/

#### Linux
     ./build
     
#### Other
See javac commands in *build*

### Run
#### Linux
     ./ITGController <sender> <script>

#### Other
See java commands in *ITGController*

### License
GPL v3
