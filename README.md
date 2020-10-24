# Pest_Application

The project consists of three architecture parts which are Android studio, AWS and MySql database. The open data of pests and weeds are stored in MySql, and AWS is a web service to connect Android studio and MySql.


For Android Studio
First,you need to download Android studio(https://developer.android.com/studio) and git file, then unzip the file. 
Secondly open the project and choose the pest application file which you just download from github.
After loading all code, you set up your simulator(recommend Pixel 3 XL API29).Then click the run button.
The simulator will pop up, you can see the UI screen and you enjoy this app!

For Amazon Web Service
1. Go to the Amazon Web Service website and sign up for an AWS account.
2. Log in to your new AWS account and click the RDS.
3. Click the database and click the create button to create your new database. 
4. In creating page, make your database public.
5. Wait a couple of minutes and the new database will turn to green.
6. Click the new database and view the detailed information. Copy the Endpoint & port number. 
7. Download the database development tool, MySQL Workbench.
8. Open MySQL Workbench and click the add button to set up a new connection.
9. Input the detailed information and set up the connection.
   (Hostname: The endpoint of your AWS database.
   Port: The port number of your AWS database.
   Set up a new database name and password.)
10. Click the new connection and view the operating board.
11. Now you can use SQL code to operate your database by CRUD (create, read, update and delete).


For deploy back end
1.download lambdafunctionAWS file 
2. copy each python code segemnt to AWS lambda function 
3.deploy lambda function and create restful API URL in AWS
4. Then you can use the restful API URL to get data in our database.
