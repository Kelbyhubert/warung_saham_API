package com.warungsaham.warungsahamappapi.premiumsub.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;


import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.warungsaham.warungsahamappapi.payment.dao.PaymentDao;
import com.warungsaham.warungsahamappapi.payment.model.Payment;
import com.warungsaham.warungsahamappapi.plan.dao.PlanDao;
import com.warungsaham.warungsahamappapi.plan.model.Plan;
import com.warungsaham.warungsahamappapi.premiumsub.dao.PremiumSubDao;
import com.warungsaham.warungsahamappapi.premiumsub.dto.response.PaymentDataResponse;
import com.warungsaham.warungsahamappapi.premiumsub.model.PremiumSub;
import com.warungsaham.warungsahamappapi.storage.files.service.FilesStorageService;
import com.warungsaham.warungsahamappapi.user.dao.UserDao;
import com.warungsaham.warungsahamappapi.user.model.User;


import jakarta.transaction.Transactional;

@Service
public class PremiumSubServiceImpl implements PremiumSubService {

    @Value("${waroengsaham.filepath.buktiTransfer}")
    private String buktiTransferDirPath;
    private UserDao userDao;
    private PremiumSubDao premiumSubDao;
    private PlanDao planDao;
    private PaymentDao paymentDao;
    private FilesStorageService filesStorageService;

    @Autowired
    public PremiumSubServiceImpl(UserDao userDao, PremiumSubDao premiumSubDao, PlanDao planDao, PaymentDao paymentDao, FilesStorageService filesStorageService){
        this.userDao = userDao;
        this.premiumSubDao = premiumSubDao;
        this.planDao = planDao;
        this.paymentDao = paymentDao;
        this.filesStorageService = filesStorageService;
    }


    @Override
    @Transactional
    public void createPremiumUser(String userId, int planId, MultipartFile imageUrl, String paymentType) {
        User user = userDao.findByUserId(userId);
        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        Plan plan = planDao.findById(planId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan not found"));

        DateFormat filePathDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String filePathDate = filePathDateFormat.format(new Date());
        String filename = paymentType + "_" + user.getId() + "_" + user.getUsername() + "." + imageUrl.getOriginalFilename().substring(imageUrl.getOriginalFilename().lastIndexOf(".") + 1);

        filesStorageService.save(imageUrl,buktiTransferDirPath + filePathDate, filename);
        
        PremiumSub premiumSub = new PremiumSub();
        premiumSub.setUser(user);
        premiumSub.setPlan(plan);

        long planDuration = plan.getDuration() * 2629800000l;
        premiumSub.setStartDate(new Date());
        premiumSub.setEndDate(new Date((long) (new Date().getTime() + planDuration)));
        
        Payment payment = new Payment();
        payment.setPaymentDate(new Date());
        payment.setFilename(filename);
        payment.setFileType(imageUrl.getContentType());
        payment.setDirPath(buktiTransferDirPath + filePathDate);
        payment.setOriginalName(imageUrl.getOriginalFilename());
        payment.setPaymentType(paymentType);

        premiumSub.setPayment(payment);

        premiumSubDao.save(premiumSub);

    }


    @Override
    public PaymentDataResponse getPaymentDataById(int id) {
        Optional<PremiumSub> data = premiumSubDao.findById(id);
        if(data.get().getPayment() == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found");
        }

        Payment payment = data.get().getPayment();
        
        StringBuilder sb = new StringBuilder();
        sb.append("data:image/png;base64,");
        sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(filesStorageService.getFileByte(payment.getFilename(), payment.getDirPath()), false)));

        PaymentDataResponse paymentDataResponse = new PaymentDataResponse();
        paymentDataResponse.setPaymentDate(payment.getPaymentDate());
        
        paymentDataResponse.setImageUrl(sb.toString());
        return paymentDataResponse;
    }

    
}
