SimpleJavaProxy
===============

Simple proxy impemented in Java to intercept and redirect connections.

To compile with Eclipe : 
------------------------
- first build an Eclipse project  
gradle clean eclipse  

- Export as an Executable jar  

To execute 
----------

into Run SimpleProxy.bat  
java -jar ProxyServer.jar aaa.bbb.ccc.ddd distantPort localPort  
replace aaa.bbb.ccc.ddd by the distant IP adress to connect  
replace distantPort by the distant port to connect   
replace localPort by the local port the SimpleProxyServer will listen to.  
