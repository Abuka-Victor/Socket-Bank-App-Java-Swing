
# Java Banking Application with Sockets, and Swing

This is a bank application simulator (using sockets) that I did for my final Java Project. It uses **Java Swing** for the GUI and **MySQL** for the database. It is a multithreaded server-client application. So you can have multiple instances of the client open at any point in time.

## Setup

The folder structure uses the original from the Eclipse IDE as it was directly pushed to GitHub, meaning the same goes for most of the set up, import the repo as is into your IDE and most things should already be good to go. To set it up you will need to configure the JDBC and manualy run the database creation method in BankDB.java, depending on your machine, you may need to adjust the port settings as well to avoid the error saying this port is in use. I was okay using port 9999. After that's all done then you can start the server at BankServer.java followed by the client from the BankApp.java file. 

> This is a software that may still undergo changes, not perfect at all but the code can be used to learn a lot of concepts. I hope it will be useful to you. Contributions are also welcome. Please see issues.
