package com.warungsaham.warungsahamappapi.premiumsub.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.warungsaham.warungsahamappapi.payment.dao.PaymentDao;
import com.warungsaham.warungsahamappapi.payment.model.Payment;
import com.warungsaham.warungsahamappapi.plan.dao.PlanDao;
import com.warungsaham.warungsahamappapi.plan.model.Plan;
import com.warungsaham.warungsahamappapi.premiumsub.dao.PremiumSubDao;
import com.warungsaham.warungsahamappapi.premiumsub.model.PremiumSub;
import com.warungsaham.warungsahamappapi.storage.files.service.FilesStorageService;
import com.warungsaham.warungsahamappapi.user.dao.UserDao;
import com.warungsaham.warungsahamappapi.user.model.User;

import jakarta.transaction.Transactional;

@Service
public class PremiumSubServiceImpl implements PremiumSubService {

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

        filesStorageService.save(imageUrl);
        
        PremiumSub premiumSub = new PremiumSub();
        premiumSub.setUser(user);
        premiumSub.setPlan(plan);

        long planDuration = plan.getDuration() * 2629800000l;
        premiumSub.setStartDate(new Date());
        premiumSub.setEndDate(new Date((long) (new Date().getTime() + planDuration)));
        
        Payment payment = new Payment();
        payment.setPaymentDate(new Date());
        payment.setBuktiTransfer(imageUrl.getOriginalFilename());
        payment.setPaymentType(paymentType);

        premiumSub.setPayment(payment);

        premiumSubDao.save(premiumSub);

    }
    
}
