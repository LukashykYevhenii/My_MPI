import mpi.MPI;
import mpi.Status;

public class MPIExample {

  public static void helloMPI(int rank, int size) {

    System.out.println("Hello world from <" + rank + "> of <" + size + ">");
    MPI.Finalize();
  }

  public static void messageMPI(int rank) {

    String message = "Hello i am " + rank + " processor!";
    byte[] bytes = message.getBytes();

    if (rank == 0) {
      MPI.COMM_WORLD.Send(bytes, 0, bytes.length, MPI.BYTE, 1, 0);
      System.out.println("Process " + rank + " sent message: " + message);
    } else if (rank == 1) {
      byte[] received = new byte[bytes.length];
      MPI.COMM_WORLD.Recv(received, 0, received.length, MPI.BYTE, 0, 0);
      String receivedMessage = new String(received);
      System.out.println("Process " + rank + " received message: " + receivedMessage);
    }
  }

  public static void messageEvenMPI(int rank, int size) {

    String message = "Hello i am " + rank + " processor!";
    byte[] bytes = message.getBytes();

    if (rank % 2 == 0) {
      if (rank + 1 != size) {
        MPI.COMM_WORLD.Send(bytes, 0, bytes.length, MPI.BYTE, 1, 0);
        System.out.println("Process " + rank + " sent message: " + message);
      }
    } else if(rank != 0) {
        byte[] received = new byte[bytes.length];
        MPI.COMM_WORLD.Recv(received, 0, received.length, MPI.BYTE, 0, 0);
        String receivedMessage = new String(received);
        System.out.println("Process " + rank + " received message: " + receivedMessage);
      }
    }


  public static void main(String[] args) {
    MPI.Init(args);
    int rank = MPI.COMM_WORLD.Rank();
    int size = MPI.COMM_WORLD.Size();

    //helloMPI(rank, size);
    //messageMPI(rank);
    messageEvenMPI(rank, size);
  }
}
