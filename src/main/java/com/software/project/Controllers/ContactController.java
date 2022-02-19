package com.software.project.Controllers;

import java.util.ArrayList;
import java.util.List;

import com.software.project.Domain.Contact;
import com.software.project.Domain.User;
import com.software.project.Service.ContactService;
import com.software.project.Service.UserService;

import org.apache.catalina.valves.rewrite.InternalRewriteMap.Escape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class ContactController {

    @Autowired
    ContactService contactService;
    @Autowired
    UserService userServices;
    Contact contact;

    @GetMapping("/contact")
    public String toshow(Model themodel){
       
        Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (auth instanceof UserDetails) {
            username = ((UserDetails)auth).getUsername();
        } else {
            username = auth.toString();
        } 
       
        
    User user = userServices.findByUsername(username);
    themodel.addAttribute("contact",new Contact() );
    themodel.addAttribute("user",user );
            
        
     
        if(username.equals("anonymousUser")){
            themodel.addAttribute("logged", "false");
        } else{
            themodel.addAttribute("logged", "true");
            User loged = userServices.findByUsername(username);
            if(loged.getRoles().equals("ADMIN")){
                themodel.addAttribute("role", "admin");
            }    
        }
    

 return "contact" ;
    }

    @PostMapping("/contact/save")
    public String toSave(@ModelAttribute("contact") Contact contact ){
        Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (auth instanceof UserDetails) {
            username = ((UserDetails)auth).getUsername();
        } else {
            username = auth.toString();
        } 

        User existingUser= userServices.findByUsername(username);
        contact.setUserId(existingUser.getId());
      
        contact.setMessage(contact.getMessage());

     
       contact.setName(contact.getName());
       contact.setEmail(contact.getEmail());
       contactService.Save(contact);

       

  
    
    
        return "home";



    }
    
}
