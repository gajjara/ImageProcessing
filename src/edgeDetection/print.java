package edgeDetection;

public class print {
	public print(Class<?> obj) {
		System.out.print(obj);
	}
	public print(Object obj, boolean println) {
		if(println == true) {
			System.out.println(obj);
		}
		else {
			System.out.print(obj);
		}
	}
	
	public print(Object obj, boolean println, String desc) {
		if(println == true) {
			System.out.println(desc + obj);
		}
		else {
			System.out.print(desc + obj);
		}
	}
}
