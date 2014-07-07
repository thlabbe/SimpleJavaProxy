SimpleJavaProxy
===============

Simple proxy impemented in Java to intercept and redirect connections.

To compile with Eclipe : 
------------------------
- first build an Eclipse project  
```bat  
gradle clean eclipse  
```
- Export as an Executable jar  

To execute 
----------

into Run SimpleProxy.bat  
```bat  
@echo off
ipconfig

java -jar ProxyServer.jar aaa.bbb.ccc.ddd distantPort localPort  
```
- replace _aaa.bbb.ccc.ddd_ by the distant IP adress to connect  
- replace _distantPort_ by the distant port to connect   
- replace _localPort_ by the local port the SimpleProxyServer will listen to.  

