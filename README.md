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
