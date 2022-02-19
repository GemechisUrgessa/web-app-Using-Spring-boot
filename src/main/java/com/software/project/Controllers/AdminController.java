package com.software.project.Controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.software.project.Domain.Comment;
import com.software.project.Domain.Contact;
import com.software.project.Domain.Content;
import com.software.project.Domain.Gallary;
import com.software.project.Domain.Que;
import com.software.project.Domain.User;
import com.software.project.Repository.ContentRepository;
import com.software.project.Service.CommentService;
import com.software.project.Service.ContactService;
import com.software.project.Service.ContentService;
import com.software.project.Service.GallaryService;
import com.software.project.Service.QueService;
import com.software.project.Service.UserService;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import org.springframework.stereotype.Controller;

@Controller
public class AdminController {
    @Autowired
    ContentService contentService;
    @Autowired
    QueService queService;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    @Autowired
    GallaryService gallaryService;
    @Autowired
    ContactService contactService;

     //give the admin page
     @GetMapping("/adminPage")
     public String  getUplaodContentForm( Model model){
         Que newContent = new Que();
         List<Que> ques = queService.findAll();
         List<Contact> contacts = contactService.findAllContact();
         List<User> users=userService.findAllUsers();
         int contents = contentService.findAll().size();
         int histories= contentService.findAllHistory().size();
         int sites= contentService.findAllSites().size();
         int cultures = contentService.findAllCulture().size();
         int user=userService.findAllUsers().size();
         Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         String username;
         if (auth instanceof UserDetails) {
             username = ((UserDetails)auth).getUsername();
         } else {
             username = auth.toString();
         } 
         List<String> type= Arrays.asList("HISTORY","SITE","CULTURE");
         model.addAttribute("histories", histories);
         model.addAttribute("sites", sites);
         model.addAttribute("cultures", cultures);
         model.addAttribute("contents", contents);
         model.addAttribute("contacts", contacts);
         model.addAttribute("ques", ques);
         model.addAttribute("type",type);
         model.addAttribute("userName", username);
         model.addAttribute("contentHolder", newContent);
         model.addAttribute("users",user);
         model.addAttribute("user",users);

     
 
         return "admin";
         
 
     }
        
     @GetMapping("/content/edit/{id}")
     public String editContent(@PathVariable("id") int id, Model model){
         Content content= contentService.findbyId(id);
         Que que = new Que();
         que.setTitle(content.getTitle());
         que.setImgData(content.getImgData());
         que.setTitle(content.getTitle());
         que.setId(id);
         que.setOldId(content.getId());
         que.setContentData(content.getContentData());
         que.setContentIntro(content.getContentIntro());
         que.setYearElapsed(content.getYearElapsed());
         que.setIsUpdate("true");
         model.addAttribute("content", que);
         model.addAttribute("update", "true");


        return "contentEdit";
     }


     //stopre uploaded content to database
     @PostMapping("/content/upload")
     public String uplaodContent(@RequestParam("picture") MultipartFile imgFile,@ModelAttribute("contentHolder") Que content,@RequestParam(name = "preference",defaultValue = "none") String pref,@RequestParam(name="update",defaultValue = "false") String update ) throws IOException{
    //    System.out.println("first: "+pref.equals("new"));
    //     System.out.println("second: "+pref.equals("old"));
    //     System.out.println("second: "+!pref.equals("none"));
    //     System.out.println("id: "+content.getOldId());
    // System.out.println("here hrer here her");
    // System.out.println(content.getOldId());
        
      
    

        
        Que newContent = new Que();
        // if(content.getIsUpdate()!=null){

        // }
         if(content.getIsUpdate()!=null){
            Content orginalContent= contentService.findbyId(content.getOldId());
             newContent.setIsUpdate("true");
             newContent.setOldId(content.getOldId());
             newContent.setYearElapsed(orginalContent.getYearElapsed());
             newContent.setContentType(orginalContent.getContentType());
            if(!pref.equals("none")){
                if(pref.equals("new")){
                    newContent.setImgData(imgFile.getBytes());
   
                }
                else if(pref.equals("old")){
                    System.out.println("i am in the old fashion");
                    newContent.setImgData(orginalContent.getImgData());
                }
   
            }

         }
         else{
            newContent.setYearElapsed(content.getYearElapsed());
            newContent.setContentType(content.getContentType());
            newContent.setImgData(imgFile.getBytes());
         }

        //  System.out.println("preference: "+pref);
        //  System.out.println(newContent);
        //  System.out.println("update: "+content.getIsUpdate());
     
        
         newContent.setContentData(content.getContentData());
         if(content.getContentData().length()>310){
             newContent.setContentIntro(content.getContentData().substring(0,300));
         }
         else{
             newContent.setContentIntro(content.getContentData());
             
         }
        

         Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         String username;
         if (auth instanceof UserDetails) {
             username = ((UserDetails)auth).getUsername();
         } else {
             username = auth.toString();
         } 
     
        
         newContent.setTitle(content.getTitle());
         

   

         newContent.setLocation("Addis Ababa");

         

         newContent.setApprovalCount(1);
         newContent.setDeclineCount(0);
         String uploaderAdmin =username;
         newContent.setAdminOne("");
         newContent.setAdminFour("");
         newContent.setAdminTwo("");
         newContent.setAdminThree("");
         switch(uploaderAdmin){
             case "adminOne": newContent.setAdminOne("approved");
             break;
             case "adminTwo": 
             newContent.setAdminTwo("approved");
             break;
             case "adminThree":
             newContent.setAdminThree("approved");
             break;
             case "adminFour": 
             newContent.setAdminFour("approved");
             break;
         }



         
         queService.saveContent(newContent);
         return "redirect:/";
     }


     @GetMapping("/que/detail/{contentId}")
     public String showQueInDepth(@PathVariable("contentId") int id,Model model){
       Que content = queService.findbyId(id);
       model.addAttribute("content", content);
       return "adminApprove";
     }

     @GetMapping("/img/que/{contentId}")
     public void getQueImage(@PathVariable("contentId") int id, HttpServletResponse response) throws IOException{
 
       Que content = queService.findbyId(id);
       InputStream in = new ByteArrayInputStream(content.getImgData());
       IOUtils.copy(in, response.getOutputStream());
 
 
     } 
     @GetMapping("/approve/{id}")
     public String approveQue(@PathVariable("id") int id){
        Que que = queService.findbyId(id);
        
        Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (auth instanceof UserDetails) {
            username = ((UserDetails)auth).getUsername();
        } else {
            username = auth.toString();
        } 
        String uploaderAdmin= username;
        System.out.println(username);
        // String adminID;
        switch(uploaderAdmin){
            case "adminOne": 
            if(que.getAdminOne().equals("declined")){
                que.setAdminOne("approved");
                que.setApprovalCount(que.getApprovalCount()+1);
                que.setDeclineCount(que.getDeclineCount()-1);
            }
            else if(!que.getAdminOne().equals("approved")){
                que.setAdminOne("approved");
                que.setApprovalCount(que.getApprovalCount()+1);
            }
          
            break;
            case "adminTwo": 
             if(que.getAdminTwo().equals("declined")){
                que.setAdminTwo("approved");
                que.setApprovalCount(que.getApprovalCount()+1);
                que.setDeclineCount(que.getDeclineCount()-1);
            }
            else if(!que.getAdminTwo().equals("approved")){
                que.setAdminTwo("approved");
                que.setApprovalCount(que.getApprovalCount()+1);
            } 
            break;
            case "adminThree":
            if(que.getAdminThree().equals("declined")){
                que.setAdminThree("approved");
                que.setApprovalCount(que.getApprovalCount()+1);
                que.setDeclineCount(que.getDeclineCount()-1);
            }
           else if(!que.getAdminThree().equals("approved")){
                que.setAdminThree("approved");
                que.setApprovalCount(que.getApprovalCount()+1);
            }
            break;
            case "adminFour": 
            if(que.getAdminFour().equals("declined")){
                que.setAdminFour("approved");
                que.setApprovalCount(que.getApprovalCount()+1);
                que.setDeclineCount(que.getDeclineCount()-1);
            }
            if(!que.getAdminFour().equals("approved")){
                que.setAdminFour("approved");
                que.setApprovalCount(que.getApprovalCount()+1);
            }
            break;
        }
        if(que.getApprovalCount() > 3){
            Content content = new Content();
            content.setTitle(que.getTitle());
            content.setContentData(que.getContentData());
            content.setContentIntro(que.getContentIntro());
            content.setYearElapsed(que.getYearElapsed());
            content.setViewCount(0);
            content.setImgData(que.getImgData());
            content.setContentType(que.getContentType());
            content.setLocation(que.getLocation());
            if(que.getIsUpdate()!=null){
           
                contentService.DeleteById(que.getOldId());
            }
            contentService.saveContent(content);
            queService.DeleteById(que.getId());
        }else{

        
        if(que.getApprovalCount()<0){
            que.setApprovalCount(0);
        }
        queService.saveContent(que);
    }
        return "redirect:/adminPage";

     }

     @GetMapping("/decline/{id}")
     public String declineQue(@PathVariable("id") int id){
         Que que = queService.findbyId(id);
        

        
         Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         String username;
         if (auth instanceof UserDetails) {
             username = ((UserDetails)auth).getUsername();
         } else {
             username = auth.toString();
         } 

         String [] admins = {"adminOne","adminTwo","adminThree","adminFour"};
         switch (username){
             case "adminOne":
             if(que.getAdminOne().equals("approved")){
                 que.setApprovalCount(que.getApprovalCount()-1);
                 que.setAdminOne("declined");
                 que.setDeclineCount(que.getDeclineCount()+1);
            
             }
             else if(que.getAdminOne().equals("declined")){

             }
             else{
                 que.setAdminOne("declined");
                 que.setDeclineCount(que.getDeclineCount()+1);
             }
             break;
             case "adminFour":
             if(que.getAdminFour().equals("approved")){
                 que.setApprovalCount(que.getApprovalCount()-1);
                 que.setAdminFour("declined");
                 que.setDeclineCount(que.getDeclineCount()+1);
            
             }
             else if(que.getAdminFour().equals("declined")){

            }
             else{
                 que.setAdminFour("declined");
                 que.setDeclineCount(que.getDeclineCount()+1);
             }
             break;
             case "adminTwo":
             if(que.getAdminTwo().equals("approved")){
                 que.setApprovalCount(que.getApprovalCount()-1);
                 que.setAdminTwo("declined");
                 que.setDeclineCount(que.getDeclineCount()+1);
            
             }
             else if(que.getAdminTwo().equals("declined")){

            }
             else{
                 que.setAdminTwo("declined");
                 que.setDeclineCount(que.getDeclineCount()+1);
             }
             break;
             case "adminThree":
             if(que.getAdminThree().equals("approved")){
                 que.setApprovalCount(que.getApprovalCount()-1);
                 que.setAdminThree("declined");
                 que.setDeclineCount(que.getDeclineCount()+1);
            
             }
             else if(que.getAdminThree().equals("declined")){


            }
             else{
                 que.setAdminThree("declined");
                 que.setDeclineCount(que.getDeclineCount()+1);
             }
             break;
         }
         System.out.println("decline here");
       
         if(que.getDeclineCount()<0){
             que.setDeclineCount(0);
         }
         if(que.getDeclineCount()>3){
             queService.DeleteById(que.getId());
         }else{
           queService.saveContent(que);
        }
        return "redirect:/adminPage";
     }


 

     
    
}
