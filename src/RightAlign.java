import java.util.ArrayList;
/**
 *
 * Specialized alignment class for right alignment, it inherits from the general alignment abstract class.
 *
 * @author Malak Sadek
 *
 */
public class RightAlign extends Align {

    /**
     * Constructor, calls Align's general constructor.
     */
    RightAlign(String file_name, int limit) {
        super(file_name, limit);
    }

    /**
     * RightAligns's implementation of the abstract print method.
     * @param lines contains the lines of text that have been split according to the line length
     * padSpaces is called before printing to create the right alignment
     */
    void print(ArrayList<String> lines) {
        for (String line : lines) {
            System.out.print(padSpaces(line) + "\n");
        }
    }

    /**
     * Pads the lines with spaces to create a right alignment effect.
     * @param line contains a line of text that has been split according to the line length
     * @return Returns the same line after it has been padded to create right alignment
     * The repeat method was found on https://www.w3schools.com/jsref/jsref_repeat.asp
     * And the max method is part of the Math library, found on: https://www.w3schools.com/java/java_math.asp
     */
    @Override
    String padSpaces(String line) {

        //Calculates the maximum of zero or the remaining spaces between the maximum line length and current line length
        //and then pads the line with that number of spaces
        return " ".repeat(Math.max(0, this.getLimit() - line.length()))
        + line;
    }
}
