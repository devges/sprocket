package com.company;


public class Main {

    public static void main(String[] args){
        /*Input data is set here*/
        int[] testData1 = {4,0,6,0,7,0,3};
        block_fill(testData1);

    }

    public static int block_fill(int[] blockHeights) {
        /*Calculates 1st and last occurrence of each height (used for later calculations: block_count).*/
        int maxHeightLimit = maxVal(blockHeights) + 1;
        int numOfCols = blockHeights.length;

        int[] heightOccursLast  = new int[maxHeightLimit];
        int[] heightOccursFirst = new int[maxHeightLimit];

        for (int i=0; i < maxHeightLimit; i++) {
            heightOccursFirst[i] = numOfCols;
        }

        for (int col=0; col < numOfCols; col++) {
            for (int row=0; row <= blockHeights[col]; row++) {
                heightOccursLast[row] = col;
                if (col < heightOccursFirst[row]) {
                    heightOccursFirst[row] = col;
                }
            }
        }
        return block_count(blockHeights, heightOccursFirst, heightOccursLast);
    }

    public static int block_count(int[] blockHeights, int[] heightOccursFirst, int[] heightOccursLast) {
        /*Counts the number of blocks filled with liquid; Creates char[row][col] matrix with printing characters*/
        char solid  = '|';  //symbols used for printing diagram
        char liquid = '~';
        char gas    = ' ';

        int count     = 0;  // returned result: A count of blocks filled with liquid.
        int numOfCols = blockHeights.length;
        int numOfRows = heightOccursFirst.length;

        int[] countByCol    = new int[numOfCols];
        int[] countByRow    = new int[numOfRows];

        char[][] cellRowCol = new char[numOfRows][numOfCols];
//        char[][] cellColRow = new char[numOfCols][numOfRows];       //redundant with cellRowCol

        for (int col=0; col < numOfCols; col++) {
            for (int row = 1; row < numOfRows; row++) {
                if (row <= blockHeights[col]) {
                    // if a cell's height is at or below the column of solid blocks, then its solid
                    cellRowCol[row][col] = solid;
//                    cellColRow[col][row] = solid;
                } else if ((heightOccursFirst[row] < col) && (col < heightOccursLast[row])) {
                    // if a cell's height is between solid blocks (but not solid), then its liquid
                    count++;
                    countByCol[col]++;
                    countByRow[row]++;
                    cellRowCol[row][col] = liquid;
//                    cellColRow[col][row] = liquid;
                } else {
                    cellRowCol[row][col] = gas;
//                    cellColRow[col][row] = gas;
                }
            }
        }


        printMatrix(cellRowCol, true);
//        printMatrix(cellColRow, false);               //redundant
        printArray(blockHeights, "Heights");
        printArray(countByCol,"countByCol");
        printArray(countByRow,"countByRow");
        System.out.println("count = " + count);
        return count;
    }

    public static void printArray(int[] values, String text) {
        /*Prints an array of integers, followed by some text*/
        System.out.print(" ");
        for (int n : values) {
            System.out.print(n + "  ");
        }
        System.out.println("// Data: " + text);
    }

    public static void printMatrix(char[][] data, boolean isRowThenCol) {
        /*Prints a matrix of characters (can transpose). */
        if (isRowThenCol) {
            for (int row = data.length - 1; row > 0; row--) {
                for (int col = 0; col < data[0].length; col++) {
                    System.out.print(" " + data[row][col] + " ");
                }
                System.out.println();
            }
        } else {
            for (int row = data[0].length - 1; row > 0; row--) {
                for (char[] colData : data) {
                    System.out.print(" " + colData[row] + " ");
                }
                System.out.println();
            }
        }
    }

    public static int maxVal(int [] values) {
        /*Returns max value in array of integers*/
        int max = Integer.MIN_VALUE;
        for (int n : values) {
            if (n > max) {
                max = n;
            }
        }
        return max;
    }
}


