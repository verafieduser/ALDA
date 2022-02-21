package se.su.dsv.MyAldaList.ProjectTwo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SLlight {
    //addStop


    public void importCSV(String fileName, int... parametersToUse){
        try (
            FileReader fileReader = new FileReader("src\\test\\resources\\" + fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            String line = bufferedReader.readLine();
            while((line = bufferedReader.readLine()) != null){
                String[] lines = line.split(",");

                switch (fileName) {
                    case "sl_stop_times":
                        addStopTimes(lines);
                        break;
                    
                    case "sl_trips":
                        addTrips(lines);
                        break;
                    case "sl_stops":
                        addStop(lines);
                        break;
                    case "sl_routes":
                        addRoutes(lines);
                        break;
                    default:
                        break;
                }


            }
        } catch (IOException e) {
            //TODO: handle exception
        }  catch (Exception e) {
            //TODO: handle
        }
    }

    private void addStop(String[] lines) {
        int stop_id = Integer.parseInt(lines[0]);
        String stop_name = lines[1];
        double stop_lat = Double.parseDouble(lines[2]);
        double stop_lon = Double.parseDouble(lines[3]);
        new SL_Stop(stop_id, stop_name, stop_lat, stop_lon);
    }

    private void addRoutes(String[] lines) {
        int route_id = Integer.parseInt(lines[0]);
        short route_short_name = Short.parseShort(lines[2]); 
        String route_long_name = lines[3];
        short route_type = Short.parseShort(lines[4]);
        new SL_Routes(route_id, route_short_name, route_long_name, route_type);
    }

    private void addTrips(String[] lines) {
        int route_id = Integer.parseInt(lines[0]);
        int trip_id = Integer.parseInt(lines[2]);
        String trip_headsign = lines[4];
        new SL_Trips(route_id, trip_id, trip_headsign);
    }

    private void addStopTimes(String[] lines) {
        int trip_id = Integer.parseInt(lines[0]);
        String departure_time = lines[2];
        int stop_id = Integer.parseInt(lines[3]);
        short stop_sequence = Short.parseShort(lines[4]);
        new SL_Stop_Time(trip_id, departure_time, stop_id, stop_sequence);
    }

                // for(int i : parametersToUse){
                //     String parameter = lines[i]; 
                //     int parameterAsInt = tryIfStringIsInt(parameter);
                //     if(parameterAsInt == -1){
                //         //TODO: if it is string
                //     } else {
                //         //TODO: if it is int
                //     }
                // }
}