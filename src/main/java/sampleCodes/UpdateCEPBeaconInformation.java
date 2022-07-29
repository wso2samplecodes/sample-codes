package sampleCodes;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by ramindu on 3/21/16.
 */
public class UpdateCEPBeaconInformation {
    public static void main(String args[]) {
        try {

            String myDriver = "com.mysql.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost/poc_united_airways";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "root");

            String query = "SELECT * FROM beaconInformation;";
            PreparedStatement preparedStmt2 = conn.prepareStatement(query);

            ResultSet result = preparedStmt2.executeQuery();
            JSONObject jobj = new JSONObject();
            while (result.next()) {
                // create the java mysql update preparedstatement
                jobj.put("beaconType", result.getString("beaconType"));
                jobj.put("uuid", result.getString("uuid"));
                jobj.put("majorId", result.getInt("majorId"));
                jobj.put("minorId", result.getInt("minorId"));
                jobj.put("terminal", result.getString("terminal"));
                jobj.put("floor", result.getInt("floor"));
                jobj.put("timestamp", result.getLong("timestamp"));
                jobj.put("latitude", result.getDouble("latitude"));
                jobj.put("longitude", result.getDouble("longitude"));
                jobj.put("name", result.getString("name"));
                jobj.put("location", result.getString("location"));

            }
        } catch (Throwable t) {
            System.out.println("Error when sending the messages " + t.getMessage());
        } finally {
//            producer.close();
        }
    }
}
