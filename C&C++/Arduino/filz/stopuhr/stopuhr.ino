#include <Adafruit_NeoPixel.h>

#define DATA_PIN 6
#define NUM_LEDS 55

int precision =100; // genauigkeit, umgekehrt proportional
int i=0;

unsigned long myTime;
unsigned long ofASecond;


int farbe_rot = 255;
int farbe_gruen = 0;
int farbe_blau = 0;

Adafruit_NeoPixel pixels = Adafruit_NeoPixel(NUM_LEDS, DATA_PIN, NEO_RBG + NEO_KHZ800);

void setup() {
  // initialize serial communication at 9600 bits per second:
  Serial.begin(9600);

    pixels.setBrightness(10);
  pixels.begin();
}

void loop() {
if (i >= precision) {
myTime = millis();

setMillis();
setSeconds();

//lightUpN(24,40,40);


  
}
i++;
}

void setMillis(){
  ofASecond = myTime%1000;
  unsigned long partOftausend = ofASecond/(1000/24);
  lightUpN(0, partOftausend, 24);
}

void setSeconds() {
  long ofAMinute = myTime%(60000);
  long partOfsechzig = ofAMinute/(60000/16);
  Serial.println(partOfsechzig);
  lightUpN(24, (24 +partOfsechzig), 40);
}

void lightUpN(int from, int to, int max) {
  if (from < 0) {
    Serial.println("ERROR, from kleiner 0");
  }
  for (int i = from; i<=to; i++) {
    pixels.setPixelColor(i, pixels.Color(farbe_rot,farbe_gruen,farbe_blau));
    
  }
  for (int i=to; i<= max; i++) {
    pixels.setPixelColor(i, pixels.Color(0,0,0));
  }
  pixels.show();
}
