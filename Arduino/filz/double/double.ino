#include <Adafruit_NeoPixel.h>

#define DATA_PIN 6
#define NUM_LEDS 55
int laenge = 10;


int precision =50; // genauigkeit, umgekehrt proportional
int i=0; // tmp zähler
int avgCounter = 0; // tmp zähler
long lastN =0; // tmp 
int currentValue =0; //tmp
int avgOverLastN =200; // über wie viele Avg (wie viele zsm. rechnen)

int wertVonDerNiedrigstenAusgabe = 353; // niedrigster Wert, welcher vom Sensor ausgegeben wird 352, 354 gut wenn licht auch mal ausgehen soll
int stufenSize = 2;

int loudnessLvl = 1;

int farbe_rot;
int farbe_gruen;
int farbe_blau;

Adafruit_NeoPixel pixels = Adafruit_NeoPixel(NUM_LEDS, DATA_PIN, NEO_RGB + NEO_KHZ800);

void setup() {
  // initialize serial communication at 9600 bits per second:
  Serial.begin(9600);

    pixels.setBrightness(10);
  pixels.begin();

 
}

// the loop routine runs over and over again forever:
void loop() {
  // wie oft der Input des Sensors gemessen wird
if (i >= precision) {
  light();
 avgCounter++; // zählt jeden eingang in die if(i%precision)
 i=0;
} // precision loop ENDE
i++;

} // loop ENDE 



void light () {
   // read the input on analog pin 0:
  int sensorValue = analogRead(A0);
  // print out the value you read:
  lastN += sensorValue;
  //Serial.println(avgCounter);
  if( avgCounter >= avgOverLastN){

 // Summe der letzen N Messungen /N   
 currentValue = (lastN / avgOverLastN);
 int loudnessLvl = loudnessAsInteger();
 setColorOnLoudness();
 lightUpN((currentValue - (wertVonDerNiedrigstenAusgabe +1)) *2); // Damit n bei 0 beginnt, -1 um öfters 0 zu haben

 
 //Serial.println(currentValue);
 //Serial.println(loudnessAsInteger());
 lastN = 0;
 avgCounter =0;
  }
  }

int loudnessAsInteger() {
int r =7;
 if(currentValue <= wertVonDerNiedrigstenAusgabe ) {
  return 1;
 }
 if(currentValue <= (wertVonDerNiedrigstenAusgabe + stufenSize) ) {
  return 2;
 }
 if(currentValue <= (wertVonDerNiedrigstenAusgabe +(2* stufenSize)) ) {
  return 3;
 }
 if(currentValue <= (wertVonDerNiedrigstenAusgabe + (3* stufenSize)) ) {
  return 4;
 }
 if(currentValue <= (wertVonDerNiedrigstenAusgabe +(4* stufenSize)) ) {
  return 5;
 }
 if(currentValue <= (wertVonDerNiedrigstenAusgabe +(6* stufenSize)) ) {
  return 6;
 } 
 return r;
}

void setColorOnLoudness() {
  if(loudnessLvl = 1 ) {
    //leise
    setColor(168, 166, 50);
  return;
 }
 if(currentValue <= (wertVonDerNiedrigstenAusgabe + stufenSize) ) {
  setColor(237, 182, 64);
  return;
 }
 if(currentValue <= (wertVonDerNiedrigstenAusgabe +(2* stufenSize)) ) {
  setColor();
  return;
 }
 if(currentValue <= (wertVonDerNiedrigstenAusgabe + (3* stufenSize)) ) {
  setColor();
  return;
 }
 if(currentValue <= (wertVonDerNiedrigstenAusgabe +(4* stufenSize)) ) {
  setColor();
  return;
 }
 if(currentValue <= (wertVonDerNiedrigstenAusgabe +(6* stufenSize)) ) {
  setColor();
  return;
 } 
 // ganz laut
 setColor();
 return;
}

void setColor(int rot, int gruen, int blau) {
  farbe_rot = rot;
  farbe_gruen = gruen;
  farbe_blau = blau;
}

void lightUpN(int n) {
  Serial.println(n);
  if (n < 0) {
    Serial.println("ERROR, n kleiner 0");
  }
  for (int i = 0; i<=n; i++) {
    pixels.setPixelColor(i, pixels.Color(farbe_rot,farbe_gruen,farbe_blau));
    
  }
  for (int i=n; i<= NUM_LEDS; i++) {
    pixels.setPixelColor(i, pixels.Color(0,0,0));
  }
  pixels.show();
}
