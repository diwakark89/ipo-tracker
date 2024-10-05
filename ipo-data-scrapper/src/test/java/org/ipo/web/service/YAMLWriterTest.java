package org.ipo.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ipo.web.model.DataStore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.slf4j.Logger;

class YAMLWriterTest {


    private YAMLWriter yamlWriter;
    private Path testDirectory;
    private ObjectMapper mockMapper;
    private final String baseFolder ="tmp";
    @Mock
    private Logger log;

    @BeforeEach
    public void setUp() throws IOException {
        // Create a temporary directory for the test
        testDirectory = Files.createTempDirectory("testFolder");

        // Create a mock ObjectMapper
        mockMapper = mock(ObjectMapper.class);

        // Create a YAMLWriter instance with a mock ObjectMapper and max file age days
        yamlWriter = new YAMLWriter("7", mockMapper, testDirectory.toAbsolutePath().toString());
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Delete the test directory and its contents
        if (Files.exists(testDirectory)) {
            Files.walk(testDirectory)
                    .sorted(Comparator.reverseOrder()) // Sort in reverse order to delete files before the directory
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    @Test
    void testCreateYamlFile_FileDoesNotExist_CreatesNewFile() throws IOException {
        String fileName= UUID.randomUUID().toString();
        yamlWriter.createYamlFile(testDirectory.toString(), fileName);

        // Assert
        Path yamlFilePath = testDirectory.resolve(fileName);
        assertTrue(Files.exists(yamlFilePath), "YAML file should be created.");
    }

    @Test
    void testCreateYamlFile_FileExistsAndIsOlderThanMaxDays_DeletesAndCreatesNewFile() throws IOException {
        // Arrange
        Path yamlFilePath = testDirectory.resolve("IPO_Data.yml");
        Files.createFile(yamlFilePath);

        // Simulate file older than max days by setting last modified time
        long currentTime = System.currentTimeMillis();
        long futureTime = currentTime - (8 * 24 * 60 * 60 * 1000); // 8 days ago
        Files.setLastModifiedTime(yamlFilePath, java.nio.file.attribute.FileTime.fromMillis(futureTime));

        // Act
        yamlWriter.createYamlFile(testDirectory.toString(), "IPO_Data.yml");

        // Assert
        assertTrue(Files.exists(yamlFilePath), "YAML file should be created after deletion.");
    }

    @Test
    void testCreateYamlFile_FileExistsAndIsNotOlderThanMaxDays_DoesNotDeleteFile() throws IOException {
        // Arrange
        Path yamlFilePath = testDirectory.resolve("IPO_Data.yml");
        Files.createFile(yamlFilePath);

        // Simulate file not older than max days
        long currentTime = System.currentTimeMillis();
        long futureTime = currentTime - (5 * 24 * 60 * 60 * 1000); // 5 days ago
        Files.setLastModifiedTime(yamlFilePath, java.nio.file.attribute.FileTime.fromMillis(futureTime));

        // Act
        yamlWriter.createYamlFile(testDirectory.toString(), "IPO_Data.yml");

        // Assert that the file still exists and hasn't been deleted
        assertTrue(Files.exists(yamlFilePath), "YAML file should still exist.");
    }

    @Test
    void testReadYaml_FileExists_ReturnsDataStore() throws IOException {
        String folderName="LISTED";
        String fileName="IPO_Data.yml";
        // Arrange
        DataStore expectedDataStore = new DataStore(new HashSet<>());
        when(mockMapper.readValue(any(File.class), eq(DataStore.class))).thenReturn(expectedDataStore);

        // Create the YAML file to be read
        File yamlFile = new File(testDirectory.toFile(), folderName + File.separator + "IPO_Data.yml");
        yamlFile.getParentFile().mkdirs(); // Create folder structure
        Files.createFile(yamlFile.toPath());

        // Act
        DataStore result = yamlWriter.readYaml(folderName, fileName);

        // Assert
        assertEquals(expectedDataStore, result, "The DataStore should match the expected one.");
        verify(mockMapper, times(1)).readValue(yamlFile, DataStore.class);
    }

    @Test
    void testReadYaml_FileDoesNotExist_ReturnsEmptyDataStore() throws IOException {
        String folderName="LISTED";
        String fileName="IPO_Data.yml";
        // Act
        DataStore result = yamlWriter.readYaml(folderName, fileName);

        // Assert
        assertEquals(new DataStore(new HashSet<>()), result, "Should return an empty DataStore when file does not exist.");
        verify(mockMapper, never()).readValue(any(File.class), eq(DataStore.class));
    }

    @Test
    void testReadYaml_ReadFails_ReturnsEmptyDataStore() throws IOException {
        String folderName="LISTED";
        String fileName="IPO_Data.yml";
        // Arrange
        File yamlFile = new File(testDirectory.toFile(), folderName + File.separator + fileName);
        yamlFile.getParentFile().mkdirs(); // Create folder structure
        Files.createFile(yamlFile.toPath());

        // Simulate IOException when reading the file
        doThrow(new IOException("Read error")).when(mockMapper).readValue(yamlFile, DataStore.class);

        // Act
        DataStore result = yamlWriter.readYaml(folderName, fileName);

        // Assert
        assertEquals(new DataStore(new HashSet<>()), result, "Should return an empty DataStore on read error.");
        verify(mockMapper, times(1)).readValue(yamlFile, DataStore.class);
    }

    @Test
    void testWriteToYaml_Success() throws IOException {
        String folderName="LISTED";
        String fileName="IPO_Data.yml";
        // Arrange
        DataStore dataStore = new DataStore(new HashSet<>()); // Create an instance of your DataStore


        File yamlFile = new File(testDirectory.toFile(), folderName + File.separator + fileName);
        yamlFile.getParentFile().mkdirs(); // Create folder structure
        Files.createFile(yamlFile.toPath());


        // Act
        yamlWriter.writeToYaml(folderName, fileName, dataStore);

        // Assert that mapper.writeValue was called with the correct arguments
        ArgumentCaptor<File> fileCaptor = ArgumentCaptor.forClass(File.class);
        verify(mockMapper).writeValue(fileCaptor.capture(), eq(dataStore));
        assertTrue(fileCaptor.getValue().exists(), "YAML file should exist after writing.");
    }
}
