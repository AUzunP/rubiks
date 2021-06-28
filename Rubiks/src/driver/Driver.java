package driver;

import cube.Cube;

public class Driver {

	public static void main(String[] args) {
		
		Cube cube = new Cube();
		
		//cube.generateScramble(10);
		
		String[] movesToExecute = new String[] {"F'"};
		
		cube.executeMoves(movesToExecute);
		//"f'", "f", "r'", "r", "l'", "l", "b'", "b", "u'", "u", "d'", "d", "m'", "m"
		
		cube.printAllFaces();
		
		System.out.println("test");
		
	}

}
