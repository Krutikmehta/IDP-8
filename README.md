# IDP-8th-sem

### server.py
1. All clients are handled by separate threads.
2. Communication format is JSON.
3. If ip address is not specified in JSON, the server echo backs the data. 
4. Else it forwards the data to the specified ip address, also it changes the ip address to the source ip(from initial dest ip)  which can be used by destination to send back data again.

### android 
1. All fields are formatted to JSON and sent/received.


### esp8266
1. Forwards received data from/to launchpad to/from the server.
2. It sends data in size of 100 bytes to the launchpad.
3. It can receive variable length data from launchpad.

### launchpad
1. Receives data from the esp8266.
2. Responds with the appropritate response.

### sources - serial communication - esp8266 and launchpad
https://www.hackster.io/RoboticaDIY/send-data-from-arduino-to-nodemcu-and-nodemcu-to-arduino-17d47a
https://www.instructables.com/Arduino-Function-Serialread-And-SerialreadString/ \
using software serial - https://arduino.stackexchange.com/questions/76299/nodemcu-wiring-for-serial1 \
                      - http://pdacontrolen.com/esp8266-two-serial-ports-with-softwareserial-library/
                      
### sources - android studio 
android basics - https://www.youtube.com/watch?v=qK0QNA0sMGc&t=3964s \
               - https://www.youtube.com/watch?v=FjrKMcnKahY \
networking     - https://www.youtube.com/watch?v=6kPOhWqHqZc&t=718s \
               - https://www.youtube.com/watch?v=srUSutae9n8  \
Installing basics - https://www.youtube.com/watch?v=SRD6v0trzlA \
                  - https://www.youtube.com/watch?v=QpuoZrKUVmo
                  
### web server - python
running server on gcloud - https://www.youtube.com/watch?v=RFPlXmgKCtk

