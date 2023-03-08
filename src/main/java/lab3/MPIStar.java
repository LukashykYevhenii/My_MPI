package lab3;

import mpi.MPI;

public class MPIStar {

  public static void main(String[] args) {
    MPI.Init(args);

    int rank = MPI.COMM_WORLD.Rank();

    if (rank == 0) {
      int numProcessors = MPI.COMM_WORLD.Size() - 1;
      int[] receivedData = new int[numProcessors];

      for (int i = 0; i < numProcessors; i++) {
        MPI.COMM_WORLD.Recv(receivedData, i, 1, MPI.INT, i + 1, 0);
        System.out.println("Received data "+ rank+" from processor " + (i + 1) + ": " + receivedData[i]);
      }
    } else {
      int[] data = new int[]{rank};
      System.out.println("send " + rank + " to ");
      MPI.COMM_WORLD.Send(data, 0, 1, MPI.INT, 0, 0);
    }

    MPI.Finalize();
  }
}

