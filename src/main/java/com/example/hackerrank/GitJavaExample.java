package com.example.hackerrank;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class GitJavaExample {

    private static final String GIT_REPO_URL = "https://github.com/YOUR_GIT_REPO_URL.git";
    private static final String USERNAME = "YOUR_GIT_USERNAME";
    private static final String PASSWORD = "YOUR_GIT_PASSWORD";
    private static final String FILE_EXTENSION_JKS = ".jks";
    private static final String FILE_EXTENSION_PFX = ".pfx";
    private static final String KEYSTORE_TYPE_JKS = "JKS";
    private static final String KEYSTORE_TYPE_PKCS12 = "PKCS12";

    public static void main(String[] args) {
        try {
            // Clone the Git repository
            Git git = Git.cloneRepository()
                    .setURI(GIT_REPO_URL)
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(USERNAME, PASSWORD))
                    .setDirectory(File.createTempFile("temp", ""))
                    .call();

            // Get a list of all files in the repository
            List<File> files = getAllFiles(git.getRepository().getWorkTree());

            // Find all .jks and .pfx files
            List<File> keystoreFiles = new ArrayList<>();
            for (File file : files) {
                if (file.getName().endsWith(FILE_EXTENSION_JKS) || file.getName().endsWith(FILE_EXTENSION_PFX)) {
                    keystoreFiles.add(file);
                }
            }

            // List certificates in each keystore
            for (File keystoreFile : keystoreFiles) {
                KeyStore keystore = KeyStore.getInstance(
                        keystoreFile.getName().endsWith(FILE_EXTENSION_JKS) ? KEYSTORE_TYPE_JKS : KEYSTORE_TYPE_PKCS12);
                keystore.load(new FileInputStream(keystoreFile), PASSWORD.toCharArray());

                Enumeration<String> aliases = keystore.aliases();
                while (aliases.hasMoreElements()) {
                    String alias = aliases.nextElement();
                    Certificate certificate = keystore.getCertificate(alias);
                    System.out.println("Certificate alias: " + alias);
                    System.out.println("Certificate: " + certificate);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<File> getAllFiles(File parentDirectory) {
        List<File> files = new ArrayList<>();
        for (File file : parentDirectory.listFiles()) {
            if (file.isDirectory()) {
                files.addAll(getAllFiles(file));
            } else {
                files.add(file);
            }
        }
        return files;
    }
}