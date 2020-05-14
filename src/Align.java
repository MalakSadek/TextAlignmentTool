import java.util.ArrayList;
/**
 *
 * An abstract alignment class that specific alignment classes inherit from.
 * It contains the attributes file_name (file path & name) and limit (line length)
 * The idea of using padding & splitting was obtained from https://howtodoinjava.com/java/string/how-to-left-right-or-center-align-string-in-java/
 * However, the implementation is completely different
 * @author Malak Sadek
 *
 */

public abstract class Align {

    private String file_name;
    private int limit;

    /**
     * Constructor.
     */
    Align(String file_name, int limit) {
        this.file_name = file_name;
        this.limit = limit;
    }

    /**
     * Getter for the variable limit as it's set to private.
     * @return limit (line length).
     */
    int getLimit() {
        return limit;
    }

    /**
     * Getter for the variable file_name as it's set to private.
     * @return file_name (file path and name).
     */
    String getFileName() {
        return file_name;
    }

    /**
     * Abstract methods for adding spaces and printing the text, each specific alignment class implements their own method.
     */
    abstract String padSpaces(String line);
    abstract void print(ArrayList<String> lines);

    /**
     * Reads the contents of the file whose name and path are stored in the class' file_name variable.
     * @return Returns an array of Strings, each string representing a paragraph of text from the file (from FileUtil).
     */
    String[] readFile() {

        String[] paragraphs = FileUtil.readFile(file_name);

        //If paragraphs returns as zero, then there was a problem reading the file or the file was empty
        if (paragraphs.length == 0) {
            System.out.print("usage: java AlignText file_name line_length [align_mode]");
            System.exit(1);
        }
        return paragraphs;
    }

    /**
     * Splits the text lines in different ways based on several cases. This is implemented as a concrete method as all specific alignment classes use the same one
     * @param paragraphs contains the paragraphs of text found inside the supplied file
     * @return Returns an ArrayList of strings, each containing a single line from the original text that has been split appropriately.
     */
    ArrayList<String> splitString(String[] paragraphs) {

        ArrayList<String> lines = new ArrayList<>();

        //Iterates over each paragraph in the text (obtained from lecture slides)
        for (String paragraph : paragraphs) {
            int prev = 0, index;

            //Each iteration removes a part of paragraph, and so if it's still larger than the limit, it means there's still lines to be split
            while (paragraph.length() > limit) {

                //Finds the index of first space starting from the previous search (prev+1 because using prev would get the same space each time)
                index = paragraph.indexOf(' ', prev + 1);

                //prev == 0 indicates that this is the first iteration, and index == -1 indicates that no spaces were found
                //This means that the paragraph is only one word that is longer than the line length, we put it as is and move onto the next paragraph
                if (index == -1 && prev == 0) {
                    lines.add(paragraph);
                    prev = 0;
                    //Ends current paragraph
                    paragraph = paragraph.substring(paragraph.length());
                }

                //index==limit indicates that a word has ended at the limit, so we split at the end of the word and remove the line from paragraph
                //index > limit && prev == 0 indicates that there is a word longer than the limit, but it is not the only word in the paragraph,
                //so we put it on a separate line and continue
                else if (index == limit || (index > limit && prev == 0)) {
                    lines.add(paragraph.substring(0, index));
                    paragraph = paragraph.substring(index + 1);
                    prev = 0;

                }

                //index > limit indicates that the limit is in the middle of a word, in that case we split at the previous word
                //and add the current word to a new line
                //index == -1 indicates that no spaces were found, but it's not the first word, which means it's the last word,
                //so we put it in a line on its own
                else if (index > limit || index == -1) {
                    lines.add(paragraph.substring(0, prev));
                    paragraph = paragraph.substring(prev + 1);
                    prev = 0;
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
}
