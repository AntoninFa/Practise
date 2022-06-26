#include <Adafruit_NeoPixel.h>

#define DATA_PIN 6
#define NUM_LEDS 55
int laenge = 10;


int precision =100;
int i=0;
int avgCounter = 0;
long lastN =0; // tmp 
int currentValue =0; //tmp
int avgOverLastN =200; // über wie viele Avg (wie viele zsm. rechnen)

int wertVonDerNiedrigstenAusgabe = 353;
int stufenSize = 2;

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
 //Serial.println(currentValue);
 Serial.println(loudnessAsInteger());
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
 } else { 
  return r;
 }
 return r;
  
}



}
