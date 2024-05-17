package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import entities.User;

public class InitUser {

  public static User loadUser(File file) {

    if (file.exists()) {
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
        return (User) ois.readObject();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
        return null;
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }
    } else {
      System.out.println("No such user exist");
    }
    return null;
  }

  // saves user as a file . file anme is the user's ID.us
  public static File saveUser(User user) {
    String file_name = user.getID() + ".us";
    File file = new File(file_name);
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
      oos.writeObject(user);
      return file;
    } catch (IOException e) {
      System.out.println("Error creating User File");
      return null;
    }

  }
}
