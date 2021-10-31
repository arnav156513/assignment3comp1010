package comp1010_s2_2021_ass3_for_students;
import java.lang.annotation.*;

/* Please DO NOT modify this class, it is required for
 * the UnitTests
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Graded {
	public String description();
	public int marks();
}
