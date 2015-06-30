# SmsForwarder
Android SMS forwarder using rules.
Rules modification are not implemented yet.

## How it works
Today, it doesn't do much. The activity doesn't display anything. 

A SMS Receiver captures all SMS messages and parse them before the default SMS application does its duty. This also works even if the activity is not running foreground.
If a SMS has the following structure :
+ 1st line = FORWARD
+ 2nd line = a phone number (not controlled yet)
+ 3rd line = a string message (not controlled yet.. has sure to be)

then the SMS Receiver creates a new SMS using the following structure :
+ destination number provided on 2nd line of the previous SMS
+ content body of the SMS provided on 3rd line of the previous SMS

Example of message :
FORWARD
+33626262626
Say Hello To SMS Forwarder

## How to test (French FreeMobile users)
First have a look at : http://www.freenews.fr/freenews-edition-nationale-299/free-mobile-170/nouvelle-option-notifications-par-sms-chez-free-mobile-14817
This step is needed in order to activate the option.

To test :
https://smsapi.free-mobile.fr/sendmsg?user=(free_mobile_user)&pass=(free_mobile_pass)=FORWARD%0d+33626262626%0dFREE
+ user = your Free mobile ID
+ pass = the passkey provided by Free
+ msg = the message you have to send
  + %0D are URL encoded cariage return (known as '\r').
  + %0A are URL encoded new line (known as '\n'), but doesn't seem to be OK with SMS.

## Future plans

+ List of forwarded message in activity
+ Control of phone number provided in source SMS
+ Control of content provided in source SMS
