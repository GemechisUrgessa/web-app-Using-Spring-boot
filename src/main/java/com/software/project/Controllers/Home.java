package com.software.project.Controllers;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.software.project.Domain.Content;
import com.software.project.Domain.Gallary;
import com.software.project.Domain.User;
import com.software.project.Repository.GallaryRepository;
import com.software.project.Service.ContentService;
import com.software.project.Service.GallaryService;
import com.software.project.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Home {
    @Autowired
    ContentService contentService;
    @Autowired
    GallaryService gallaryService;
    @Autowired
    UserService userService;
  
    @GetMapping("/")
    public String toshowHome(Model model){
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

        return "index";
    }
    @GetMapping("/map")
    public String toshowMap(Model model){
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
        return "map";
    }
    @GetMapping("/about")
    public String showAbout(Model model){
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
        return "about";
    }
    @GetMapping("/search")
    public String showSearchResults(@RequestParam("searchterms") String searchKeys,Model model){
        List<Content> contents =  contentService.searchFor(searchKeys);
        model.addAttribute("contents", contents);
        return "content";
    }
    @GetMapping("/logout")
    public String logout(){
        
        return "logout";
    }
 
 

}
