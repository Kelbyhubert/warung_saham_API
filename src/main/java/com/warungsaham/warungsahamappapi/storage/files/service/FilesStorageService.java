package com.warungsaham.warungsahamappapi.storage.files.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
    
    public void init();
    public void save(MultipartFile file, String path , String newFilename);
    public Resource load(String filename , String path);
    public void deleteAll();
    public Stream<Path> loadAll();
    public byte[] getFileByte(String filename , String path);
}
