package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger(); // logger used to manage levels of output

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");

        // instantiate a new parser object to parse command line args
        CommandLineParser parser = new DefaultParser();

        // define possible command line options
        Options clOptions = new Options();
        clOptions.addOption("i", "input", true, "Maze input filepath"); // -i flag
        clOptions.addOption("p", "input", true, "Path for validation"); // -p flag

        try {

            CommandLine commands = parser.parse(clOptions, args); // ensures valid flags given with clOptions

            if (commands.hasOption("i")){ // check if the -i flag was given

                String mazeFilePath = commands.getOptionValue("i"); // retrieve filepath from -i flag
                logger.info("**** Reading the maze from file " + mazeFilePath);

                Maze maze = new Maze(mazeFilePath); // create a new Maze object
                //maze.displayMaze();
                logger.info("**** Placing player on the maze entry point ");
                Instructor instructor = new Instructor(maze, true); // startWest = true; i.e. starting on the west wall 

                if (commands.hasOption("p")){
                    String unprocessedValidationPath = commands.getOptionValue("p");
                    String validationPath = instructor.factorizedToCanonical(unprocessedValidationPath);
                    logger.info("**** Validating path beginning from Western wall");
                    boolean res = instructor.validatePath(validationPath);
                    if (res) {
                        logger.info("**** Successfully traversed maze starting from Western wall");
                        System.out.println("correct path");
                    }
                    else {
                        maze = new Maze(mazeFilePath); // Reset the maze to its original state
                        instructor = new Instructor(maze, false); // startWest = false; i.e. starting on the east wall
                        res = instructor.validatePath(validationPath);
                        if (res){
                            logger.info("**** Successfully traversed maze starting from Eastern wall");
                            System.out.println("correct path");
                        }
                        else {
                            logger.info("**** Inputted path is not valid starting from either the East or West wall.");
                            System.out.println("incorrect path");
                        }
                    }
                }
                else {
                    logger.info("**** Computing path");
                    instructor.exploreMaze();

                    String factorized = instructor.getFactorized();
                    System.out.println(factorized);

                    logger.info("** End of MazeRunner");

                }

            }
            else { // no -i flag used in program call
                logger.error("No input file provided, please use the -i flag to specify maze filepath");
            }
        } 
        catch(Exception e) { // unexpected flag given; ex. -x flag
            logger.info("Went out of the bounds of the maze!");
            System.out.println("incorrect path");
        }

    }
}
