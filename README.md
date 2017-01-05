WVUIT golfCart Project
===================


This is the primary control software for the WVUIT golfCart Project. 

----------
###Change Log
- Dev 
	- Added subModule code for throttle and steering ( currently Untested)
	- Began work on primary movement code for golfCart
	
----------

###Requirements 

- Perl
	- perl-Future
	- perl-IO-Termios
	- perl-IO-Async
	- perl-Moose
	- perl-Text-CSV
	- perl-Data-Dumper
- Arduino
	- Arduino IDE v 1.6.7
- QT
	- QSerialPort (Not required but hightly reccomended)

Basic Documentation
----  

###Files
| Category	| Component	| Description						|
| --------------| --------------| ----------------------------------------------------- |
|golfCart	|		| 							|
|		| golfCart	| main program for the golfCart 			|
|testModules	| 		| 							|
|           	| brake 	| Tests the Brake system sub-component.			|
|           	| move 		| Tests the primary movements of golfCart		|
|           	| steering 	| Tests the steering system sub-component.		|
|           	| throttle 	| Tests the throttle system sub-component.		|
|java		|		|							|
|		| server	| Main java application for the server			|
|		| sharedData 	| Common data object					|
|		| networkWrite 	| Sends data via network socket to connected client	|
|		| networkRead 	| Reads Data via network socket from connected client	|
|		| arduinoWrite 	| Sends data via serial port to arduino			|
|		| arduinoRead 	| Reads data via serial port from arduino		|
|		| debugLogger 	| Loggs all server program activity			|
|		| jssc-2.8.0 	| JSSC serial port library for serial communication. 	|
|docs		| 		| 							|
|           	| Documentation	| General Documentation and diagrams			|
|qt 		|		|							|
|		|remotecontrol 	| GUI client for remote-controlling golfCart		| 
     

