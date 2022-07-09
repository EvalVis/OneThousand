package utils;

public class Misc {
	
	public static boolean othersPassed(boolean[] array) {
		int passCount = 0;
		for(int i = 0; i < array.length; i++) {
			if(array[i]) passCount++;
			if(passCount > 1) return false;
		}
		return true;
	}
	
	public static boolean sameColor(int code1, int code2) {
		return (code1 % 4) == (code2 % 4); 
	}
	
	public static int findMaxIndex(int[] codes) {
		int max = 0;
		for(int i = 0; i < codes.length; i++) {
			if(codes[i] > max) max = i;
		}
		return max;
	}
	
	public static boolean allMinuesOne(int[] array) {
		for(int i = 0; i < array.length; i++) {
			if(array[i] != -1) return false;
		}
		return true;
	}
	
	public static boolean contains3Nines(String message) {
		String[] parts = message.split(",");
		int count = 0;
		for(int i = 0; i < parts.length; i++) {
			if(Integer.parseInt(parts[i]) / 4 == 0) count ++;
		}
		return count > 2;
	}
	
	public static int calculateScore(int[] codes) {
		System.out.println("INFO");
		for(int i = 0; i < codes.length; i++) System.out.println(codes[i]);
		int score = 0;
		for(int i = 0; i < codes.length; i++) {
			int division = codes[i] / 4;
			if(division == 0) score += 0;
			else if(division == 1) score += 2;
			else if(division == 2) score += 3;
			else if(division == 3) score += 4;
			else if(division == 4) score += 10;
			else if(division == 5) score += 11;
		}
		System.out.println("SCORE: " + score);
		return score;
	}

}
