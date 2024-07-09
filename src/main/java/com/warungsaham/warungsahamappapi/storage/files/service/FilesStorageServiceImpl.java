package com.warungsaham.warungsahamappapi.storage.files.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    private static final Logger LOG = LoggerFactory.getLogger(FilesStorageServiceImpl.class);

    private final Path root = Paths.get("upload");

    @Override
    public void init() {
        LOG.info("Create Directory : {}", root);
        try {
            Files.createDirectories(root);
            LOG.info("Succes create Directory");
        } catch (IOException e) {
            LOG.error("Failed to Create Directory");
            throw new RuntimeException("Could not init folder");
        }
    }

    @Override
    public void save(MultipartFile file, String path, String newFileName) {
        LOG.info("Create File : {}", newFileName);
        try {
            Path dirPath = Paths.get(root + path);
            LOG.info("directory path: {}" , dirPath);

            Files.createDirectories(dirPath);
            LOG.info("Directory created");

            Files.copy(file.getInputStream(), dirPath.resolve(newFileName));
            LOG.info("Success Create File with path : {}" , dirPath);
            
        }catch (InvalidPathException e) {
            LOG.error("Invalid File Path");
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            LOG.error("Failed to Create File");
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Resource load(String filename , String path) {
        LOG.info("Load File : {}", filename);
        try {
            Path dirPath = Paths.get(root + path);
            Path filePath = dirPath.resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists() || resource.isReadable()){
                LOG.info("Success Load File : {}", filename);
                return resource;
            }else{
                LOG.error("File not readable or not exists");
                throw new RuntimeException("Cannot Read file");
            }

        } catch (IOException e) {
            LOG.error("Failed to Read file");
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public byte[] getFileByte(String filename, String path) {

        try {
            Path dirPath = Paths.get(root + path);
            Path filePath = dirPath.resolve(filename);
            return Files.readAllBytes(filePath);

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
}
