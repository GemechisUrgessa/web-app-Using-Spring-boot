package com.software.project.Controllers;
import java.util.Optional;

import javax.validation.Valid;

import com.software.project.Domain.User;
import com.software.project.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;
    @GetMapping("/profile")
    public String toshow(Model model){
        Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (auth instanceof UserDetails) {
            username = ((UserDetails)auth).getUsername();
        } else {
            username = auth.toString();
        } 
        User exUser = userService.findByUsername(username);
        model.addAttribute("newUser", exUser);
        if(exUser.getRoles().equals("ADMIN")){
            return "adminProfile";
        }else{
            return "profile";
        }
        
    }
    @GetMapping("/register")
    public String register(Model model){
        User user=new User();
        model.addAttribute("newUser", user);
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
        return "register";
    }
    @GetMapping("/login")
    public String showLogin(Model theModel){
        return "login";
    }
    @PostMapping("/loginFailed")
    public String loginFailled(Model model){
        model.addAttribute("message", "error");
        return "login";
    }
    @PostMapping("/register")
    public String signup( @ModelAttribute("newUser")  @Valid User user,Errors errors,Model model){
        User newUser = user;
        // System.out.println(newUser.getUsername().length()==0);
        // System.out.println(newUser.getPassword().length()==0);
        if(newUser.getUsername().length()<1 || newUser.getPassword().length()<7 || newUser.getEmail().length()<1 || !((userService.findByUsername(user.getUsername())==null))){

       
        if(newUser.getUsername().length()<1){
            model.addAttribute("name", "error");
        }
         if( newUser.getEmail().length()<1){
            model.addAttribute("email", "error");

        }
        if(newUser.getPassword().length()<6){
            model.addAttribute("password", "error");
        }
        System.out.println("ada ada");
        System.out.println(!(userService.findByUsername(user.getUsername())==null));
        if(!(userService.findByUsername(user.getUsername())==null)){
            model.addAttribute("duplicate", "error");
        }
        return "register";
    }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("USER");
        userService.saveUser(user);
        return "redirect:/";
    }
    
    @PostMapping("/update")
    public String update( @ModelAttribute("newUser")  @Valid User user,Errors errors,Model model,@RequestParam("oldpassword") String oldpassword){
        User newUser = user;

        // System.out.println(newUser.getUsername().length()==0);
        // System.out.println(newUser.getPassword().length()==0);
        Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (auth instanceof UserDetails) {
            username = ((UserDetails)auth).getUsername();
        } else {
            username = auth.toString();
        } 
        User exUser = userService.findByUsername(username);
        if(newUser.getUsername().length()<1 || ((newUser.getPassword().length()<7)&&newUser.getPassword().length()>0) || newUser.getEmail().length()<1 ||(!(userService.findByUsername(user.getUsername())==null)) && (!exUser.getUsername().equals(user.getUsername())) ){

       
        if(newUser.getUsername().length()<1){
            model.addAttribute("name", "error");
        }
         if( newUser.getEmail().length()<1){
            model.addAttribute("email", "error");

        }
        if(newUser.getPassword().length()<6){
            if(newUser.getPassword().length()==0){

            }else{
                model.addAttribute("password", "error");
            }
            
        }
      
        if((!(userService.findByUsername(user.getUsername())==null)) && (!exUser.getUsername().equals(user.getUsername())) ){
            model.addAttribute("duplicate", "error");
        }
        return "profile";
    }
    
    // System.out.println("her we are here we are here we are here we are");
    // System.out.println(passwordEncoder.matches(oldpassword,exUser.getPassword()));
    // System.out.println(exUser);
    // System.out.println(oldpassword);
    if(passwordEncoder.matches(oldpassword,exUser.getPassword())){
        if(user.getPassword().length()>0){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        else{
            user.setPassword(passwordEncoder.encode(oldpassword));
        }
        
        user.setRoles("USER");
        user.setId(exUser.getId());
        userService.saveUser(user);
        return "redirect:/";
    }else{
        
        model.addAttribute("oldpassword", "error");
        return "profile";
        
    }

       
    }
}
