package com.aim.project.pwp.instance.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.aim.project.pwp.instance.Location;
import com.aim.project.pwp.instance.PWPInstance;
import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.interfaces.PWPInstanceReaderInterface;

public class PWPInstanceReader implements PWPInstanceReaderInterface {

  @Override
  public PWPInstanceInterface readPWPInstance(Path path, Random random) {

    BufferedReader bfr;
    try {
      bfr = Files.newBufferedReader(path);

      int count = 0;
      String line;
      Location depotLocation = null;
      Location homeLocation = null;
      List<Location> aoLocations = new ArrayList<>();

      while (!(line = bfr.readLine()).equals("EOF")) {
        count++;
        if (count == 4) {
          depotLocation = readLocation(line);
        } else if (count == 6) {
          homeLocation = readLocation(line);
        } else if (count >= 8) {
          aoLocations.add(readLocation(line));
        }
      }

      int numberOfLocations = aoLocations.size() + 2;
      Location[] locations = aoLocations.toArray(new Location[0]);

      return new PWPInstance(numberOfLocations, locations, depotLocation, homeLocation, random);

    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  private Location readLocation(String line) {
    String[] coordinate = line.split("\\s+");
    return new Location(Double.parseDouble(coordinate[0]), Double.parseDouble(coordinate[1]));
  }
}
