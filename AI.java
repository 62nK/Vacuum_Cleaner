// Student name: Andrea Pinardi
// Student ID: 000 763 118
// Assignment #:01
// Due Date: 02/5/19
// Signature: Andrea Pinardi
// Score:

import java.util.ArrayList;
import java.util.Queue;

public class AI {

    // next()
    public static int next(int[][] environment, Location currentPosition, ArrayList<Integer> next_q, double orientation){
        switch (ControlPanel.AI){
            case 0:
                return nextHeuristic(environment, currentPosition, orientation);
            case 1:
                return nextHeuristicQueue(environment, currentPosition, next_q, orientation);
            case 2:
                return nextHeuristicWeighted(environment, currentPosition, orientation);
            case 3:
                return nextMinDistance(environment, currentPosition);
            default:
                return nextRand(currentPosition);
        }
    }

    private static int nextRand(Location currentPosition){
        int nextDirection=0;
        do {
            nextDirection = (int) Math.floor(Math.random() * 4);
        }while (!currentPosition.canMove(nextDirection));
        return nextDirection;
    }
    private static int nextHeuristic(int[][] environment, Location currentPosition, double orientation) {
        int nextDirection;
        do {
            int order = 1;  // needs to be odd
            do{
                order+=2;
                nextDirection = findOptimalHeuristic(getLocality(order, environment, currentPosition), currentPosition);
            }while (nextDirection == -1 && order<11);
            System.out.println("chose direction="+nextDirection);
        }while (!currentPosition.canMove(nextDirection));
        if(nextDirection==-1) {
            nextDirection = nextRand(currentPosition);
        }
        return nextDirection;
    }
    private static int nextHeuristicWeighted(int[][] environment, Location currentPosition, double orientation) {
        int nextDirection;
        do {
            int order = 1;  // needs to be odd
            do{
                order+=2;
                nextDirection = findOptimalHeuristicWeighted(getLocality(order, environment, currentPosition), currentPosition);
            }while (nextDirection == -1 && order<11);
            System.out.println("chose direction="+nextDirection);
        }while (!currentPosition.canMove(nextDirection));
        if(nextDirection==-1) {
            nextDirection = nextRand(currentPosition);
        }
        return nextDirection;
    }
    private static int nextHeuristicQueue(int[][] environment, Location currentPosition, ArrayList<Integer> next_q, double orientation) {
        int nextDirection=0;
        if(next_q.size()==0) {
            do {
                int order = 1;  // needs to be odd
                do {
                    order += 2;
                    nextDirection = findOptimalHeuristicQueued(getLocality(order, environment, currentPosition), next_q, currentPosition);
                } while (nextDirection == -1 && order < 11);
                if(next_q.contains(nextDirection))
                    next_q.remove(nextDirection);
                System.out.println("choose direction=" + nextDirection);
                System.out.println("size"+next_q.size());
            } while (!currentPosition.canMove(nextDirection));
            if (nextDirection == -1) {
                nextDirection = nextRand(currentPosition);
            }
        }
        else {
            do{
                if(next_q.isEmpty())
                    nextDirection = nextRand(currentPosition);
                else
                    nextDirection = next_q.remove(0);
                System.out.println("Pulled from the q: "+nextDirection);
            } while (currentPosition.canMove(nextDirection)==false);

        }
        return nextDirection;
    }
    private static int nextMinDistance(int[][] environment, Location currentPosition){
        Location solutionLocation = findMinCoord(environment, currentPosition);
        double[] distances = new double[4];
        initArray(-1, distances);
        int nextDirection;
        if(new Location(currentPosition.getCoordX(), currentPosition.getCoordY()).canMoveNorth()) {
            Location temp = new Location(currentPosition.getCoordX(), currentPosition.getCoordY());
            temp.moveNorth();
            distances[ControlPanel.DIRECTION_NORTH] = Location.getDistance(solutionLocation, temp);
        }
        if(new Location(currentPosition.getCoordX(), currentPosition.getCoordY()).canMoveSouth()) {
            Location temp = new Location(currentPosition.getCoordX(), currentPosition.getCoordY());
            temp.moveSouth();
            distances[ControlPanel.DIRECTION_SOUTH] = Location.getDistance(solutionLocation, temp);
        }
        if(new Location(currentPosition.getCoordX(), currentPosition.getCoordY()).canMoveWest()) {
            Location temp = new Location(currentPosition.getCoordX(), currentPosition.getCoordY());
            temp.moveWest();
            distances[ControlPanel.DIRECTION_WEST] = Location.getDistance(solutionLocation, temp);
        }
        if(new Location(currentPosition.getCoordX(), currentPosition.getCoordY()).canMoveEast()) {
            Location temp = new Location(currentPosition.getCoordX(), currentPosition.getCoordY());
            temp.moveEast();
            distances[ControlPanel.DIRECTION_EAST] = Location.getDistance(solutionLocation, temp);
        }
        nextDirection = indexOfMin(distances);
        if(nextDirection==-1)
            return nextRand(currentPosition);
        else
            return nextDirection;
    }

    private static int findOptimalHeuristic(int[][] matrix, Location currentPosition){
        int hn = heuristicNorth(matrix, currentPosition);
        int hs = heuristicSouth(matrix, currentPosition);
        int hw = heuristicWest(matrix, currentPosition);
        int he = heuristicEast(matrix, currentPosition);
        int h = findMax(new int[]{hn, hs, he, hw});
        System.out.printf("n=%d s=%d w=%d e=%d -- max=%d\n", hn, hs, hw, he, h);
        if(h==0)
            return -1;
        if(h==hs)
            return ControlPanel.DIRECTION_SOUTH;
        if(h==hn)
            return ControlPanel.DIRECTION_NORTH;
        if(h==he)
            return ControlPanel.DIRECTION_EAST;
        if(h==hw)
            return ControlPanel.DIRECTION_WEST;
        return -1;
    }
    private static int findOptimalHeuristicWeighted(int[][] matrix, Location currentPosition){
        int hn = heuristicNorthWeighted(matrix, currentPosition);
        int hs = heuristicSouthWeighted(matrix, currentPosition);
        int hw = heuristicWestWeighted(matrix, currentPosition);
        int he = heuristicEastWeighted(matrix, currentPosition);
        int h = findMax(new int[]{hn, hs, he, hw});
        System.out.printf("n=%d s=%d w=%d e=%d -- max=%d\n", hn, hs, hw, he, h);
        if(h==0)
            return -1;
        if(h==hs)
            return ControlPanel.DIRECTION_SOUTH;
        if(h==hn)
            return ControlPanel.DIRECTION_NORTH;
        if(h==he)
            return ControlPanel.DIRECTION_EAST;
        if(h==hw)
            return ControlPanel.DIRECTION_WEST;
        return -1;
    }
    private static int findOptimalHeuristicQueued(int[][] matrix, ArrayList<Integer> next_q, Location currentPosition){
        int hn = heuristicNorthWeighted(matrix, currentPosition);
        int hs = heuristicSouthWeighted(matrix, currentPosition);
        int hw = heuristicWestWeighted(matrix, currentPosition);
        int he = heuristicEastWeighted(matrix, currentPosition);
        int h = findMax(new int[]{hn, hs, hw, he});
        System.out.printf("n=%d s=%d w=%d e=%d -- max=%d\n", hn, hs, hw, he, h);
        if(h==0)
            return -1;
        if(isConcurrent(new int[]{hn, hs, hw, he}, h)){
            enqueueConcurrent(next_q, new int[]{hn, hs, hw, he}, h);
        }
        if(h==hs)
            return ControlPanel.DIRECTION_SOUTH;
        if(h==hn)
            return ControlPanel.DIRECTION_NORTH;
        if(h==he)
            return ControlPanel.DIRECTION_EAST;
        if(h==hw)
            return ControlPanel.DIRECTION_WEST;
        return -1;
    }

    private static int heuristicNorth(int[][] locality, Location currentPosition){
        int h=0;
        if(currentPosition.canMoveNorth())
            for (int i=0; i<locality.length/2; i++){
                for (int j=0; j<locality.length; j++) {
                    h+=locality[i][j]*(i+1);
                }
            }
        else
            h=-1;
        return h;
    }
    private static int heuristicSouth(int[][] locality, Location currentPosition){
        int h=0;
        if(currentPosition.canMoveSouth())
            for (int i=locality.length/2+1; i<locality.length; i++){
                for (int j=0; j<locality.length; j++) {
                    h+=locality[i][j]*(locality.length-i);
                }
            }
        else
            h=-1;
        return h;
    }
    private static int heuristicWest(int[][] locality, Location currentPosition){
        int h=0;
        if(currentPosition.canMoveWest())
            for (int j=0; j<locality.length/2; j++){
                for (int i=0; i<locality.length; i++) {
                    h+=locality[i][j]*(j+1);
                }
            }
        else
            h=-1;
        return h;
    }
    private static int heuristicEast(int[][] locality, Location currentPosition){
        int h=0;
        if(currentPosition.canMoveEast())
            for (int j=locality.length/2+1; j<locality.length; j++){
                for (int i=0; i<locality.length; i++) {
                    h+=locality[i][j]*(locality.length-j);
                }
            }
        else
            h=-1;
        return h;
    }
    private static int heuristicNorthWeighted(int[][] locality, Location currentPosition){
        int h=0;
        if(currentPosition.canMoveNorth()) {
            for (int i = 0; i < locality.length / 2; i++) {
                for (int j = 0; j < locality[i].length / 2; j++) {
                    h += locality[i][j] * (i + j);
                }
            }
            for (int i = 0; i < locality.length / 2; i++) {
                for (int j = locality[i].length / 2; j < locality[i].length; j++) {
                    h += locality[i][j] * (locality.length - j + i - 1);
                }
            }
        }
        else
            h=-1;
        return h;
    }
    private static int heuristicSouthWeighted(int[][] locality, Location currentPosition){
        int h=0;
        if(currentPosition.canMoveSouth()) {
            for (int i = locality.length / 2 + 1; i < locality.length; i++) {
                for (int j = 0; j < locality[i].length / 2; j++) {
                    h += locality[i][j] * (locality.length  - i + j  -1);
                }
            }
            for (int i = locality.length / 2 + 1; i < locality.length; i++) {
                for (int j = locality[i].length / 2; j < locality[i].length; j++) {
                    h += locality[i][j] * ( 2*(locality.length-1) - i - j);
                }
            }
        }
        else
            h=-1;
        return h;
    }
    private static int heuristicWestWeighted(int[][] locality, Location currentPosition){
        int h=0;
        if(currentPosition.canMoveWest()){
            for (int i = 0; i < locality.length / 2; i++) {
                for (int j = 0; j < locality[i].length / 2; j++) {
                    h += locality[i][j] * (i + j);
                }
            }
            for (int i = locality.length / 2; i < locality.length; i++) {
                for (int j = 0; j < locality[i].length / 2; j++) {
                    h += locality[i][j] * ( locality.length - i + j - 1);
                }
            }
        }
        else
            h=-1;
        return h;
    }
    private static int heuristicEastWeighted(int[][] locality, Location currentPosition){
        int h=0;
        if(currentPosition.canMoveEast()){
            for (int i = 0; i < locality.length / 2; i++) {
                for (int j = locality[i].length / 2 + 1; j < locality[i].length; j++) {
                    h += locality[i][j] * (locality.length + i - j - 1);
                }
            }
            for (int i = locality.length / 2; i < locality.length; i++) {
                for (int j = locality[i].length / 2 + 1 ; j < locality[i].length; j++) {
                    h += locality[i][j] * ( 2*(locality.length-1) - i - j);
                }
            }
        }
        else
            h=-1;
        return h;
    }
    private static int[][] getLocality(int order, int[][] environment, Location currentLocation){
        System.out.println("order:"+ order);
        int yOffset = currentLocation.getCoordY()-order/2;
        int xOffset = currentLocation.getCoordX()-order/2;
        int[][] locality = new int[order][order];
        initMatrix(0, locality);
        for (int i=0; i<order; i++)
            for (int j=0; j<order; j++)
                if(i+yOffset>=0 && j+xOffset>=0 && i+yOffset< ControlPanel.SIZEY && j+xOffset< ControlPanel.SIZEX)
                    locality[i][j]=environment[i+yOffset][j+xOffset];
        printMatrix(locality);
        return locality;
    }

    private static int findMax(int[] array){
        int max=array[0];
        for(int i=1; i<array.length; i++)
            if(array[i]>max)
                max=array[i];
        return max;
    }
    private static Location findMinCoord(int[][] matrix, Location currentPosition){
        Location minCoord = new Location(0, 0);
        double distanceToMin = ControlPanel.SIZEX*ControlPanel.SIZEY;
        for(int i = 0; i< ControlPanel.SIZEY; i++)
            for(int j = 0; j< ControlPanel.SIZEX; j++){
                if(matrix[i][j]==ControlPanel.DIRT) {
                    double tempDistance = Location.getDistance(currentPosition, new Location(j, i));
                    if (tempDistance < distanceToMin) {
                        distanceToMin = tempDistance;
                        minCoord.setCoord(j, i);
                    }
                }
            }
        System.out.println("minCoord= ("+minCoord.getCoordX()+","+minCoord.getCoordY()+")");
        return minCoord;
    }
    private static int indexOfMin(double[] array){
        double min=ControlPanel.SIZEY*ControlPanel.SIZEX;
        int indexOfMin=-1;
        for(int i=0; i<array.length; i++)
            if(array[i]<min && array[i]!=-1) {
                min = array[i];
                indexOfMin = i;
            }
        System.out.println("n"+array[0]+" s"+array[1]+" w"+array[2]+" e"+array[3]);
        System.out.println("direction chosen"+indexOfMin);
        return indexOfMin;
    }

    private static boolean isConcurrent(int[] array, int value){
        int count=0;
        for(int i=0; i<array.length; i++) {
            if (array[i] == value)
                count++;
        }
        return count>=2;
    }
    private static void enqueueConcurrent(ArrayList<Integer> next_q, int[] array, int value){
        for(int i=0; i<array.length; i++) {
            if (array[i] == value) {
                System.out.println("enqueued "+i);
                    next_q.add(i);
            }
        }
        System.out.println();
    }
    private static void initMatrix(int value, int[][] matrix){
        for (int i=0; i<matrix.length; i++)
            for (int j=0; j<matrix[i].length; j++)
                matrix[i][j]=value;
    }
    private static void initArray(double value, double[] array){
        for(int i=0; i<array.length; i++)
            array[i]=value;
    }
    private static void printMatrix(int[][] matrix){
        System.out.println("submat:");
        for (int i=0; i<matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++)
                System.out.print(matrix[i][j]);
            System.out.println();
        }
    }
}