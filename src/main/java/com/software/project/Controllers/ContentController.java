package com.software.project.Controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.software.project.Domain.Comment;
import com.software.project.Domain.Content;
import com.software.project.Domain.Gallary;
import com.software.project.Domain.Que;
import com.software.project.Domain.User;
import com.software.project.Repository.ContentRepository;
import com.software.project.Service.CommentService;
import com.software.project.Service.ContentService;
import com.software.project.Service.GallaryService;
import com.software.project.Service.QueService;
import com.software.project.Service.UserService;

import org.apache.tomcat.util.http.fileupload.IOUtils;
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


@Controller
public class ContentController {

    @Autowired
    ContentService contentService;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    @Autowired
    GallaryService gallaryService;
    @Autowired
    QueService queService;
    
    
    //get all contentes based on type 
    @GetMapping("/content/getAll/{type}")
    public String findContentByType(Model model, @PathVariable("type") String type){
      List<Content> contents;
      if(type.equals("HISTORY")){
        contents = contentService.findAllHistory();
        model.addAttribute("title", "Lets Explore Ethiopian History.");
      }
      else if(type.equals("SITE")){
        contents = contentService.findAllSites();
        model.addAttribute("title", "Lets Explore All Cultural And Historical Sites in Ethiopia");
      }
       else if(type.equals("CULTURE")){
        contents = contentService.findAllCulture();
        model.addAttribute("title", "Lets Explore All Cultures in Ethiopia");
       }
       else{
         contents=contentService.findAll();
       }
       model.addAttribute("contents", contents);
       Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (auth instanceof UserDetails) {
            username = ((UserDetails)auth).getUsername();
        } else {
            username = auth.toString();
        } 
        if(username.equals("anonymousUser")){
            model.addAttribute("logged", "false");
        } else{
            model.addAttribute("logged", "true");
            User loged = userService.findByUsername(username);
            if(loged.getRoles().equals("ADMIN")){
                model.addAttribute("role", "admin");
            }    
        }

       return "content";  
    }

    @GetMapping("/content/detail/{contentId}")
    public String showInDepth(@PathVariable("contentId") int id,Model model){
      List<Comment> comments = commentService.findCommentByContentId(id);
      Content content = contentService.findByIdAndIncreaseViewCount(id);
      model.addAttribute("content", content);
      model.addAttribute("existingComments", comments);
      Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (auth instanceof UserDetails) {
            username = ((UserDetails)auth).getUsername();
        } else {
            username = auth.toString();
        } 
        if(username.equals("anonymousUser")){
            model.addAttribute("logged", "false");
        } else{
            model.addAttribute("logged", "true");
            User loged = userService.findByUsername(username);
            if(loged.getRoles().equals("ADMIN")){
                model.addAttribute("role", "admin");
            }    
        }
      return "content_detail";
    }

    // comment opertaion handler
    @PostMapping("/content/comment/{contentId}")
    public String showUpdated(@PathVariable("contentId") int id,Model model,@RequestParam("usercomment") String comment){
      if(comment.length()>0){

        Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (auth instanceof UserDetails) {
            username = ((UserDetails)auth).getUsername();
        } else {
            username = auth.toString();
        } 

      Comment newComment = new Comment();
      newComment.setCommentBody(comment);
      newComment.setContent(contentService.findbyId(id));
      newComment.setUser(userService.findByUsername(username));
      commentService.savecomment(newComment);
      List<Comment> comments = commentService.findCommentByContentId(id);
      Content content = contentService.findByIdAndIncreaseCommentCount(id);
      model.addAttribute("newComment", newComment);
      model.addAttribute("content", content);
      model.addAttribute("existingComments", comments);
      return "content_detail";
      }
      else{
        return "redirect:/content/detail/"+id;
      }
      
      


    }
    //display image
    @GetMapping("/img/{contentId}")
    public void getImage(@PathVariable("contentId") int id, HttpServletResponse response) throws IOException{
      System.out.println("from image part :"+id);
      Content content = contentService.findbyId(id);
      InputStream in = new ByteArrayInputStream(content.getImgData());
      IOUtils.copy(in, response.getOutputStream());


    } 


    
   
}
