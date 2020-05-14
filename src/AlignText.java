/**
 *
 * Driver class for the program, contains the main function.
 *
 * @author Malak Sadek
 *
 */


public class AlignText {

    /**
     * To avoid Magic Number warning on stacscheck style check.
     */
    public static final int NUMBER_OF_ARGUMENTS = 3;

    /**
     * Main function takes user inputs, validates them, then creates the appropriate object.
     * @param args - the command line arguments 'file_name line_length [align_mode]'
     */
    public static void main(String[] args) {

        //At least file name and line length should be specified, or else it's invalid
        if (args.length < 2) {
            System.out.print("usage: java AlignText file_name line_length [align_mode]");
            System.exit(1);
        }

        //Default alignment is left
        String file_name = args[0];
        String line_length = args[1];
        String alignment = "L";
        int limit;

        //If a third argument is supplied, then that becomes the new alignment
        if (args.length == NUMBER_OF_ARGUMENTS) {
            alignment = args[2];
        }

        //Checks whether the value entered for line length is a number
        //Try-catch block with exceptions obtained from: https://www.baeldung.com/java-check-string-number
        try {
            limit = Integer.parseInt(line_length);

            //Checks whether the value entered for line length is positive
            if (limit <= 0) {
                System.out.print("usage: java AlignText file_name line_length [align_mode]");
                System.exit(1);
            }

            //The following part creates the appropriate object based on the supplied alignment

            //If hyphening is involved, the default is left hyphening, however the user might choose right or centered hyphening by inputting 2 characters as shown
            if (alignment.contains("H")) {
                HyphenateAlign hyphenate = new HyphenateAlign(file_name, limit);
                switch(alignment) {
                    case "CH":
                    case "HC":
                        hyphenate.setAlignment("C");
                        break;
                    case "RH":
                    case "HR":
                        hyphenate.setAlignment("R");
                        break;
                    case "LH":
                    case "HL":
                    case "H":
                        hyphenate.setAlignment("L");
                        break;
                    default:
                        //If the alignment supplied is invalid
                        System.out.print("usage: java AlignText file_name line_length [align_mode]");
                        System.exit(1);
                }

                hyphenate.print(hyphenate.splitString(hyphenate.readFile()));

            //Other alignments
            } else {
                switch(alignment) {
                    case "L":
                        LeftAlign left = new LeftAlign(file_name, limit);
                        left.print(left.splitString(left.readFile()));
                        break;
                    case "R":
                        RightAlign right = new RightAlign(file_name, limit);
                        right.print(right.splitString(right.readFile()));
                        break;
                    case "C":
                        CenterAlign center = new CenterAlign(file_name, limit);
                        center.print(center.splitString(center.readFile()));
                        break;
                    default:
                        //If the alignment supplied is invalid
                        System.out.print("usage: java AlignText file_name line_length [align_mode]");
                        System.exit(1);
                }
            }

        } catch (NumberFormatException nfe) {
            System.out.print("usage: java AlignText file_name line_length [align_mode]");
            System.exit(1);
        } catch (NullPointerException np) {
            //Thrown if a program tries to use padding with left or hyphenated alignment (which is not allowed)
            System.out.print(np.getMessage());
            System.exit(1);
        }

        System.exit(0);
    }
}
