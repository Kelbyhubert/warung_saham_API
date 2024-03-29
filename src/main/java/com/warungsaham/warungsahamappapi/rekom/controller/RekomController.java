package com.warungsaham.warungsahamappapi.rekom.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.warungsaham.warungsahamappapi.global.response.ApiResponse;
import com.warungsaham.warungsahamappapi.rekom.dto.request.SaveUpdateRekomRequest;
import com.warungsaham.warungsahamappapi.rekom.dto.response.RekomListResponse;
import com.warungsaham.warungsahamappapi.rekom.model.Rekom;
import com.warungsaham.warungsahamappapi.rekom.service.RekomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping(path = "/api/v1/rekom")
public class RekomController {

    private RekomService rekomService;

    @Autowired
    public RekomController(RekomService rekomService){
        this.rekomService = rekomService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<RekomListResponse>>> getRekomList(@RequestParam int index, @RequestParam int size, @RequestParam String search) {
        Page<RekomListResponse> rekomPage = rekomService.getRekomList(index, size, search);
        ApiResponse<Page<RekomListResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setData(rekomPage);
        apiResponse.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Rekom>> getRekomDetailById(@PathVariable(value = "id") int id) {
        Rekom rekomPage = rekomService.getRekomDetail(id);
        ApiResponse<Rekom> apiResponse = new ApiResponse<>();
        apiResponse.setData(rekomPage);
        apiResponse.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }
    

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createNewRekom(@RequestBody SaveUpdateRekomRequest data) {
        rekomService.createRekom(data);
        ApiResponse<String> response = new ApiResponse<>();
        response.setData("Succes");
        response.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse<String>> updateRekomById(@PathVariable(value = "id") int id,@RequestBody SaveUpdateRekomRequest data) {
        rekomService.updateRekom(id,data);
        ApiResponse<String> response = new ApiResponse<>();
        response.setData("Succes");
        response.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteRekomById(@PathVariable(value = "id") int id) {
        rekomService.deleteRekom(id);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setData("Rekom Deleted");
        apiResponse.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }
    
    
}
