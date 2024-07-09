package com.warungsaham.warungsahamappapi.insight.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.warungsaham.warungsahamappapi.global.exception.NotFoundException;
import com.warungsaham.warungsahamappapi.insight.dao.InsightDao;
import com.warungsaham.warungsahamappapi.insight.dto.request.NewInsightRequest;
import com.warungsaham.warungsahamappapi.insight.dto.response.InsightDetailDto;
import com.warungsaham.warungsahamappapi.insight.dto.response.InsightDto;
import com.warungsaham.warungsahamappapi.insight.model.Insight;
import com.warungsaham.warungsahamappapi.storage.files.service.FilesStorageService;
import com.warungsaham.warungsahamappapi.user.dao.UserDao;
import com.warungsaham.warungsahamappapi.user.model.User;

import jakarta.transaction.Transactional;

@Service
public class InsightServiceImpl implements InsightService {

    private static final Logger LOG = LoggerFactory.getLogger(InsightServiceImpl.class);

    @Value("${waroengsaham.filepath.thumbnail}")
    private String thumbnailFilePath;

    private FilesStorageService filesStorageService;
    private UserDao userDao;
    private InsightDao insightDao;

    public InsightServiceImpl(FilesStorageService filesStorageService ,UserDao userDao, InsightDao insightDao){
        this.filesStorageService = filesStorageService;
        this.userDao = userDao;
        this.insightDao = insightDao;
    }


    
    @Override
    @Transactional
    public void createInsight(NewInsightRequest newInsightRequest) {
        User user = getUser(newInsightRequest.getUserId());

        Insight newInsight = new Insight();
        newInsight.setUser(user);
        newInsight.setContent(newInsightRequest.getContent());
        newInsight.setCreateBy(user.getUsername());
        newInsight.setTitle(newInsightRequest.getTitle());
        newInsight.setCreateDate(new Date());

        Insight tempInsight = insightDao.save(newInsight);

        String imgPath = saveFile(newInsightRequest.getThumbnailImg(), tempInsight.getId(),tempInsight.getTitle());

        tempInsight.setThumbnailImg(imgPath);
        
    }

    @Override
    @Transactional
    public void updateInsight(int id, NewInsightRequest newInsightRequest) {
        Insight insight = getInsight(id);
        User user = getUser(newInsightRequest.getUserId());

        insight.setContent(newInsightRequest.getContent());
        insight.setTitle(newInsightRequest.getTitle());
        insight.setUpdateBy(user.getUsername());
        insight.setUpdateDate(new Date());

        Insight tempInsight = insightDao.save(insight);

        if(newInsightRequest.getThumbnailImg() != null){
            String imgPath = saveFile(newInsightRequest.getThumbnailImg(), id,insight.getTitle());
            tempInsight.setThumbnailImg(imgPath);
        }
       
    }

    @Override
    public Page<InsightDto> getListInsightPage(int index, int size) {
        Pageable pageable = PageRequest.of(index, size);
        return insightDao.findAll(pageable).map(this::mapInsightToInsightDto);

    }

    @Override
    public InsightDetailDto getInsightDetailById(int id) {
        Insight insight = getInsight(id);
        return mapInsightToInsightDetailDto(insight);

    }


    @Override
    public Page<InsightDto> getListInsightPageByFilter(int index, int size, String title, String createBy,
            Date startDate, Date endDate) {

        Pageable pageable = PageRequest.of(index, size);
        return insightDao.findInsightPageByFilter(title,createBy,startDate,endDate,pageable).map(this::mapInsightToInsightDto);

    }

    private User getUser(String userId) {
        User user = userDao.findByUserId(userId);

        if(user == null) {
            throw new NotFoundException("User Not Found");
        }
        return user;
    }

    private Insight getInsight(int id){
        return insightDao.findById(id)
                .orElseThrow(() -> new NotFoundException("Insight Not Found"));
    }


    private String saveFile(MultipartFile imgFile, int uniqueId , String title) {
        LOG.info("Img File : {}", imgFile);
        if(imgFile == null) return null;
        
        DateFormat filePathDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String filePathDate = filePathDateFormat.format(new Date());

        StringBuilder sb = new StringBuilder();
        sb.append(uniqueId);
        sb.append("_");
        sb.append(title);
        sb.append("_thumbnail.");
        sb.append(imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf(".") + 1));

        String filename = sb.toString();

        filesStorageService.save(imgFile,thumbnailFilePath + filePathDate, filename);

        return thumbnailFilePath + filePathDate + "/" + filename;
    }


    private InsightDto mapInsightToInsightDto(Insight insight){
        InsightDto insightDto = new InsightDto();

        insightDto.setId(insight.getId());
        insightDto.setTitle(insight.getTitle());
        insightDto.setCreateBy(insight.getCreateBy());
        insightDto.setCreateDate(insight.getCreateDate());
        insightDto.setUpdateBy(insight.getUpdateBy());
        insightDto.setUpdateDate(insight.getUpdateDate());

        return insightDto;
    }

    private InsightDetailDto mapInsightToInsightDetailDto(Insight insight){
        InsightDetailDto insightDetailDto = new InsightDetailDto();

        insightDetailDto.setId(insight.getId());
        insightDetailDto.setUserId(insight.getUser().getUserId());
        insightDetailDto.setTitle(insight.getTitle());
        insightDetailDto.setThumbnailImg(insight.getThumbnailImg());
        insightDetailDto.setContent(insight.getContent());

        return insightDetailDto;
    }



}
