import java.util.Scanner;

public class Hello {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Leap year calculator by MaomiHz");
		System.out.println("*****Hello, MaomiHz!!!*****\n");
		
		
		Scanner s = new Scanner(System.in);
		int year;
		do {
			try {
				System.out.print("Type a Year! (type -1 to quit!)==> ");
				year = s.nextInt();
			} catch (Exception e) {
				System.out.println("Input is not correct!!!");
				continue;
			}
			if (year == -1) {
				System.out.println("Thank you for using!!!");
				break;
			}
			if ((year % 100 == 0 && year % 400 == 0) || (year % 4 == 0 && year % 100 != 0)) {
				System.out.println("This is a leap year!!!! ");
			} else {
				System.out.println("This is NOT a leap year!!!!!");
			}
		} while (true);
		
		
		
	}

}
