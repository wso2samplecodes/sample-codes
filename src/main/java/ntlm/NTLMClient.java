package ntlm;


import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.io.PrintWriter;

public class NTLMClient {
    public static void main(String[] args) {
        FileSystemManager fsManager;
        PrintWriter pw = null;
        OutputStream out = null;

        String output = "";
        String payloadContent = "";
        try {
            JSONObject jsonPayload = new JSONObject("{\n" +
                    "    \"content\" : \"test\",\n" +
                    "    \"fileName\" : \"file://Users/ramindu/Desktop/test.log\"\n" +
                    "}");
            String fileName = jsonPayload.getString("fileName");
            payloadContent = jsonPayload.getString("content");
            fsManager = VFS.getManager();
            if (fsManager != null) {
                FileObject fileObj = fsManager.resolveFile(fileName);
                // if the file does not exist, this method creates it, and the parent folder, if necessary
                // if the file does exist, it appends whatever is written to the output stream
                out = fileObj.getContent().getOutputStream(true);
                pw = new PrintWriter(out);
                pw.write(payloadContent);
                pw.flush();
                fileObj.close();
                fsManager.close();
            }
            output = "{\"status\": \"content processed successfully\"}";
        } catch (FileSystemException e) {
            e.printStackTrace();
            output = "{\"exception\": " + e.getMessage() + " - " + payloadContent + "}";
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }

        if (output.isEmpty()) {
            output = "{\"exception\": \" exception has ocurred \"}";
        }
        System.out.println(output);
    }
}
