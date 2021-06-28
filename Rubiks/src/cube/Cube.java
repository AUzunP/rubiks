package cube;

import java.util.ArrayList;
import java.util.Arrays;

public class Cube {

	public ArrayList<Character> faces = new ArrayList<Character>(Arrays.asList('F', 'R', 'L', 'B', 'U', 'D'));
	public ArrayList<String> movements = new ArrayList<String>(Arrays.asList("F'", "F", "R'", "R",
			"U'", "U", "L'", "L", "B'", "B", "D'", "D", "CCW", "CW", "M'", "M"));
	//S at the end denotes a switching of order of the cells
	public String[][] movementSets = {/*F'*/{"U3RS", "L3C", "D1RS", "R1C"}, /*F*/{"R1CS", "D1R", "L3CS", "U3R"}, /*R'*/{"F3C", "D3CS", "B1CS", "U3C"}, 
			/*R*/{"U3CS", "B1CS", "D3C", "F3C"}, /*U'*/{"F1R", "R1R", "B1R", "L1R"}, /*U*/{"L1R", "B1R", "R1R", "F1R"}, /*L'*/{"U1CS", "B3CS", "D1C", "F1C"}, 
			/*L*/{"F1C", "D1CS", "B3CS", "U1C"}, /*B'*/{"U1R", "R3CS", "D3R", "L1CS"}, /*B*/{"L1C", "D3RS", "R3C", "U1RS"},  /*D'*/{"F3R", "L3R", "B3R", "R3R"}, 
			/*D*/{"R3R", "B3R", "L3R", "F3R"}, /*CCW*/{"3C", "1RS", "1C", "3RS"}, /*CW*/{"1R", "3CS", "3R", "1CS"}};
	
	/* Rules to Movement of Sides
	 * Movement of one side affects all sides of cube EXCEPT for opposite side
	 * i.e., F affects all sides except B
	 * 
	 * Movement requires temporary storage of each column/row being manipulated
	 * 
	 * [U3R, L3C, D1R, R1C]
	 * || F' Rotation
	 * 		- U	3rd row --> L 3rd column
	 * 		- L 3rd column --> D 1st row
	 * 		- D 1st row --> R 1st column
	 * 		- R 1st column --> U 3rd row
	 * 		- Rotate CCW around middle
	 * || F Rotation
	 * 		- U 3rd row --> R 1st column
	 * 		- R 1st column --> D 1st row
	 * 		- D 1st row --> L 3rd column
	 * 		- L 3rd column --> U 3rd row
	 * 		- Rotate CW around middle
	 * 
	 * [F3C, D3C, B1C, U3C]
	 * || R' Rotation
	 * 		- F 3rd column --> D 3rd column
	 * 		- D 3rd column --> B 1st column
	 * 		- B 1st column --> U 3rd column
	 * 		- U 3rd column --> F 3rd column
	 * 		- Rotate CCW around middle
	 * || R Rotation
	 * 		- F 3rd column --> U 3rd column
	 * 		- U 3rd column --> B 1st column
	 * 		- B 1st column --> D 3rd column
	 * 		- D 3rd column --> F 3rd column
	 * 		- Rotate CW around middle
	 * 
	 * [F1R, R1R, B1R, L1R]
	 * || U' Rotation
	 * 		- F 1st row --> R 1st row
	 * 		- R 1st row --> B 1st row
	 * 		- B 1st row --> L 1st row
	 * 		- L 1st row --> F 1st row
	 * 		- Rotate CCW around middle
	 * || U Rotation
	 * 		- F 1st row --> L 1st row
	 * 		- L 1st row --> B 1st row
	 * 		- B 1st row --> R 1st row
	 * 		- R 1st row --> F 1st row
	 * 		- Rotate CW around middle
	 * 
	 * [U1C, B3C, D1C, F1C]
	 * || L' Rotation
	 * 		- U 1st column --> B 3rd column
	 * 		- B 3rd column --> D 1st column
	 * 		- D 1st column --> F 1st column
	 * 		- F 1st column --> U 1st column
	 * 		- Rotate CCW around middle
	 * || L Rotation
	 * 		- U 1st column --> F 1st column
	 * 		- F 1st column --> D 1st column
	 * 		- D 1st column --> B 3rd column
	 * 		- B 3rd column --> U 1st column
	 * 		- Rotate CW around middle
	 * 
	 * [U1R, R3C, D3R, L1C]
	 * || B' Rotation
	 * 		- U 1st row --> R 3rd column
	 * 		- R 3rd column --> D 3rd row
	 * 		- D 3rd row --> L 1st column
	 * 		- L 1st column --> U 1st row
	 * 		- Rotate CCW around middle
	 * || B Rotation
	 * 		- U 1st row --> L 1st column
	 * 		- L 1st column --> D 3rd row
	 * 		- D 3rd row --> R 3rd column
	 * 		- R 3rd column --> U 1st row
	 * 		- Rotate CW around middle
	 * 
	 * [F3R, L3R, B3R, R3R]
	 * || D' Rotation
	 * 		- F 3rd row --> L 3rd row
	 * 		- L 3rd row --> B 3rd row
	 * 		- B 3rd row --> R 3rd row
	 * 		- R 3rd row --> F 3rd row
	 * 		- Rotate CCW around middle
	 * || D Rotation
	 * 		- F 3rd row --> R 3rd row
	 * 		- R 3rd row --> B 3rd row
	 * 		- B 3rd row --> L 3rd row
	 * 		- L 3rd row --> F 3rd Row
	 * 		- Rotate CW around middle
	 * 
	 * || M' Rotation
	 * 		- L' & R
	 * || M Rotation
	 * 		- L & R'
	 */
	
	private String[][][] cube = {
			{
				//front face of cube, top to bottom
				{"W1", "W2", "W3"}, {"W4", "W5", "W6"}, {"W7", "W8", "W9"}
			},
			
			{
				//right face of cube
				{"R1", "R2", "R3"}, {"R4", "R5", "R6"}, {"R7", "R8", "R9"}
			},
			
			{
				//left face of cube
				{"O1", "O2", "O3"}, {"O4", "O5", "O6"}, {"O7", "O8", "O9"}
			},
			
			{
				//back face of cube
				{"Y1", "Y2", "Y3"}, {"Y4", "Y5", "Y6"}, {"Y7", "Y8", "Y9"}
			},
			
			{
				//up face of cube
				{"B1", "B2", "B3"}, {"B4", "B5", "B6"}, {"B7", "B8", "B9"}
			},
			
			{
				//down face of cube
				{"G1", "G2", "G3"}, {"G4", "G5", "G6"}, {"G7", "G8", "G9"}
			}
	};
	
	public void printAllFaces() {
		for (int i = 0; i < faces.size(); i++) {
			printFace(faces.get(i));
		}
	}
	
	public void printFace(char face) {
		//{
		//print out given side of cube
		//}
		face = Character.toUpperCase(face);
		
		int side = faces.indexOf(face);
		System.out.println(Character.toUpperCase(face));
		
		System.out.println("-----------");
		
		for(String[] elementsArray: cube[side]) {
			System.out.print("|");
			for(String elements: elementsArray) {
				System.out.print(elements + " ");
			}
			System.out.print("|");
			System.out.println();
		}
		
		System.out.println("-----------");
		System.out.println();
		System.out.println();
	}
	
	public ArrayList<String> generateScramble(int numMoves) {
		//{
		//generate a list of moves to scramble the cube
		//}
		ArrayList<String> scrambleScript = new ArrayList<String>();
		
		for(int i = 0; i < numMoves; i++) {
			scrambleScript.add(movements.get((int)(Math.random()*10)));
		}
		
		return scrambleScript;
	}

	void rotateFace(String move) {
		move = move.toUpperCase();
		String[] toExecute;
		String face = "" + move.charAt(0);
		
		if(move.length() != 1) {
			//System.out.println("Clockwise rotation of " + move.charAt(0) + " face...");
			toExecute = movementSets[12].clone();
			
			String temp = "";
			for(int i = 0; i < 4; i++) {
				temp = toExecute[i];
				temp = face + temp;
				toExecute[i] = temp;
			}
			
		} else {
			//System.out.println("Counter clockwise rotation of " + move.charAt(0) + " face...");
			toExecute = movementSets[13].clone();
			
			String temp = "";
			for(int i = 0; i < 4; i++) {
				temp = toExecute[i];
				temp = face + temp;
				toExecute[i] = temp;
			}
		}
		
		executeMove(toExecute, move);
		
	}
	
	void selectMove(String move) {
		
		/*
		 * make array of all cells affected
		 * check input (move) and find index in array movements (at top)
		 * divide by 2 to get the list of rows and columns affected by move
		 * grab the cells taken through getRC()
		 * add to array
		 * if index of move is 13 or 14, deal with it by making it L' R or L R' and call again
		 */
		
		//{
		//temporarily hold the values that will be transferred
		//}
		//ArrayList<String[]> tempHold = new ArrayList<String[]>();
		//{
		//every 2 indices of movements corresponds to 1 index in movementSets
		//if command is NOT prime of movement, reverse the order of returned movementSets
		//}
		move = move.toUpperCase();

		int x = (int)(movements.indexOf(move));
		
		if (x > 13) {
			if(x == 15) {
				//M'
				selectMove("L'");
				selectMove("R");
				return;
			} else {
				//M
				selectMove("L");
				selectMove("R'");
				return;
			}
		}
		
		//{
		//tempMovementSets[x] in order to maintain movementSets values
		//}
		String[][] tempMovementSets = movementSets.clone();
		
		rotateFace(move);
		executeMove(tempMovementSets[x], move);
	}
	
	void executeMove(String[] movements, String move) {
		//accepts an array of strings that holds a move set and executes that move set
		
		ArrayList<String[]> tempHold = new ArrayList<String[]>();

		System.out.println("Face moving: " + move.charAt(0));
		
		System.out.println("Executing: " + Arrays.toString(movements));
		
		for(int i = 0; i < 4; i++) {
			//{
			//4 pieces of info for each move e.g., "U3R", "L3C", "D1R", "R1C"
			//}
			String temp = movements[i];
			
			String[] toAdd;
			
			//{
			//send info to get affected cells and add for temporary storage for future re-assignment
			//}
			toAdd = getRC(faces.indexOf(temp.charAt(0)), Character.getNumericValue(temp.charAt(1)-1), temp.charAt(2));
			if(temp.charAt(temp.length()-1) == 'S') {
				toAdd = reverse(toAdd);
			}
			
			tempHold.add(toAdd);
			
		}
		//{
		//set the proper rows or columns to their next value, indicated by tempHold.get(i-1)
		//the first 3 values passed to setRC allow it to access the rc (row or column) that is being changed, last value is the new value
		//each rc gets set to the previous one in the movementSet list; iterate three times
		//}
		for(int i = 1; i < 4; i++) {
			setRC(faces.indexOf(movements[i].charAt(0)), Character.getNumericValue(movements[i].charAt(1)-1), 
					movements[i].charAt(2), tempHold.get(i-1));
		}
		//{
		//instead of doing a circular linked list, just add this last one manually
		//}
		setRC(faces.indexOf(movements[0].charAt(0)), Character.getNumericValue(movements[0].charAt(1)-1), movements[0].charAt(2), tempHold.get(3));
		
	}
	
	void setRC(int face, int num, char rowCol, String[] setValue) {
		//{
		//grab proper row/column and change the values to ones given in setVal
		//}
		
		//must create a new array to use in order to get rid of last element which determined if it was a R or C so that reverse can work

		String[] setVal = {setValue[0], setValue[1], setValue[2]};
		
		if(rowCol == 'R') {
			//set row
			for(int x = 0; x < 3; x++) {
				
				cube[face][num][x] = setVal[x];
				//System.out.println(cube[face][num][x].toString());
			}
			
		}
		
		if(rowCol == 'C') {
			//set column
			for(int x = 0; x < 3; x++) {
				cube[face][x][num] = setVal[x];
				//System.out.println(cube[face][x][num].toString());
			}
			
		}
		
	}
	
	public String[] getRC(int face, int num, char rowCol) {
		//{
		//send back affected cells in groups of 3 (i.e., affected column, row)
		//}
		String[] temp = new String[4];
		
		if(rowCol == 'R') {
			
			//row
			for(int x = 0; x < 3; x++) {
				temp[x] = cube[face][num][x];
			}

		}
		
		if(rowCol == 'C') {
			
			//column
			for(int x = 0; x < 3; x++) {
				temp[x] = cube[face][x][num];
			}

		}
	
		String x = "";
		x += rowCol;
		
		temp[3] = x;
		return temp;
	}
	
	public void executeMoves(String[] moves) {
		for(int i = 0; i < moves.length; i++) {
			selectMove(moves[i]);
		}
	}
	
	String[] reverse(String[] toReverse) {
		//{
		//reverse the way needed
		//7 8 9 R --> 9 8 7 R
		//}
		
		String temp = toReverse[2];
		toReverse[2] = toReverse[0];
		toReverse[0] = temp;
		
		return toReverse;
	}
	
}
