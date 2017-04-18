/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groupproject;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * This class is to provide basic features and methods to connect to aws.
 *
 * @author XiaoSong
 */
public class Connect {

    private AmazonS3 s3;//declare an AmazonS3 object;
    private UUID suffix = UUID.randomUUID();//provide a suffix for the bucket name since it does not allow for 
    private String bucketName = "java-" + suffix;
    private String key;
    private Bucket bucket;
    private List<String> keys;
    private HashMap<String, String> filelist = new HashMap<String, String>();
    private String downloadFile = "";
    String[] files = null;

    public Connect() {
        AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. "
                    + "Please make sure that your credentials file is at the correct "
                    + "location (~/.aws/credentials), and is in valid format.",
                    e);
        }
        s3 = new AmazonS3Client(credentials);//initiate an AmazonS3 instance
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        s3.setRegion(usWest2);
        if (s3.listBuckets().isEmpty()) {
            s3.createBucket(bucketName);
        } else {
            this.bucketName = s3.listBuckets().get(0).getName();
        }
        bucket = getTheBucket();
    }

    public Bucket getTheBucket() {
        bucket = s3.listBuckets().get(0);
        return bucket;
    }

    public List<String> getAllKeys() {
        ObjectListing objectListing = s3.listObjects(new ListObjectsRequest().withBucketName(bucketName).withPrefix("java"));
        keys = new LinkedList<String>();
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            keys.add(objectSummary.getKey());
        }
        return keys;
    }

    public String getAFile() {
        if (keys.size() != 0) {
            key = keys.get(keys.size() - 1);
            return key;
        }
        return null;
    }

    public String getTheKey() {
        return key;
    }

    public void upload(File f) {
        String upFile = "java-" + f.getName();
        s3.putObject(new PutObjectRequest(bucketName, upFile, f));
        this.key = upFile;
    }

    public void download(String toFile) throws IOException {
        S3Object object = s3.getObject(new GetObjectRequest(bucketName, key));
        BufferedReader reader = new BufferedReader(new InputStreamReader(object.getObjectContent()));
        StringBuffer sb = new StringBuffer("");
        String str = null;
        while ((str = reader.readLine()) != null) {
            sb.append(str + "\n");
        }
        reader.close();
        File f = new File(toFile);
        if (!f.exists()) {
            f.createNewFile();
        }
        FileWriter writer = new FileWriter(toFile);
        BufferedWriter bw = new BufferedWriter(writer);
        bw.write(sb.toString());
        bw.close();
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        Connect conn = new Connect();
        conn.getAllKeys().get(0);
        System.out.println("111"+conn.getAllKeys().get(0));
        System.out.println("上传之前文件们" + conn.getAllKeys());
        conn.upload(new File("Doc.jpg"));
        System.out.println("上传名字" + conn.getTheKey());
        System.out.println("上传之后文件们" + conn.getAllKeys());
        conn.download("kkk.jpg");
    }
}
