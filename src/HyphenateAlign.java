import java.util.ArrayList;
/**
 *
 * Specialized alignment class for hyphenated alignment, it inherits from the general alignment abstract class.
 * It also includes the attribute alignment which sets the text alignment along with hyphening it
 * @author Malak Sadek
 *
 */
public class HyphenateAlign extends Align {

    private String alignment;

    /**
     * Constructor, calls Align's general constructor.
     */
    HyphenateAlign(String file_name, int limit) {
        super(file_name, limit);
    }

    /**
     * Setter for alignment, setting it separately outside the constructor allows for more flexibility.
     */
    void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    /**
     * HyphenateAlign's implementation of the abstract print method.
     * @param lines contains the lines of text that have been split and hyphenated according to the line length
     */
    void print(ArrayList<String> lines) {
        //Depending on the alignment, the method will directly print out the lines of text (if left alignment which is the default)
        //or it will create a right alignment or center alignment object and let that object pad the already hyphenated text,
        //creating the desired alignment with hyphenation
        switch(alignment) {
            case "L":
                for (String line : lines) {
                    System.out.print(line + "\n");
                }
                break;
            case "R":
                RightAlign r = new RightAlign(getFileName(), getLimit());

                for (String line : lines) {
                    System.out.print(r.padSpaces(line) + "\n");
                }

                break;
            case "C":
                CenterAlign c = new CenterAlign(getFileName(), getLimit());

                for (String line : lines) {
                    System.out.print(c.padSpaces(line) + "\n");
                }
                break;
            default:
                break;
        }
    }

    /**
     * HyphenateAlign overrides the splitString method implemented in the abstract Align class as it needs different functionality.
     * @param paragraphs contains the paragraphs of text found inside the supplied file
     * @return Returns an ArrayList of strings, each containing a single line from the original text that has been split appropriately.
     */
    @Override
    ArrayList<String> splitString(String[] paragraphs) {

        //Gets the line length from the class instance
        int limit = this.getLimit();
        ArrayList<String> lines = new ArrayList<>();

        //Iterates over each paragraph in the text
        for (String paragraph : paragraphs) {
            int prev = 0, index;

            //Each iteration removes a part of paragraph, and so if it's still larger than the limit, it means there's still lines to be split
            while (paragraph.length() > limit) {

                //Finds the index of first space starting from the previous search (prev+1 because using prev would get the same space each time)
                index = paragraph.indexOf(' ', prev + 1);

                //index == limit means that the limit falls on a space, no need to hyphenate, so we just split the line and remove it from paragraph, then continue
                if (index == limit) {
                    lines.add(paragraph.substring(0, index));
                    paragraph = paragraph.substring(index + 1);
                    prev = 0;
                }

                //In any other case where the index exceeds the limit, the word must be hyphenated
                else if (index > limit) {

                    //limit - prev <= 2 indicates that there is a small word that would wrap to new line,
                    //This condition is to avoid hyphening on a space (where it is not needed), by splitting at the previous word
                    if (limit - prev <= 2) {
                        lines.add(paragraph.substring(0, prev));
                        paragraph = paragraph.substring(prev + 1);
                    }

                    //This is the general case, we split at "limit - 1" to take "line length - 1" characters and add a hyphen to make "line length" characters
                    //and then remove from paragraph until the limit so that the rest of word still there and is wrapped to the new line and continue
                    else {
                        String buffer = paragraph.substring(0, limit - 1)
                        + '-';
                        lines.add(buffer);
                        paragraph = paragraph.substring(limit - 1);
                    }
                    prev = 0;
                }

                //index == -1 indicates that no spaces were found, this is either one big word or there is only one word remaining
                //we split at "limit - 1" to take "line length - 1" characters and add a hyphen to make "line length" characters
                //and then remove from paragraph until the limit so that the rest of word still there and is wrapped to the new line and continue
                else if (index == -1) {
                    String buffer = paragraph.substring(0, limit - 1)
                    + '-';
                    lines.add(buffer);
                    paragraph = paragraph.substring(limit - 1);
                }

                //updates previous each iteration
                else {
                    prev = index;
                }
                //If the remaining words are less than the limit, then we just add all of them and continue to the next paragraph
                if (paragraph.length() < limit) {
                    lines.add(paragraph);
                }
            }
        }
        //Now lines contains each line of text after they have been split according to the line length
        return lines;
    }

    /**
     * HyphenateAlign's implementation of the abstract padSpaces method.
     * It re-implements CenterAlign and RightAlign's versions depending on which alignment is used
     */
    @Override
    String padSpaces(String line) {
        String line_out = "";

        if (alignment.equals("R")) {

            //Calculates the maximum of zero or the remaining spaces between the maximum line length and current line length
            //and then pads the line with that number of spaces
            line_out = " ".repeat(Math.max(0, this.getLimit() - line.length()))
                    + line;

        } else if (alignment.equals("C")) {

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

        }
        return line_out;
    }
}
