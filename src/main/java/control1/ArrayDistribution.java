package control1;

import java.util.Arrays;
import mpi.MPI;
import mpi.MPIException;

public class ArrayDistribution {

  public static void main(String[] args) throws MPIException {

    MPI.Init(args);
    int rank = MPI.COMM_WORLD.Rank();

    int[] mainArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // Головний масив

    int[] localArray = new int[mainArray.length / MPI.COMM_WORLD.Size()]; // Локальний масив

    MPI.COMM_WORLD.Scatter(mainArray, 0, localArray.length, MPI.INT, localArray,
        0, localArray.length, MPI.INT, 0); // Розподіл масива

    System.out.println("Rank " + rank + ": Local array: " + Arrays.toString(localArray));

    MPI.Finalize();
  }
}
