package test;

//import com.vividsolutions.jts.geom.Geometry;
//import com.vividsolutions.jts.geom.Point;
//import com.vividsolutions.jts.operation.distance.DistanceOp;
import com.vividsolutions.jts.algorithm.Angle;
import com.vividsolutions.jts.geom.Coordinate;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axiom.om.util.Base64;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.json.JSONArray;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ramilu on 9/26/15.
 */

public class Sample {

    private static Log log = LogFactory.getLog(Sample.class);

//    public static void main1(String args[]){
//
//        String middleObject = "{'type':'Polygon'," +
//                             "'coordinates':[[" +
//                             "[40.695085999999996,-74.176555]," +
//                             "[40.695386420634726,-74.17641987791285]," +
//                             "[40.695113,-74.17620699999999]," +
//                             "[40.695001049367214,-74.17597597232088]," +
//                             "[40.69496800291467,-74.1764557629358]," +
//                             "[40.695085999999996,-74.176555]" +
//                             "]]}";
//
//        middleObject = "{'type':'Polygon','coordinates':[[[-74.17835712432861,40.69877128778169],[-74.18021321296692,40.69887703033527],[-74.17889356613159,40.69690856450983],[-74.17999863624573,40.69686789311501],[-74.17970895767212,40.6962334161409],[-74.18062090873718,40.69603819124087],[-74.18018102645874,40.695574529810585],[-74.1793441772461,40.69580229372344],[-74.17821764945984,40.695615201995],[-74.17740225791931,40.69512713414336],[-74.17684435844421,40.694427563987496],[-74.1763401031494,40.69454144800179],[-74.17640447616577,40.694655331821444],[-74.17618989944458,40.694712273658254],[-74.1763401031494,40.69491563696394],[-74.17614698410034,40.69493190600158],[-74.1759967803955,40.69482615718592],[-74.17335748672485,40.694362487320475],[-74.17238116264343,40.69367917847925],[-74.17210221290588,40.693776794457044],[-74.17210221290588,40.69393135279606],[-74.17314291000366,40.69467973547176],[-74.17337894439696,40.69531422724242],[-74.17260646820068,40.695924309785035],[-74.1726815700531,40.69614393813275],[-74.17288541793823,40.69612766939108],[-74.17441964149475,40.694891233400035],[-74.17595386505127,40.695184075576954],[-74.17612552642822,40.69508646166099],[-74.17628645896912,40.6953630340514],[-74.17588949203491,40.69529795829814],[-74.17618989944458,40.69567214301143],[-74.17652249336243,40.69579415931138],[-74.17670488357544,40.69568027743839],[-74.17685508728027,40.69578602489834],[-74.1767156124115,40.69585110017487],[-74.17474150657654,40.69762437699446],[-74.1747522354126,40.69778706057742],[-74.17505264282227,40.6978195972463],[-74.17708039283752,40.696030056857644],[-74.17708039283752,40.69593244418119],[-74.17726278305054,40.696030056857644],[-74.17693018913269,40.69645304346962],[-74.1782820224762,40.6968922959549],[-74.17829275131224,40.69701431002018],[-74.17842149734497,40.69715259235731],[-74.1785180568695,40.6971607266035],[-74.1787326335907,40.69746982722268],[-74.17861461639404,40.697534900854514],[-74.17872190475464,40.697648779557234],[-74.17881846427917,40.69761624280489],[-74.17888283729553,40.69824257249611],[-74.17835712432861,40.69877128778169]]]}";
//
//        Geometry northObjectGeometry = GeometryUtils.geometryFromJSON(middleObject);
//
//        Point point = GeometryUtils.createPoint(-74.177267d, 40.697673d);
//
//        DistanceOp distOp = new DistanceOp(point, northObjectGeometry);
////      distOp.nearestPoints();
//
//
//        try {
//            InputStream ist = new FileInputStream("/Users/ramilu/Desktop/POC2015.09/data/delete.txt");
//            BufferedReader in = new BufferedReader(new InputStreamReader(ist));
////            StringBuilder response = new StringBuilder();
//            String line;
//            while((line = in.readLine()) != null) {
//                String array[] = line.split(",");
//
//                Point p = GeometryUtils.createPoint(Double.parseDouble(array[0]), Double.parseDouble(array[1]));
//                System.out.println(northObjectGeometry.intersects(p));
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(northObjectGeometry.getCentroid().getY());
//
//        Point p = GeometryUtils.createPoint(40.69578, -74.1769);
//
//        northObjectGeometry.intersects(p);

//        Date userSpecifiedSourceDate;
//        String targetDataFormat = null;
//        // Format the Date to specified Format
//        FastDateFormat targetFormat;
//        long dateInMills;
//        Calendar calInstance;
//        String formattedNewDateValue = null;
//
//            targetDataFormat = "EEEE";
//            // Format the Date to specified Format
//            targetFormat = FastDateFormat.getInstance(targetDataFormat);
//            dateInMills = 1442500088000l;
//            calInstance = Calendar.getInstance();
//            calInstance.setTimeInMillis(dateInMills);
//            userSpecifiedSourceDate = calInstance.getTime();
//            formattedNewDateValue = targetFormat.format(userSpecifiedSourceDate);
//        System.out.println(formattedNewDateValue);
//
//        String name = "B21-1_Jetbridge_Outside_Batt";
//        name = name.substring(0,3);
//        System.out.println(name);
//
//        Pattern p = Pattern.compile("\\w(0)\\d+");
//        Pattern p2 = Pattern.compile("\\w(0)\\d+");
//
//        if(Pattern.compile("\\w(0)\\d+").matcher(name).find()){
//            System.out.println(name.replaceFirst("0",""));
//        }
//        if(Pattern.compile("\\W").matcher(name).find()){
//            System.out.println(name.replaceFirst("\\W",""));
//        }

//    }
//    private static double deg2rad(double deg) {
//        return (deg * Math.PI / 180.0);
//    }
//
//    private static double rad2deg(double rad) {
//        return (rad * 180 / Math.PI);
//    }
//
//    public static void main(String args[]){
//        test.Sample distanceCalculatorViaLatLongPOC = new test.Sample();
//        distanceCalculatorViaLatLongPOC.execute(new Object[]{40.69582795892822, -74.17696740390778, 40.69818946109949, -74.17923076257411});
//    }

//    private Object execute(Object[] data) {
//
//
//        double latitude = (Double) data[0];
//        double longitude = (Double) data[1];
//        double prevLatitude = (Double) data[2];
//        double prevLongitude = (Double) data[3];
//
//        int r = 6371000; // Radius of the earth in m
//
//
//        int R = 6371000;
//        System.out.println(latitude + ", "+ longitude + ", "+ prevLatitude + ", "+ prevLongitude + ", ");
//
//
//
///*
//        double theta = longitude - prevLongitude;
//        double dist = Math.sin(deg2rad(latitude)) * Math.sin(deg2rad(prevLatitude)) + Math.cos(deg2rad(latitude)) * Math.cos(deg2rad(prevLatitude)) * Math.cos(deg2rad(theta));
//
//        dist = Math.acos(dist);
//        dist = rad2deg(dist);
//        dist = dist * 60 * 1.1515;
//        dist = dist * 1609.344;
//
//        40.69582795892822, -74.17696740390778, 40.69818946109949, -74.17923076257411,
//        distanceMax: 324.5778161901858
//
//*/
//
///*
//        latitude = latitude * (Math.PI / 180);
//        prevLatitude = prevLatitude * (Math.PI / 180);
//        longitude = longitude * (Math.PI / 180);
//        prevLongitude = prevLongitude * (Math.PI / 180);
//
//        double dlon = prevLongitude - longitude;
//        double dlat = prevLatitude - latitude;
//        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) + Math.cos(latitude) * Math.cos(prevLatitude) *
//                                                             Math.sin(dlon / 2) * Math.sin(dlon / 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        double dist = R * c;
//*/
//
//
//        if (dist > 0.025) {
//            System.out.println("distanceMax: " + dist);
//        } else {
//            System.out.println("distanceMin: " + dist);
//        }
////        return r * c;
//        return dist;
//    }

//    public static void main(String args[]){
    //8.116553, 77.523679
//9.850047, 98.597177
        //2316.52
        //2322119


//54.432063, 19.669778
//59.971487, 29.958951
        //869.13
        //871946
//
//        double latitude = 54.432063;
//        double longitude = 19.669778;
//        double prevLatitude = 59.971487;
//        double prevLongitude = 29.958951;
//
//            int R = 6371000; // Radius of the earth in m
//            latitude = latitude * (Math.PI / 180);
//            prevLatitude = prevLatitude * (Math.PI / 180);
//            longitude = longitude * (Math.PI / 180);
//            prevLongitude = prevLongitude * (Math.PI / 180);
//            double dlon = prevLongitude - longitude;
//            double dlat = prevLatitude - latitude;
//            double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) + Math.cos(latitude) * Math.cos(prevLatitude) *
//                                                                 Math.sin(dlon / 2) * Math.sin(dlon / 2);
//            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//            System.out.println(R*c);

//        String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";
//        String source = "2016-02-23T12:05:00-06:00";
//        FastDateFormat userSpecificFormat = FastDateFormat.getInstance(dateFormat);
//        Date date = null;
//        try {
//            date = userSpecificFormat.parse(source);
//            long returnValue = date.getTime();
//            System.out.println(returnValue);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//    }


    public static void main(String args[]) {
        //        System.out.println(Angle.angle(new Coordinate(79.887458, 6.879721)));
        //        System.out.println(Angle.angle(new Coordinate(79.889154, 6.881264)));
        //
        //        System.out.println((Angle.angle(new Coordinate(0.0,0.0)) - Angle.angle(new Coordinate(11.362479, 9.874307))));
        //
        //        System.out.println(Angle.angle(new Coordinate(0.0,0.0), new Coordinate(11.362479, 9.874307)));
        //
        //        String textMessage = "<ns0:Envelope xmlns:ns0=\"http://www.wso2.com/cep/env/component\">\n"
        //                + "\t\t\t<ns0:Header>\n"
        //                + "\t\t\t\t<ns1:eventHeader xmlns:ns1=\"http://www.wso2.com/cep/env/header\" eventID=\"ID1\" eventName=\"wso2Event\">10</ns1:eventHeader>\n"
        //                + "\t\t\t</ns0:Header>\n" + "\t\t\t<ns0:Body>\n" + "\t\t\t\t<something>\n"
        //                + "\t\t\t\t\t<ns1:Schedule xmlns:ns1=\"http://www.wso2.com/cep/content/something\" company=\"wso2\" located=\"sri lanka\"><ns1:Equip carrIataCd=\"CO\"/></ns1:Schedule>\n"
        //                + "\t\t\t\t\t<ns1:Operate xmlns:ns1=\"http://www.wso2.com/cep/content/something\" company=\"wso22\" located=\"sri lankaa\"><ns1:Equip carrIataCd=\"CO\"/></ns1:Operate>\n"
        //                + "\t\t\t\t</something>\n" + "\t\t\t\t<something>\n"
        //                + "\t\t\t\t\t<ns1:Schedule xmlns:ns1=\"http://www.wso2.com/cep/content/something\" company=\"wso2222\" located=\"sri lanka\"><ns1:Equip carrIataCd=\"CO\"/></ns1:Schedule>\n"
        //                + "\t\t\t\t\t<ns1:Operate xmlns:ns1=\"http://www.wso2.com/cep/content/something\" company=\"wso22\" located=\"sri lankaa\"><ns1:Equip carrIataCd=\"CO\"/></ns1:Operate>\n"
        //                + "\t\t\t\t</something>\n" + "\t\t\t</ns0:Body>\n" + "\t\t</ns0:Envelope>";

//        sendBeaconGPSLogs();

        try {
            JSONArray jobj =  new JSONArray("[\n" + "  {\n" + "    \"context\": \"device-Counts-by-platforms\",\n"
                    + "    \"data\": [\n" + "      {\n" + "        \"group\": \"android\",\n"
                    + "        \"label\": \"android\",\n" + "        \"count\": 3\n" + "      },\n" + "      {\n"
                    + "        \"group\": \"ios\",\n" + "        \"label\": \"ios\",\n" + "        \"count\": 1\n"
                    + "      },\n" + "      {\n" + "        \"group\": \"windows\",\n"
                    + "        \"label\": \"windows\",\n" + "        \"count\": 1\n" + "      }\n" + "    ]\n"
                    + "  },\n" + "  {\n" + "    \"context\": \"device-Counts-by-ownership-types\",\n"
                    + "    \"data\": [\n" + "      {\n" + "        \"group\": \"BYOD\",\n"
                    + "        \"label\": \"BYOD\",\n" + "        \"count\": 3\n" + "      },\n" + "      {\n"
                    + "        \"group\": \"COPE\",\n" + "        \"label\": \"COPE\",\n" + "        \"count\": 2\n"
                    + "      }\n" + "    ]\n" + "  }\n" + "]");
//            System.out.println("" + jobj.getJSONObject(0).getString("filteringGroups"));
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }

    }

    public static void sendBeaconGPSLogs(){
        //        String urlString = "https://api.ual-mobile.com:443/LocationEvent/PublishAirportOpsLocationEven";
        String urlString = "https://192.168.1.3:9443/endpoints/deleteHttpReceiver";
        HttpsURLConnection urlConnection=null;
        StringBuilder sb = new StringBuilder();

        System.out.println("init");

        try {
            //            String urlString = "http://localhost:9763/endpoints/deleteHttpReceiver";
            //            HttpsURLConnection urlConnection = null;
            //            StringBuilder sb = new StringBuilder();

            System.out.println("init");

            try {
                URL url = new URL(urlString);
                String message = "{\n" +
                        "   \"sensorData\": {\n" +
                        "\n" +
                        "           \"timestamp\": 19900813115534,\n" +
                        "           \"powerSaved\": false,\n" +
                        "           \"id\": 503,\n" +
                        "           \"name\": temperature,\n" +
                        "           \"long\": 90.34344,\n" +
                        "           \"lat\": 20.44345,\n" +
                        "           \"humidity\": 2.3,\n" +
                        "           \"temp\": 20.44345\n" +
                        "       }\n" +
                        "   }\n" +
                        "}";

                System.out.println("init2");

                //                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                //                InputStream caInput = new BufferedInputStream(this.getAssets().open("api_ual-mobile_com.crt"));
                //                Certificate ca = cf.generateCertificate(caInput);
                //                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
                //                String keyStoreType = KeyStore.getDefaultType();
                //                KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                //                keyStore.load(null, null);
                //                keyStore.setCertificateEntry("ca", ca);
                //                String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                //                TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                //                tmf.init(keyStore);
                //                SSLContext context1 = SSLContext.getInstance("TLS");
                //                context1.init(null, tmf.getTrustManagers(), null);
                //                connection.setSSLSocketFactory(context1.getSocketFactory());

//                Authenticator.setDefault(new Authenticator(){
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication("admin","admin".toCharArray());
//                    }});
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setInstanceFollowRedirects(false);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("charset", "utf-8");
                connection.setRequestProperty("Content-Length", "" + Integer.toString(message.length()));
//                connection.setRequestProperty("Authorization", "basic " +
//                        Base64.encode("admin:admin".getBytes()));
                //                String basicAuth = "Basic " + new String(Base64.encode("Ladmin:test##test123".getBytes(),Base64.NO_WRAP ));
                //                connection.setRequestProperty ("Authorization", basicAuth);

                connection.setUseCaches(false);

                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());

                wr.writeBytes(message);
                wr.flush();
                wr.close();

                int HttpResult = connection.getResponseCode();
                if (HttpResult == HttpsURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    System.out.println("" + sb.toString());
                } else {
                    System.out.println(connection.getResponseMessage());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


