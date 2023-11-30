# CardExample

Card Reader & LED Strip Sample Demo

## Introduction

English | [简体中文](README_CN.md)

> Mainly communicates through serial port protocol.

## Documentation

> Specification and protocol documents can be found in the 'doc' folder of the project.

### Main Features of the Demo

+ Card Reader
    + Card Reader Parameter Settings
    + Read Card Number
+ Access Control
    + Access Control Management
    + Buzzer Control
+ Elevator Control
+ LED Strip Control Commonly Used
+ NFC Card Reading Example

#### Code Explanation

+ src/main/java
    + android_serialport_api  **Used for serial communication**
    + com.cn.test
        + lstener
            + MyViewListener.class **Important: Data is sent in this class**
        + nfc **This contains sample code of Android NFC API**
        + serial **This contains encapsulation of serial control including opening/closing serial port and sending/receiving data**

#### Note

* The project provides serial communication code, you can also use other serial port libraries to implement serial communication
* When using JNI for serial communication development, make sure that the package name of the Java SerialPort class matches the package name in the example, otherwise it may cause JNI SO link issues.



