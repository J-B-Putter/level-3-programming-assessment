# Plan for Testing the Program

The test plan lays out the actions and data I will use to test the functionality of my program.

Terminology:

- **VALID** data values are those that the program expects
- **BOUNDARY** data values are at the limits of the valid range
- **INVALID** data values are those that the program should reject

---

## Movement Test

User input on the keyboard and on the buttons are used for player movement and is functional. 

### Test Data To Use

Key listeners and Action listeners. 

### Expected Test Result

Keyboard input and button input will control player movement.  

---

## Boundary Test

Player movement is limited by the size of the map. Appropriate Button(s) are hidden **only** if the player reaches the edge of the map. 

### Test Data To Use

Movement Functions. 

### Expected Test Result

Player movement should not exceed the boundaries of the map and the appropriate button(s) should be hidden at appropriate times.  

---

## Random Treasure Placement

Treasure placement is random with each run of the program. 

### Test Data To Use

Random functions and coordinate placement.

### Expected Test Result

Treasure coordinates should be different with each program run. 

___


