package sampleCodes;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.sql.*;
import java.util.Arrays;

/**
 * Created by ramindu on 3/21/16.
 */
public class UpdateBeaconInformationCurlExecutor {
    public static void main(String args[]) {

        try {
            ProcessBuilder p = new ProcessBuilder("curl", "--show-error", "--request", "GET", "--header",
                                    "Accept: application/json",
                                    "https://cube.api.aero/atibeacon/beacons/1?airportCode=ORD&app_id=a0d001ee&app_key=44de7bf3fd8bbc4649300629ef07e1d4");
            final Process shell = p.start();
            InputStream shellIn = shell.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(shellIn);

            FileOutputStream outputStream = new FileOutputStream(
                    "/Users/ramindu/Desktop/delete.json");

            IOUtils.copy(bis, outputStream);
            bis.close();
            outputStream.close();

            InputStream ist = new FileInputStream("/Users/ramindu/Desktop/delete.json");

            BufferedReader in = new BufferedReader(new InputStreamReader(ist));
            int c;
            StringBuilder response = new StringBuilder();
            int line;

            int array = 0;
            int cnt = 0;
            int jsonObj = 0;
            //        line = in.read();

            String myDriver = "com.mysql.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost/poc_united_airways";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "root");

            int i=0;
            int j=0;

            while((line = in.read()) != -1) {
                if ('[' == (char)line){
                    array++;
                }
                if (']' == (char)line){
                    array--;
                }
                if ('{' == (char)line){
                    jsonObj++;
                }
                if ('}' == (char)line){
                    jsonObj--;
                    if(jsonObj == 0) {
                        response.append((char) line);
                    }
                }
                if(']' != (char)line && '[' != (char)line){
                    if(jsonObj != 0) {
                        response.append((char) line);
                    }
                }

                if(jsonObj == 0 && !"".equalsIgnoreCase(response.toString())){
                    String message = response.toString();
                    System.out.println("i: " + i++);
                    response = new StringBuilder();
                    JSONObject jobj = new JSONObject(message);
                    jobj.put("timestamp", System.currentTimeMillis());
//
                    String query = "SELECT * FROM beaconInformation where uuid = ? and majorId = ? and minorId = ?;";
                        PreparedStatement preparedStmt2 = conn.prepareStatement(query);
                        preparedStmt2.setString(1, jobj.getString("uuid"));
                        preparedStmt2.setInt(2, jobj.getInt("majorId"));
                        preparedStmt2.setInt(3, jobj.getInt("minorId"));

                        ResultSet result = preparedStmt2.executeQuery();
                        if (result.next()) {
                            // create the java mysql update preparedstatement
                            query = "update beaconInformation set beaconType = ?, terminal=?,floor=?, timestamp=?, latitude=?, longitude=?,"
                                    + "name=?, location=? where uuid = ? and majorId = ? and minorId = ?";
                            PreparedStatement preparedStmt = conn.prepareStatement(query);
                            preparedStmt.setString(1, jobj.getString("beaconType"));
                            preparedStmt.setString(2, jobj.getString("terminal"));
                            preparedStmt.setInt(3, jobj.getInt("floor"));
                            preparedStmt.setLong(4, jobj.getLong("timestamp"));
                            preparedStmt.setDouble(5, jobj.getDouble("latitude"));
                            preparedStmt.setDouble(6, jobj.getDouble("longitude"));
                            preparedStmt.setString(7, jobj.getString("name"));
                            preparedStmt.setString(8, jobj.getString("location"));
                            preparedStmt.setString(9, jobj.getString("uuid"));
                            preparedStmt.setInt(10, jobj.getInt("majorId"));
                            preparedStmt.setInt(11, jobj.getInt("minorId"));

                            // execute the java preparedstatement
                            preparedStmt.executeUpdate();
                            System.out.println("j: " + j++);
                        } else {
                            query = "insert into beaconInformation values (?,?,?,?,?,?,?,?,?,?,?)";
                            PreparedStatement preparedStmt = conn.prepareStatement(query);
                            preparedStmt.setString(1, jobj.getString("beaconType"));
                            preparedStmt.setString(2, jobj.getString("uuid"));
                            preparedStmt.setInt(3, jobj.getInt("majorId"));
                            preparedStmt.setInt(4, jobj.getInt("minorId"));
                            preparedStmt.setString(5, jobj.getString("terminal"));
                            preparedStmt.setInt(6, jobj.getInt("floor"));
                            preparedStmt.setLong(7, jobj.getLong("timestamp"));
                            preparedStmt.setDouble(8, jobj.getDouble("latitude"));
                            preparedStmt.setDouble(9, jobj.getDouble("longitude"));
                            preparedStmt.setString(10, jobj.getString("name"));
                            preparedStmt.setString(11, jobj.getString("location"));
                            preparedStmt.executeUpdate();
                        }
                }
            }



        } catch (Throwable t) {
            System.out.println("Error when sending the messages " + t.getMessage());
        } finally {
//            producer.close();
        }
    }
}
