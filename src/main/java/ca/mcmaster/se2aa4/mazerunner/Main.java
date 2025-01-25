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

        try {

            CommandLine commands = parser.parse(clOptions, args); // ensures valid flags given with clOptions

            if (commands.hasOption("i")){ // check if the -i flag was given

                String mazeFilePath = commands.getOptionValue("i"); // retrieve filepath from -i flag
                logger.info("**** Reading the maze from file " + mazeFilePath);

                Maze maze = new Maze(mazeFilePath); // create a new Maze object
                maze.displayMaze();

                logger.info("**** Placing character on the maze entry point ");
                Instructor instructor = new Instructor(maze); // create a new Instructor object

                logger.info("**** Computing path");
                logger.info("PATH NOT COMPUTED");
                instructor.exploreMaze();
                maze.displayMaze();
                logger.info("** End of MazeRunner");

            }
            else { // no -i flag used in program call
                logger.error("No input file provided, please use the -i flag to specify maze filepath");
            }
        } 
        catch(Exception e) { // unexpected flag given; ex. -x flag
            logger.error("Error... unable to parse CL arguments! Please use the provided flags ONLY! " + e.getMessage());
        }

    }
}
