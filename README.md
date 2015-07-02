# SmsForwarder
Android SMS forwarder using rules.
Rules modification are not implemented yet.

## How it works
Today, it doesn't do much. The activity doesn't display anything. 

A SMS Receiver captures all SMS messages and parse them before the default SMS application does its duty. This also works even if the activity is not running foreground.
If a SMS has one of the following structures :
+ begin with "FORWARD_CR"
  + Then separator between fields is "\r"
+ begin with "FORWARD_OTHER"
  + Then separator between fields is ":"

The fields accepted have the following structure:
+ Destination number
  + D=+33XXXXXXXXX
+ Body of the SMS
  + B=Hello World

Destination number can be multiple.

then the SMS Receiver creates as many new SMS as destination number provided before using the following structure :
+ destination number provided
+ content body of the SMS

### Example of message with \r separator :
FORWARD_CR
D=+33626262626
D=+33658585858
B=Say Hello To SMS Forwarder

### Example of message with ":" separator :
FORWARD_OTHER:D=+33626262626:D=+33658585858:B=Say Hello To SMS Forwarder

## How to test (French FreeMobile users)
First have a look at : http://www.freenews.fr/freenews-edition-nationale-299/free-mobile-170/nouvelle-option-notifications-par-sms-chez-free-mobile-14817
This step is needed in order to activate the option.

To test :
https://smsapi.free-mobile.fr/sendmsg?user=(free_mobile_user)&pass=(free_mobile_pass)=FORWARD_CR%0dD%3D+33626699180%0dB%3DHELLO%20WORLD!
+ user = your Free mobile ID
+ pass = the passkey provided by Free
+ msg = the message you have to send
  + %0D are URL encoded cariage return (known as '\r').
  + %0A are URL encoded new line (known as '\n'), but doesn't seem to be OK with SMS.
  + %3D are "="

## Future plans

+ List of forwarded message in activity
+ Control of phone number provided in source SMS
+ Control of content provided in source SMS
