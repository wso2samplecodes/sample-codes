package das_analytics;

import org.apache.axiom.om.util.Base64;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.carbon.utils.multitenancy.MultitenantUtils;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class DASAnalytics {
    public static void main(String[] args) {
        System.out.println(generateTableUUID(-1234, "ORG_WSO2_ANALYTICS_APIM_ABNORMALBACKENDTIMEALERTSTREAM"));
    }

    public static String generateTableUUID(int tenantId, String tableName) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            DataOutputStream dout = new DataOutputStream(byteOut);
            dout.writeInt(tenantId);
            /* we've to limit it to 64 bits */
            dout.writeInt(tableName.hashCode());
            dout.close();
            byteOut.close();
            String result = Base64.encode(byteOut.toByteArray());
            result = result.replace('=', '_');
            result = result.replace('+', '_');
            result = result.replace('/', '_');
            /* a table name must start with a letter */
            return "ANX" + result;
        } catch (IOException e) {
            /* this will never happen */
            throw new RuntimeException(e);
        }
    }

//    /**
//     * Gets the actual table name from the database persist name for a table
//     *
//     * @param persistName the persisted table name for which the actual name needs to be inferred
//     * @return HTTP response containing the actual table name
//     * @throws AnalyticsException
//     */
//    @GET
//    @Produces({MediaType.TEXT_PLAIN})
//    @Path("tables/{persistName}/actualName")
//    public Response getTableActualName(@PathParam("persistName") String persistName,
//                                       @HeaderParam(AUTHORIZATION_HEADER) String authHeader)
//            throws AnalyticsException {
//        if (logger.isDebugEnabled()) {
//            logger.debug("Invoking getTableActualName for table : " + persistName);
//        }
//        String username = authenticate(authHeader);
//        String tenantDomain = MultitenantUtils.getTenantDomain(username);
//        List<String> tables = Utils.getAnalyticsDataAPIs().listTables(username);
//        try {
//            int tenantId = Utils.getRealmService().getTenantManager().getTenantId(tenantDomain);
//            for (String tableName : tables) {
//                String candidate = GenericUtils.generateTableUUID(tenantId, tableName);
//                if (candidate.equalsIgnoreCase(persistName)) {
//                    if (logger.isDebugEnabled()) {
//                        logger.debug("Call by " + username + " to getTableActualName for table "
//                                + persistName + " returned " + tableName);
//                    }
//                    return Response.ok(tableName).build();
//                }
//            }
//            return Response.status(Response.Status.NOT_FOUND).entity("The table '" + persistName
//                    + "' was not found.").build();
//        } catch (UserStoreException e) {
//            throw new AnalyticsException("Error while getting tenant ID for user: " + username + "["
//                    + e.getMessage() + "]", e);
//        }
//    }


}
