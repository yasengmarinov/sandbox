import java.util.*;

/**
 * Created by b06514a on 1/20/2017.
 */
public class Main {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String[] toPass = {String.valueOf(in.nextInt()), String.valueOf(in.nextInt())};
		PercolationStats.main(toPass);
	}
}
