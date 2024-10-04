package org.ipo.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.ipo.web.model.DataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;

public class YAMLWriter {
    private static final Logger LOG = LoggerFactory.getLogger(YAMLWriter.class);
    private final ObjectMapper mapper;

    private static final String FILE_NAME = "IPO_Data.yml";
    private final long maxFileAgeDays;

    public YAMLWriter() {
        mapper = new ObjectMapper(new YAMLFactory());
        this.mapper.findAndRegisterModules();
        this.maxFileAgeDays = Long.parseLong(System.getenv("FILE_AGE"));
    }

    public void createYamlFile(String folderPath) {
        try {
            // Create folder if it doesn't exist
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File yamlFile = getFile(folderPath);

            if (yamlFile.exists()) {
                if (isFileOlderThanDays(yamlFile)) {
                    LOG.info("File is older than {} days. Deleting and creating a new file.", maxFileAgeDays);
                    yamlFile.delete();
                    yamlFile.createNewFile();
                }
            } else {
                LOG.info("File does not exist. Creating a new file.");
                yamlFile.createNewFile();
            }
        } catch (IOException e) {
            LOG.info("Not able to check file status due to:{}", e.getMessage(), e);
        }
    }

    /**
     * Check if a file is older than a specified number of days.
     */
    private boolean isFileOlderThanDays(File file) throws IOException {
        Path filePath = file.toPath();
        BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class);
        Instant lastModifiedTime = attrs.lastModifiedTime().toInstant();
        Instant currentTime = Instant.now();
        Duration duration = Duration.between(lastModifiedTime, currentTime);

        return duration.toSeconds() > maxFileAgeDays;
    }


    public DataStore readYaml(String status) {
        File yamlFile = getFile(status);
        try {
            return mapper.readValue(yamlFile, DataStore.class);
        } catch (IOException ex) {
            LOG.error("Not able to read file:{} due to : {}", yamlFile.getName(), ex.getMessage(), ex);
            return new DataStore(new HashSet<>());
        }
    }

    public void writeToYaml(String status, DataStore dataStore) {
        if(!status.equalsIgnoreCase("listed")){
            createYamlFile(status);
        }
        File yamlFile = getFile(status);
        try {
            mapper.writeValue(yamlFile, dataStore);
        } catch (IOException ex) {
            LOG.error("Not able to write yamlFile:{} due to :{} ", yamlFile.getName(), ex.getMessage(), ex);
        }
    }

    private File getFile(String status) {
        return  new File(status + File.separator + FILE_NAME);
    }
}
