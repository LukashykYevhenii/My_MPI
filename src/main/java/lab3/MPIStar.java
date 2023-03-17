package lab3;

import java.util.Scanner;
import mpi.MPI;
import mpi.MPIException;
import mpi.Status;

public class MPIStar {

  static Scanner scanner;

  static {
    scanner = new Scanner(System.in);
  }

  public static void main(String[] args) throws MPIException {
//    MPI.Init(args);
//
//    int rank = MPI.COMM_WORLD.Rank();
//
//    if (rank == 0) {
//      int j = MPI.COMM_WORLD.Size() - 1;
//      int[] receivedData = new int[j];
//
//      for (int i = 0; i < j; i++) {
//        MPI.COMM_WORLD.Recv(receivedData, i, 1, MPI.INT, i + 1, 0);
//        System.out.println("Received data "+ rank+" from processor " + (i + 1) + ": " + receivedData[i]);
//      }
//    } else {
//      int[] data = new int[]{rank};
//      System.out.println("send " + rank + " to ");
//      MPI.COMM_WORLD.Send(data, 0, 1, MPI.INT, 0, 0);
//    }
//
//    MPI.Finalize();

    int i = 2;
    int j = 4;

    MPI.Init(args);

    int rank = MPI.COMM_WORLD.Rank();
    int size = MPI.COMM_WORLD.Size();
    String message = "Hello, i am " + rank + " processor!";
    byte[] bytes = message.getBytes();

    messageEvenMPI(rank, size);

    //starTopology(rank, bytes, i, j, message);

  }

  public static void messageEvenMPI(int rank, int size) {

    String message = "Hello i am " + rank + " processor!";
    byte[] bytes = {(byte) rank};

    if (rank == 0) {

      byte[] received = new byte[bytes.length];
      MPI.COMM_WORLD.Recv(received, 0, received.length, MPI.BYTE, size - 1, 0);
      String receivedMessage = new String(received);
      System.out.println("Process " + rank + " received message: " + receivedMessage);


      MPI.COMM_WORLD.Send(bytes, 0, bytes.length, MPI.BYTE, 1, 0);
      System.out.println("Process " + rank + " sent message: " + message);


    } else if (rank == size - 1) {
      byte[] received = {(byte) rank};

      MPI.COMM_WORLD.Send(received, 0, received.length, MPI.BYTE, 0, 0);
      System.out.println("Process " + rank + " sent message: " + message);

      MPI.COMM_WORLD.Recv(received, 0, received.length, MPI.BYTE, rank - 1, 0);
      String receivedMessage = new String(received);
      System.out.println("Process " + rank + " received message: " + receivedMessage);



    } else {
      byte[] received = {(byte) rank};

      MPI.COMM_WORLD.Send(received, 0, received.length, MPI.BYTE, rank + 1, 0);
      System.out.println("Process " + rank + " sent message: " + message);


      MPI.COMM_WORLD.Recv(received, 0, received.length, MPI.BYTE, rank - 1, 0);
      String receivedMessage = new String(received);
      System.out.println("Process " + rank + " received message: " + receivedMessage);

    }
  }


  private static void starTopology(int rank, byte[] bytes, int i, int j, String message) {
    if (rank == i) {
      System.out.println(rank);
      // Відправляємо данні процессу i
      MPI.COMM_WORLD.Send(bytes, 0, bytes.length, MPI.BYTE, 0, 0);
      System.out.println("Process " + i + " sent data to process " + 0 + " " + message);
    } else if (rank == 0) {

      System.out.println(rank);
      byte[] received = new byte[bytes.length];
      MPI.COMM_WORLD.Recv(received, 0, received.length, MPI.BYTE, i, 0);
      String receivedMessage = new String(received);
      System.out.println(
          "Process " + 0 + " received data from process " + i + ": " + receivedMessage);

      MPI.COMM_WORLD.Send(bytes, 0, bytes.length, MPI.BYTE, j, 0);
      System.out.println("Process " + i + " sent data to process " + j);


    } else if (rank == j) {
      byte[] received = new byte[bytes.length];
      MPI.COMM_WORLD.Recv(received, 0, received.length, MPI.BYTE, 0, 0);
      String receivedMessage = new String(received);
      System.out.println(
          "Process " + 0 + " received data from process " + i + ": " + receivedMessage);
    }
  }

//    int main(int argc, char* argv[])
//    {
//      int my_rank, size, message;
//      int j = 8;
//      int TAG = 0;
//      MPI_Status status;
//      MPI_Init(&argc, &argv);
//      MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);
//      MPI_Comm_size(MPI_COMM_WORLD, &size);
//      message = my_rank;
//      if ((j % 2) == 0) {
//        if (my_rank == 0) {
//          MPI_Send(&message, 1, MPI_INT, j - 1, TAG, MPI_COMM_WORLD);
//          printf("sent: Process %i to %i\n", message, j - 1);
//        }
//        else if (my_rank == j - 1) {
//          MPI_Recv(&message, 1, MPI_INT, 0, TAG, MPI_COMM_WORLD, &status);
//          printf("received: Process %i from %i\n", message, 0);
//
//          MPI_Send(&message, 1, MPI_INT, j, TAG, MPI_COMM_WORLD);
//          printf("sent: Process %i to %i\n", message, j - 1);
//        }
//        else if (my_rank == j) {
//          MPI_Recv(&message, 1, MPI_INT, j - 1, TAG, MPI_COMM_WORLD, &status);
//          printf("received: Process %i from %i\n", message, j);
//        }
//      }
//      else {
//        if (my_rank == 0) {
//          MPI_Send(&message, 1, MPI_INT, j, TAG, MPI_COMM_WORLD);
//          printf("sent: processor %i to %i\n", message, j);
//        }
//        else if (my_rank == j) {
//          MPI_Recv(&message, 1, MPI_INT, 0, TAG, MPI_COMM_WORLD, &status);
//          printf("received: processor %i from %i\n", message, 0);
//        }
//      }
//      MPI_Finalize();
//      return 0;
//    }


}

