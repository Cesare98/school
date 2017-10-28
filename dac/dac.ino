#define DIM 9
#define START_STOP 5
int pin[DIM]={13, 12, 11, 10, 9, 8, 7, 6, START_STOP};

void setup()
{
    Serial.begin(9600);
    for(int i=0;i<DIM;i++)
    {
        pinMode(pin[i], OUTPUT);
    }
}

void loop()
{
    for(int i=0;i<256;i++)
    {
        digitalWrite(START_STOP, LOW);
        delay(10);
        convertAndSend(i);
        delay(10);
        digitalWrite(START_STOP, HIGH);
        delay(500);
        Serial.print((String) i+": "+((i*5)/255)+"\n");
    }
}

void convertAndSend(int n)
{
    // Serial.print((String) n+": ");
    for(int i=DIM-2;i>=0 ;i--)
    {
        digitalWrite(pin[i], n%2);
        // Serial.print((String) (n%2)+" ");
        n/=2;
    }
    Serial.print("\n");
}
