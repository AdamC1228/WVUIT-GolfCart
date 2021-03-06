import jssc.SerialPortList;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import jssc.SerialPort;
import jssc.SerialPortException;


public class server
{
	//Objects
	debugLogger myLogger;
	sharedData myData;
	arduinoRead ardReader;
	arduinoWrite ardWriter;
	networkWrite netWriter;
	networkRead netReader;
	ServerSocket myListener;
	Socket netSocket;
	
	//Serial Port Variables
	SerialPort mySerialPort;
	String serialPortLocation = "/dev/ttyACM0";
	int[] serialPortSettings = new int[]{SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE, SerialPort.FLOWCONTROL_NONE};

	//Networking Variables
	int networkPort = 4201;

	

	
    public static void main(String[] args)
    {	
    	//List my ports
        server myserver = new server();
        myserver.myLogger.writeLog("Server has stopped");
        myserver.myLogger.close();
    }    
    
    
    public server()
    {
    	while(true)
    	{
    		myLogger = new debugLogger(2);
    		myLogger.writeLog("Initialize the server!");
    		myData= new sharedData();
    	
    	//Begin Startup Procedure
    	listSerialPorts();
    	initSerialPort();
    	

    		initNetwork();
    	
    		//Init Objects
    		netWriter = new networkWrite(netSocket, myLogger);
    		ardWriter = new arduinoWrite(mySerialPort, myLogger);
    		ardReader = new arduinoRead(mySerialPort, myData, myLogger, netWriter);
    		netReader = new networkRead(netSocket, myData, myLogger, ardWriter);

        	//Done with startup
        	myLogger.writeLog("Starting up the server!");
    	
    		Thread ardReadThread = new Thread(ardReader);
    		Thread netReadThread = new Thread(netReader);
    	
    		ardReadThread.start();
    		netReadThread.start();
    	
    		while(ardReadThread.isAlive() && netReadThread.isAlive());
    		
    		myLogger.writeLog("Oops. Attemp reconnection");

    		closeAll();
    		
    	}
    }
    
    public void closeAll()
    {
    	try
    	{
    		netSocket.close();
    		mySerialPort.closePort();
    		myListener.close();
    	}
    	catch(Exception e)
    	{
    		myLogger.writeLog("ERROR CLOSING PORTS AND SOCKETS!");
    	}
    	
    	myLogger.close();
    }
    
    public void initNetwork()
    {
    	try
    	{
    		myListener = new ServerSocket(networkPort);
    		myLogger.writeLog("Waiting for client to connect!");
    		netSocket = myListener.accept();
    		myLogger.writeLog("Client Connected!");
    	}
    	catch(IOException e)
    	{
            myLogger.writeLog("Error: Failure to create network Socket!");
            myLogger.writeLog("\n\n");
            e.printStackTrace();
            myLogger.writeLog("\n\n");
    	}
    }
    
    public void initSerialPort()
    {
    	try
    	{
    		mySerialPort = new SerialPort(serialPortLocation);
    		mySerialPort.openPort();
    		mySerialPort.setParams(serialPortSettings[0],serialPortSettings[1],serialPortSettings[2],serialPortSettings[3]);
    		mySerialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
    		myLogger.writeLog("Serial Port Opened");
    	}
    	catch (SerialPortException e)
    	{
            myLogger.writeLog("Error: Failure to create to serial port!");
            myLogger.writeLog("\n\n");
            e.printStackTrace();
            myLogger.writeLog("\n\n");
    	}
    }
    
    //For getting the serial port name of the devices connected.
    public void listSerialPorts()
    {
    	myLogger.writeLog("Here are the connected devices:");
        String[] portNames = SerialPortList.getPortNames();
        for(int i = 0; i < portNames.length; i++)
        {
           myLogger.writeLog(portNames[i]);
        }
    }
}
