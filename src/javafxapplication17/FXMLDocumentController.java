/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication17;

import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

/**
 *
 * @author Dell
 */
public class FXMLDocumentController implements Initializable {
   
    private String current_input = "";
    private double result = 0;
    private static calculator calculator = new calculator();
    public static String devicePortName = "COM4";
    public static SerialPort arduinoPort = null;
    public static InputStream arduinoStream = null;
    public static int PACKET_SIZE_IN_BYTES = 8;
    public static SerialPort JumboPort;
    private static SerialPort serialPort;
    ObservableList<String> options = FXCollections.observableArrayList();

    @FXML
    private Button ebutton;
    @FXML
    public TextField operation;
    @FXML
    private Button c;
    @FXML
    private Button rem;
    @FXML
    private Button div4;
    @FXML
    private Button seven;
    @FXML
    private Button eight;
    @FXML
    private Button nine;
    @FXML
    private Button mult;
    @FXML
    private Button four;
    @FXML
    private Button five;
    @FXML
    private Button six;
    @FXML
    private Button add;
    @FXML
    private Button one;
    @FXML
    private Button two;
    @FXML
    private Button three;
    @FXML
    private Button sub;
    @FXML
    private Button eq;
    private int operator;

    double a, b;

    String prevStr = "";
    @FXML
    private TextField results;
    @FXML
    private Button ex;
    @FXML
    private Button zero;
    @FXML
    private Button dot;
    @FXML
    private Button log1;
    @FXML
    private Button ln1;
    @FXML
    private Button pow1;
    @FXML
    private Button ep1;
    @FXML
    private Button r1;
    @FXML
    private Button fac;
    @FXML
    private Button s1;
    @FXML
    private Button c1;
    @FXML
    private Button t1;
    @FXML
    private Button Hswitch;
    @FXML
    private ComboBox<String> port_selection= new ComboBox<>(options);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Calculator Started");

        //Sets the user interface to have updated list of PORTS
        getSerialPorts();
    }

    @FXML
    private void exit(ActionEvent event) {
        /*for exiting app*/
         Platform.exit();
         serialPort.closePort();
    }

    @FXML
    private void clear(ActionEvent event) {
        /*setting all text fields with nothing to clear them */
        operation.setText("");
        results.setText("");
        prevStr = "";
        calculator.clear();
        a=0;
        b=0;
        result=0;
    }

    @FXML
    private void remaning(ActionEvent event) {
        try {
            a = Double.parseDouble(operation.getText());
            operator = 5;
            results.setText(prevStr + "%");
            prevStr = results.getText();
        } catch (NumberFormatException e) {
            System.out.println("Enter value First");
        } finally {
            operation.setText("");
        }
    }

    @FXML
    private void division(ActionEvent event) {
        try {
            a = Double.parseDouble(operation.getText());
            operator = 4;
            results.setText(prevStr + "/");
            prevStr = results.getText();
        } catch (NumberFormatException e) {
            System.out.println("Enter value First");
        } finally {
            operation.setText("");
        }
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        
        String str = ((Labeled) event.getSource()).getText();
        operation.setText(operation.getText() + str);
        results.setText(prevStr + str);
        prevStr = results.getText();
    }

    @FXML
    private void multiplication(ActionEvent event) {
        try {
            a = Double.parseDouble(operation.getText());
            operator = 3;
            results.setText(prevStr + "*");
            prevStr = results.getText();
        } catch (NumberFormatException e) {
            System.out.println("Enter value First");
        } finally {
            operation.setText("");
        }
    }

    @FXML
    private void addition(ActionEvent event) {
        try {
            a = Double.parseDouble(operation.getText());
            operator = 1;
            results.setText(prevStr + "+");
            prevStr = results.getText();
        } catch (NumberFormatException e) {
            System.out.println("Enter value First");
        } finally {
            operation.setText("");
        }
    }

    @FXML
    private void subtraction(ActionEvent event) {
        try {
            a = Double.parseDouble(operation.getText());
            operator = 2;
            results.setText(prevStr + "-");
            prevStr = results.getText();
        } catch (NumberFormatException e) {
            System.out.println("Enter value First");
        } finally {
            operation.setText("");
        }
    }

    @FXML
    private void equality(ActionEvent event) {
        try {
            b = Double.parseDouble(operation.getText());

            double inRadians = Math.toRadians(b);
            switch (operator) {
                case 1:
                    result = a + b;
                    operation.setText("" + result);
                    break;
                case 2:
                    result = a - b;
                    operation.setText("" + result);
                    break;
                case 3:
                    result = a * b;
                    operation.setText("" + result);
                    break;
                case 4: 
                 try {
                    result = a / b;
                    operation.setText("" + result);
                } catch (Exception e) {
                    operation.setText("Error");
                }
                case 5:
                    result = a % b;
                    operation.setText("" + result);
                    break;
                case 6:
                    result = (float) Math.log10(b);

                    operation.setText("" + result);
                    break;
                case 7:
                    result = (float) Math.log(b);

                    operation.setText("" + result);
                    break;
                case 8:
                    result = (float) Math.exp(b);

                    operation.setText("" + result);
                    break;
                case 9:
                    result = Math.pow(a, b);
                    operation.setText("" + result);
                    break;
                case 10:
                    result = (long) Math.sqrt(b);
                    operation.setText("" + result);
                    break;
                case 11:

                    result = (float) Math.sin(inRadians);
                    operation.setText("" + result);
                    break;
                case 12:
                    result = (float) Math.cos(inRadians);
                    operation.setText("" + result);
                    break;
                case 13:
                    result = (float) Math.tan(inRadians);
                    operation.setText("" + result);
                    break;
                case 14:
                    result = 1;
                    for (int i = 1; i <= b; i++) {
                        result *= i;
                    }
                    operation.setText("" + result);
                    break;
            }
            results.setText(prevStr + "=" + result);

        } catch (NumberFormatException e) {
            System.out.println("Select values First");
        }
    }

    @FXML
    private void log(ActionEvent event) {
        try {
            results.setText("log");
            // a = Double.parseDouble(operation.getText());
            operator = 6;
            //results.setText("log" + prevStr);
            prevStr = results.getText();
        } catch (NumberFormatException e) {
            System.out.println("Enter value First");
        } finally {
            operation.setText("");
        }
    }

    @FXML
    private void ln(ActionEvent event) {
        try {
            results.setText("ln");
            // a = Double.parseDouble(operation.getText());
            operator = 7;
            //results.setText("log" + prevStr);
            prevStr = results.getText();
        } catch (NumberFormatException e) {
            System.out.println("Enter value First");
        } finally {
            operation.setText("");
        }
    }

    @FXML
    private void power(ActionEvent event) {
        try {

            a = Double.parseDouble(operation.getText());
            operator = 9;
            results.setText(prevStr + " ^");
            prevStr = results.getText();
        } catch (NumberFormatException e) {
            System.out.println("Enter value First");
        } finally {
            operation.setText("");
        }
    }

    @FXML
    private void epower(ActionEvent event) {
        try {
            results.setText("e^");
            // a = Double.parseDouble(operation.getText());
            operator = 8;
            //results.setText("log" + prevStr);
            prevStr = results.getText();
        } catch (NumberFormatException e) {
            System.out.println("Enter value First");
        } finally {
            operation.setText("");
        }
    }

    @FXML
    private void sqRoot(ActionEvent event) {
        try {
            results.setText("√");
            // a = Double.parseDouble(operation.getText());
            operator = 10;
            //results.setText("log" + prevStr);
            prevStr = results.getText();
        } catch (NumberFormatException e) {
            System.out.println("Enter value First");
        } finally {
            operation.setText("");
        }
    }

    @FXML
    private void factorial(ActionEvent event) {
        try {
            results.setText("!");
            // a = Double.parseDouble(operation.getText());
            operator = 14;
            //results.setText("log" + prevStr);
            prevStr = results.getText();
        } catch (NumberFormatException e) {
            System.out.println("Enter value First");
        } finally {
            operation.setText("");
        }
    }

    @FXML
    private void sin(ActionEvent event) {
        try {
            results.setText("sin(");
            // a = Double.parseDouble(operation.getText());
            operator = 11;
            //results.setText("log" + prevStr);
            prevStr = results.getText();
        } catch (NumberFormatException e) {
            System.out.println("Enter value First");
        } finally {
            operation.setText("");
        }
    }

    @FXML
    private void cos(ActionEvent event) {
        try {
            results.setText("cos(");
            // a = Double.parseDouble(operation.getText());
            operator = 12;
            //results.setText("log" + prevStr);
            prevStr = results.getText();
        } catch (NumberFormatException e) {
            System.out.println("Enter value First");
        } finally {
            operation.setText("");
        }
    }

    @FXML
    private void tan(ActionEvent event) {
        try {
            results.setText("tan(");
            // a = Double.parseDouble(operation.getText());
            operator = 13;
            //results.setText("log" + prevStr);
            prevStr = results.getText();
        } catch (NumberFormatException e) {
            System.out.println("Enter value First");
        } finally {
            operation.setText("");
        }
    }

    @FXML
    private void hardwareSwitch(ActionEvent event) {

            
         if(Hswitch.getText().equals("Connect"))
            {
                try
                {
                    if(port_selection.getValue() == null)
                    {
                        error("SELECT PORT: EXAMPLE      port > \"COM3\" or '/dev/ttyACM0'");
                    }
                    else
                    {

                        String portName = port_selection.getValue();

                        serialPort = SerialPort.getCommPort(portName);

                        //set port to scanner mode lets reading in characters without quitting early
                        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 1000, 1000);
                        serialPort.openPort();
                        //open port arduino serial port
                        if(serialPort.isOpen())
                        {
                            System.out.println(String.format("Port: %s %n", portName));
                            alert(String.format("NOTE: If buttons NOT working, TRY other PORT, than port: %s %n", portName));
                            alert(String.format("Port Connected!: %s %n", portName));
                            Hswitch.setText("Disconnect");
                            port_selection.setEditable(false);
                        }
                        else
                        {
                            System.out.println("Port not open");
                        }




                        /*
                        * create a thread that monitors the characters
                        * that come off the arduino
                        * for each character that comes in
                        * CALL
                        *
                        * calculator.setTextInput(line);
                        *
                        * witch calls the calculator class
                        * sets up the math stuff, then returns the result
                        * sets the javafx insterface to display the result
                        * calculator > "11+33" set interface to "44"
                        * */
                        Thread thread = new Thread()
                        {
                            @Override
                            public void run()
                            {
                                if(serialPort.getInputStream() == null)
                                {
                                    //error("Error occurred when tring to connect to port:" +
                                    //      "Try another port");
                                    error(String.format("Error Occurred when trying to connect to port:%n Is the Arduino Connected? %n or try connecting to a different port%n"));
                                } else
                                {
                                    try
                                    {
                                        Scanner keypressed = null;
                                        if(serialPort.isOpen())
                                        {
                                            keypressed = new Scanner(serialPort.getInputStream());
                                        }

                                        while(keypressed.hasNext())
                                        {
                                            try
                                            {
                                                String line = keypressed.nextLine();
                                                if(line.matches("[ABCD0123456789#*]"))
                                                {

                                                    calculator.setTextInput(line);


                                                    results.setText(calculator.getCurrentInput());
                                                    operation.setText(calculator.getResult());
                                                }
                                            }
                                            catch(Exception err)
                                            {
                                                System.err.println("Exception: Reading Arduino serial port");
                                                error("Exception: Reading Arduino serial port");
                                                keypressed.close();
                                            }
                                        }
                                    }
                                    catch(IllegalStateException e)
                                    {
                                        System.err.println("ERROR: Try DISCONNECT  then CONNECT ");
                                        serialPort.closePort();
                                    }
                                }
                            }
                        };


                        //close text input monitoring thread when closing the program
                       
                        thread.setDaemon(true);

                        //start a new thread the monitors the serial characters coming in from arduino keypad
                        thread.start();

                    }
                }
                catch(Exception err)
                {
                    System.out.print(String.format("Something went wrong%n",err));
                }
            }
            else
            {

                /*
                * DISCONNECT
                * when the button 'disconnect is pressed it
                * Closes the connection to the arduino
                * sets the desplay to blank
                * allows the user to select a port
                *
                * also clears the display when trying to reconnect
                * sets the current number of the calculator to blank
                * */
                serialPort.closePort();
                Hswitch.setText("Connect");
                port_selection.setEditable(true);

                results.setText("0.0");
                operation.setText("");
                calculator.setMathOperator("");
                calculator.setResult(0.0);
                calculator.setCurrentInput("");

            }
     
    }
private void alert( String message )
    {
        alert_set(message, Alert.AlertType.INFORMATION);
    }
    private void error( String error_message )
    {
        alert_set(error_message, Alert.AlertType.ERROR);
    }
private void alert_set( String message, Alert.AlertType alert_type )
    {
        try
        {
            //http://stackoverflow.com/questions/28937392/ddg#36938061
            Alert alert = new Alert(alert_type, message, ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);

            alert.setX(0);
            alert.setY(0);

            alert.show();
        }catch(Exception err)
        {
            System.err.println("ERROR: Alert Message Failed!");
        }

    }
   private void getSerialPorts()
    {

        SerialPort[] portNames = SerialPort.getCommPorts();

        options = FXCollections.observableArrayList();

        for(int i = 0; i < portNames.length; i++)
        {
            String portname = portNames[i].getSystemPortName();
            options.add(portname);
            port_selection.setItems(options);
        }
    }

    @FXML
    private void del(ActionEvent event) {
        calculator.del();
        String temp=operation.getText();
        int length = temp.length();
        if (length != 0) {
                temp = temp.substring(0, length - 1);
            } else {
                System.err.print(String.format("Error Input is empty%n"));
            }
        results.setText(temp);
        operation.setText(temp);
        prevStr= temp;
    }

}
