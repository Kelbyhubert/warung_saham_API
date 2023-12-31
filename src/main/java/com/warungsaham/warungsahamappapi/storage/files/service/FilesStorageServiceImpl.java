package com.warungsaham.warungsahamappapi.storage.files.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    private final Path root = Paths.get("upload");

    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (Exception e) {
            throw new RuntimeException("Could not init folder");
        }
    }

    @Override
    public void save(MultipartFile file, String path, String newFileName) {
        try {
            Path dirPath = Paths.get(root + path);
            Files.createDirectories(dirPath);
            Files.copy(file.getInputStream(), dirPath.resolve(newFileName));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Resource load(String filename , String path) {
        try {
            Path dirPath = Paths.get(root + path);
            Path filePath = dirPath.resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists() || resource.isReadable()){
                return resource;
            }else{
                throw new RuntimeException("Cannot read file");
            }

        } catch (Exception e) {
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
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public byte[] getFileByte(String filename, String path) {
        // TODO Auto-generated method stub
        try {
            Path dirPath = Paths.get(root + path);
            Path filePath = dirPath.resolve(filename);
            return Files.readAllBytes(filePath);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
}
