package com.ktb.clmapiauthen.services.impl;

import java.text.MessageFormat;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.directory.Attributes;
import javax.naming.directory.Attribute;
import com.ktb.clmapiauthen.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktb.clmapiauthen.config.AppConfig;
import com.ktb.clmapiauthen.models.annotation.Loggable;
import com.ktb.clmapiauthen.models.authen.AuthenRequest;
import com.ktb.clmapiauthen.models.interfaces.ad.ADResponse;
import com.ktb.clmapiauthen.repository.TransectionLogRepository;
import com.ktb.clmapiauthen.repository.UserRepository;
import com.ktb.clmapiauthen.services.AuthenService;
import com.ktb.clmapiauthen.util.JWTTokenUtil;
import java.util.*;


@Service
public class AuthenServiceImpl implements AuthenService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String SUCCESS = "S";
    private String ERORR = "E";

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransectionLogRepository transectionLogRepository;
    
    @Loggable
    @Override
    public String authen(AuthenRequest request, String uuid) {
        String token = null;
        TransactionLog transactionLog = new TransactionLog();
        String status = this.ERORR;
        String deptCode = null;
        String rankCode = null;
        String ipAddr = null;
        String remark = null;
        try{

            if(!request.getUsername().isEmpty() && !request.getPassword().isEmpty()){
                String username = request.getUsername();
                String password = request.getPassword();
                User user = null; 

                // --------------------- Bypass ----------------------
                if(appConfig.getUsername().equalsIgnoreCase(username) && appConfig.getPassword().equalsIgnoreCase(password)){
                    String result = jwtTokenUtil.generateToken(user, uuid);
                        if(!result.isEmpty()){
                            token = "Bearer " + result;
                            if (token != null && token.startsWith("Bearer ")) {
                                token = token.substring(7);
                                status = this.SUCCESS;
                                return token;
                            }
                        }
                }
                // ---------------------------------------------------
                user = findUserByUsername(username, password);
                logger.info("find User By Username : {}",user);

                if(user != null){
                    ADResponse adResponse = ADInterface(username,password);
                    logger.info("AD Interface Response : {}",adResponse);

                    if(adResponse != null){
                        deptCode = adResponse.getDepartmentCode();
                        rankCode = adResponse.getRankCode();
                        ipAddr = adResponse.getIpAddress();

                        String result = jwtTokenUtil.generateToken(user, uuid);

                        if(!result.isEmpty()){
                            token = "Bearer " + result;
                            if (token != null && token.startsWith("Bearer ")) {
                                token = token.substring(7);
                                status = this.SUCCESS;
                            }
                        }
                    }else{
                        remark = "Can not request or response AD interface";
                    }
                }else{
                    remark = "User or username not found from database";
                }
            }else{
                remark = "Username or password not found";
            }
        }catch (Exception e) {
            token = null;
            status = this.ERORR;
            remark = e.getMessage();
            e.printStackTrace();
        }finally{
            transactionLog.setTransUuid(uuid);
            transactionLog.setStatus(status);
            transactionLog.setDeptCode(deptCode);
            transactionLog.setRankCode(rankCode);
            transactionLog.setIpAddr(ipAddr);
            transactionLog.setToken(token);
            transactionLog.setRemark(remark);
            transectionLogRepository.saveAndFlush(transactionLog);
        }
        return token;
    }

    @Loggable
    @Override
    public ADResponse ADInterface(String username ,String password) throws NamingException  {
        String[] attrIDs = null;
        Hashtable env = new Hashtable();
        ADResponse adResponse = new ADResponse();

        logger.info("INITIAL_CONTEXT_FACTORY : {}",appConfig.getADContextFactory());
        logger.info("PROVIDER_URL : {}",appConfig.getADUrl());
        logger.info("SECURITY_AUTHENTICATION : {}",appConfig.getADSecurityAuthen());
        logger.info("SECURITY_PRINCIPAL : {}",username + "@" + appConfig.getADDomain());
        logger.info("SECURITY_CREDENTIALS : {}",password);

        env.put(Context.INITIAL_CONTEXT_FACTORY, appConfig.getADContextFactory());
        env.put(Context.PROVIDER_URL, appConfig.getADUrl());
        env.put(Context.SECURITY_AUTHENTICATION, appConfig.getADSecurityAuthen());
        env.put(Context.SECURITY_PRINCIPAL, username + "@" + appConfig.getADDomain());
        env.put(Context.SECURITY_CREDENTIALS, password);

        attrIDs = new String[]{
            "thaipersonaltitle", "thaifirstname", "thailastname", "kcsbranchcode",
            "personaltitle", "givenname", "sn", "businesscategory", "title",
            "postaladdress", "mail", "citizenid", "cn", "uid", "kcsbankId",
            "RankCode", "Rank", "LocationCode", "DepartmentCode",
            "employeenumber", "telephonenumber"
        };

        String filter = appConfig.getADFilter();
        String base = appConfig.getADBase();
        adResponse.setIpAddress(appConfig.getADUrl());

        logger.info("filter : {}",filter);
        logger.info("base : {}",base);

        // DirContext dirContext = new InitialDirContext(env);

        // SearchControls searchControls = new SearchControls();
        // searchControls.setReturningAttributes(attrIDs);
        // searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        try {
            // filter = MessageFormat.format(filter,username);

            // NamingEnumeration answer = dirContext.search(base , filter, searchControls);

            // while (answer.hasMore()) {
            //         SearchResult sr = (SearchResult) answer.next();
            //         adResponse = defineAttrs(sr.getAttributes());
            // }

            adResponse = this.muckupData(); // test
        } finally {
            // dirContext.close();
        }
        return adResponse;
    }

    @Loggable
    @Override
    public User findUserByUsername(String username, String password) {
        return userRepository.findByEmpMailAndEmpNameEng(username, password);
    }

    public ADResponse defineAttrs(Attributes attrs){
        logger.info("Response AD Interface Attrs : ");

        ADResponse adResponse = new ADResponse();

        if (attrs != null) {
            try {
                logger.info("{");
                for (NamingEnumeration obj = attrs.getAll(); obj.hasMore(); ) {
                    Attribute attr = (Attribute) obj.next();
                    String attrID = (String) attr.getID();
                    logger.info("   "+attr.getID() + " : " + getAttr(attr.getAll()));
                    switch (attrID.trim()){
                        case "uid":
                            adResponse.setUid(getAttr(attr.getAll()));
                            adResponse.setUsername(getAttr(attr.getAll()));
                            break;
                        case "cn":
                            adResponse.setUid(getAttr(attr.getAll()));
                            adResponse.setUsername(getAttr(attr.getAll()));
                            break;
                        case "givenName":adResponse.setGivenName(getAttr(attr.getAll()));break;
                        case "sn":adResponse.setSn(getAttr(attr.getAll()));break;
                        case "thaipersonaltitle":adResponse.setThaiPersonalTitle(getAttr(attr.getAll()));break;
                        case "thaifirstname":adResponse.setThaiFirstName(getAttr(attr.getAll()));break;
                        case "thailastname":adResponse.setThaiLastName(getAttr(attr.getAll()));break;
                        case "titlecode":adResponse.setTitleCode(getAttr(attr.getAll()));break;
                        case "title":adResponse.setTitle(getAttr(attr.getAll()));break;
                        case "rankcode":adResponse.setRankCode(getAttr(attr.getAll()));break;
                        case "rank":adResponse.setRank(getAttr(attr.getAll()));break;
                        case "departmentcode":adResponse.setDepartmentCode(getAttr(attr.getAll()));break;
                        case "businessCategory":adResponse.setBusinessCategory(getAttr(attr.getAll()));break;
                        case "locationcode":adResponse.setLocationCode(getAttr(attr.getAll()));break;
                        case "postalAddress":adResponse.setPostalAddress(getAttr(attr.getAll()));break;
                        case "kcsbranchcode":adResponse.setKcsBranchCode(getAttr(attr.getAll()));break;
                        case "citizenID":adResponse.setCitizenID(getAttr(attr.getAll()));break;
                        case "mail":adResponse.setMail(getAttr(attr.getAll()));break;
                    }
                }
                logger.info("}");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return adResponse;
    }

    private String getAttr(NamingEnumeration obj){
        String attr = null;
        try{
            while(obj.hasMore()){
                attr = (String) obj.next();
            }
        }catch (NamingException e){
            e.printStackTrace();
        }
        return attr;
    }

    private ADResponse muckupData(){
        ADResponse adResponse = new ADResponse();
        adResponse.setUid("15518");;
        adResponse.setUsername("15518");
        adResponse.setGivenName("Eakkawee");
        adResponse.setSn("Pooissarakul");
        adResponse.setThaiPersonalTitle("วท.ร.ต.");
        adResponse.setThaiFirstName("ทดสอบ");
        adResponse.setThaiLastName("ทดสอบ");
        // adResponse.setTitleCode();
        adResponse.setTitle("ผู้จัดการศูนย์");
        adResponse.setRankCode("3080A");
        adResponse.setRank("ผู้จัดการ");
        adResponse.setDepartmentCode("3H015574");
        adResponse.setBusinessCategory("สายงานบริการคุณภาพสินเชื่อ");
        adResponse.setLocationCode("KTBME10052");
        adResponse.setPostalAddress("อาคารเอกมัยชั้น11");
        adResponse.setKcsBranchCode("108233");
        adResponse.setCitizenID("3100800391601");
        adResponse.setMail("eakkawee.pooissarakul@krungthai.com");
        return adResponse;
    }


    
    
}
