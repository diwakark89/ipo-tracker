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
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;

public class YAMLWriter {
    private static final Logger LOG = LoggerFactory.getLogger(YAMLWriter.class);
    private final ObjectMapper mapper;

    private final long maxFileAgeDays;

    private final String baseFolder;


    // Original constructor
    public YAMLWriter() {
        this(System.getenv("FILE_AGE"), new ObjectMapper(new YAMLFactory()),"tmp"); // Default to reading from the environment variable
    }

    // New constructor for testability
    public YAMLWriter(String maxFileAgeDays, ObjectMapper mapper,String baseFolder) {
        this.mapper=mapper;
        this.mapper.findAndRegisterModules();
        this.maxFileAgeDays = Long.parseLong(maxFileAgeDays);
        this.baseFolder=baseFolder;
    }

    public void createYamlFile(String pathToYaml, String fileName) throws IOException {
        Path yamlFilePath = Paths.get(pathToYaml, fileName);

        if (Files.exists(yamlFilePath) ){
            if(isOlderThanMaxDays(yamlFilePath)) {
                Files.delete(yamlFilePath);
                // Create a new YAML file
                Files.createFile(yamlFilePath);
            }
            return;
        }

        Files.createFile(yamlFilePath);

    }

    /**
     * Check if a file is older than a specified number of days.
     */
    private boolean isOlderThanMaxDays(Path filePath) throws IOException {
        LocalDate lastModifiedDate = Files.getLastModifiedTime(filePath).toInstant()
                .atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        return ChronoUnit.DAYS.between(lastModifiedDate, LocalDate.now()) > maxFileAgeDays;
    }


    public DataStore readYaml(String folderName, String fileName) {
        LOG.info("Reading YAML for :{} ",folderName);
        File yamlFile = getFile(folderName, fileName);
        try {
            if (yamlFile.exists()) {
                return mapper.readValue(yamlFile, DataStore.class);
            } else {
                LOG.info("File does not exists:{} ", yamlFile.getAbsolutePath());
                return new DataStore(new HashSet<>());
            }

        } catch (IOException ex) {
            LOG.error("Not able to read file:{} due to : {}", yamlFile.getName(), ex.getMessage(), ex);
            return new DataStore(new HashSet<>());
        }
    }

    public void writeToYaml(String folderName, String fileName, DataStore dataStore) {

        try {
            String folderPath = getFolderPath(folderName);
            createYamlFile(folderPath, fileName);
            File yamlFile = getFile(folderName, fileName);
            mapper.writeValue(yamlFile, dataStore);
        } catch (IOException ex) {
            LOG.error("Not able to write yamlFile:{} due to :{} ", fileName, ex.getMessage(), ex);
        }
    }

    private File getFile(String folderName, String fileName) {
        String folderPath = getFolderPath(folderName);
        createFolder(folderPath);
        return new File(folderPath + File.separator + fileName);
    }

    private void createFolder(String folderPath) {
        File folder = new File(folderPath);

        if (!folder.exists()) {
            LOG.info("Creating Folder: {} it does not exists", folder.getAbsolutePath());
            boolean mkdirs = folder.mkdirs();
            LOG.info("Does folder: {}, has been created: {}",  folder.getAbsolutePath(), mkdirs);
        }
    }

    private  String getFolderPath(String folderName) {
        return baseFolder + File.separator + folderName;
    }


}
