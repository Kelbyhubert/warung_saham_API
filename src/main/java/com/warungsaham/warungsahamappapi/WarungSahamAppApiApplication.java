package com.warungsaham.warungsahamappapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.warungsaham.warungsahamappapi.storage.files.service.FilesStorageService;

import jakarta.annotation.Resource;

@EnableAspectJAutoProxy
@SpringBootApplication
public class WarungSahamAppApiApplication implements CommandLineRunner {

	@Resource
	FilesStorageService filesStorageService;

	public static void main(String[] args) {
		SpringApplication.run(WarungSahamAppApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		filesStorageService.init();
		
	}



}
