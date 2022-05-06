
#include <ESP8266WiFi.h>
#include <ESP8266WiFiMulti.h>
#include <SoftwareSerial.h>


// hostpost(other than the communicating mobile) id and password
#ifndef STASSID
#define STASSID "Nord" 
#define STAPSK "my phone" 
#endif

const char* ssid     = STASSID;
const char* password = STAPSK;

// web server ip and port
const char* host = "34.125.211.48";
const uint16_t port = 3389;

// serial ports to communicate to launchpad
SoftwareSerial mySerial(13, 15);  // rx,tx. pin D7,D8
 
ESP8266WiFiMulti WiFiMulti;

WiFiClient client;

void setup() {
  Serial.begin(115200);
  mySerial.begin(9600);  //Rx Tx c2000

  // We start by connecting to a WiFi network
  WiFi.mode(WIFI_STA);
  WiFiMulti.addAP(ssid, password);

  Serial.println();
  Serial.println();
  Serial.print("Wait for WiFi... ");

  while (WiFiMulti.run() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

// connect to web server
  if (!client.connect(host, port)) {
    Serial.println("connection failed");
    Serial.println("wait 5 sec...");
    delay(5000);
    return;
  }
  else{
    Serial.print("connected to ");
    Serial.print(host);
    Serial.print(':');
    Serial.println(port);
  }

  delay(500);
}


void loop() {

    String to_mc = client.readString();

    // if data is received via wifi
    if(to_mc!=""){
      Serial.print("to_mc : ");
      Serial.println(to_mc);

      // making the length of string 100
      int len = to_mc.length();
      for(int i=0;i<100-len;i++) {
        to_mc += ' ';
      }

      // write to launchpad serial port
      const char *cstr = to_mc.c_str();
      mySerial.write(cstr);
    }

    // receive data from launchpad via serial

    String from_mc = mySerial.readString();
    if(from_mc!=""){
      Serial.print("from_mc : ");
      Serial.println(from_mc);

      // send data received from launchpad to web server
      client.write(from_mc.c_str());
    }

    

}
