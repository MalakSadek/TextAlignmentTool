import java.util.ArrayList;
/**
 *
 * Specialized alignment class for center alignment, it inherits from the general alignment abstract class.
 *
 * @author Malak Sadek
 *
 */
public class CenterAlign extends Align {

    /**
     * Constructor, calls Align's general constructor.
     */
    CenterAlign(String file_name, int limit) {
        super(file_name, limit);
    }

    /**
     * CenterAlign's implementation of the abstract print method.
     * @param lines contains the lines of text that have been split according to the line length
     * padSpaces is called before printing to create the center alignment
     */
    void print(ArrayList<String> lines) {
        for (String line : lines) {
            System.out.print(padSpaces(line) + "\n");
        }
    }

    /**
     * Pads the lines with spaces to create a center alignment effect.
     * @param line contains a line of text that has been split according to the line length
     * @return Returns the same line after it has been padded to create center alignment
     * The floor and ciel methods in Math were found on: https://www.javacodex.com/Math-Examples/Floor-and-Ceiling-Example
     */
    @Override
    String padSpaces(String line) {
        String line_out = "";

        //Calculates the remaining spaces between the maximum line length and current line length and divides it by 2
        double spaces = (this.getLimit() - line.length()) / 2.0;

        //If there are an odd number of spaces, we add the extra space to the beginning of the text by rounding up using ceil
        String before = " ".repeat((int) Math.max(0, Math.ceil(spaces)));
        //We then add the other half to the end of the text by rounding down using floor
        String after = " ".repeat((int) Math.max(0, Math.floor(spaces)));

        //line_out is then created with the left spaces, text, then right spaces
        line_out = line_out + before;
        line_out = line_out + line;
        line_out = line_out + after;

        return line_out;
    }
}
