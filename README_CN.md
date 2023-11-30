# CardExample

读卡器&灯带示例demo

## 介绍

简体中文 | [English](README.md)

> 主要通过串口协议进行通讯

## 文档

> 规格书以及协议文档在工程中的doc文件夹下

### Demo主要功能

+ 读卡器
  + 读卡器参数设置
  + 读取卡号
+ 门禁
  + 门禁控制
  + 蜂鸣器控制
+ 梯控
+ Led灯带控制
+ NFC读卡示例

#### 代码说明

+ src/main/java
  + android_serialport_api  **用于串口通讯**
  + com.cn.test
    + lstener
      + MyViewListener.class **重点,数据是在这个类中进行发送**
    + nfc **这里是android nfc api的示例代码**
    + serial **这里是对串口控制进行了一些封装，包含打开关闭串口，接收发送数据**

#### 注意事项

* 项目中提供了串口的通讯代码，**你也可以用其它的串口库来实现串口通讯**
* 使用JNI开发串口通讯时，需要注意java SerialPort类的包名一定要和示例中的包名一致，否则会产生jni so link问题



