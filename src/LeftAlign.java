import java.util.ArrayList;
/**
 *
 * Specialized alignment class for left alignment, it inherits from the general alignment abstract class.
 *
 * @author Malak Sadek
 *
 */
public class LeftAlign extends Align {

    /**
     * Constructor, calls Align's general constructor.
     */
    LeftAlign(String file_name, int limit) {
        super(file_name, limit);
    }

    /**
     * LeftAlign's implementation of the abstract print method.
     * @param lines contains the lines of text that have been split according to the line length
     */
    void print(ArrayList<String> lines) {
        for (String line : lines) {
            System.out.print(line + "\n");
        }
    }

    /**
     * LeftAlign's implementation of the abstract padSpaces method.
     * It has no need for this method and throws an exception of it's used
     */
    @Override
    String padSpaces(String line) {
        throw new NullPointerException("Cannot use padding with left alignment");
    }
}
