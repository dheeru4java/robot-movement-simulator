# robot-movement-simulator

The robots have three possible movements:
•	turn right
•	turn left
•	advance
Robots are placed on a hypothetical infinite grid, facing a particular direction (north, east, south, or west) at a set of {x,y} coordinates, e.g., {3,8}, with coordinates increasing to the north and east.
The robot then receives a number of instructions, at which point the testing facility verifies the robot's new position, and in which direction it is pointing.
•	The letter-string "RAALAL" means: 
o	Turn right
o	Advance twice
o	Turn left
o	Advance once
o	Turn left yet again
•	Say a robot starts at {7, 3} facing north. Then running this stream of instructions should leave it at {9, 4} facing west.


## To Run the program just do -
	mvnw spring-boot:run

or 
	mvn clean install
and go to target folder and do
	java -jar simulator-0.0.1-SNAPSHOT.jar

It will start the application on port 8446, just access the application at endpoint http://localhost:8446/simulator


## Below is the sample POST request  -

curl --location --request POST 'http://localhost:8446/simulator' \
--header 'Content-Type: application/json' \
-d '{
  "starting_position":"7,3",
  "direction_facing":"north",
  "instruction":"RAALAL"
}'


## Below is the sample response of above request

{
    "response": {
        "final_position": "9,4",
        "direction_facing": "west"
    }
}

## All the required validation of request parameters are done
## Like if we pass invalid valus in instructuions e.g:

{
  "starting_position":"7,3",
  "direction_facing":"north",
  "instruction":"RAALALX"
}

## Then response will come as 400 (BAD Request):

{
    "statusMessage": "The instruction should contain characters: RLA"
}

