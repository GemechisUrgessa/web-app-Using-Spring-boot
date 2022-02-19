package com.software.project.Controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.software.project.Domain.Content;
import com.software.project.Domain.Gallary;
import com.software.project.Domain.User;
import com.software.project.Service.GallaryService;
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
public class GallaryController {
    @Autowired
    GallaryService gallaryService;
    @Autowired
    UserService userService;
    @GetMapping("/gallary")
    public String getGallary(Model model) {
        List<Gallary> gallaries = gallaryService.findAllGallary();
        List<String> regions = Arrays.asList("ALL","TIGRAY","DEBUB","OROMIYA","AFAR","AMHARA","BENSHANGUL","SOMMALIA","HARERI");
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
        model.addAttribute("gallaries", gallaries);
        model.addAttribute("regions", regions);
        return "gallary";
    }
    @GetMapping("/gallary/upload")
    public String  getUplaodContentFormGallary( Model model){
        Gallary newGallary = new Gallary();
        List<String> regions = Arrays.asList("TIGRAY","DEBUB","OROMIYA","AFAR","AMHARA","BENSHANGUL","SOMMALIA","HARERI");
        model.addAttribute("gallaryHolder", newGallary);
        model.addAttribute("regions", regions);
        return "gallaryFileUploader";
    }
    @PostMapping("/gallary/upload")
    public String uplaodContentGallary(@RequestParam("picture") MultipartFile imgFile,@ModelAttribute("gallaryHolder") Gallary gallary ) throws IOException{
        System.out.println(gallary);
        gallary.setGallaryPictureBytes(imgFile.getBytes());
        gallaryService.save(gallary);
        return "index";
    }
    @GetMapping("/img/gallary/{id}")
    public void getSiteImage(@PathVariable("id") int id,HttpServletResponse response) throws IOException{
        Gallary gallary= gallaryService.findbyId(id);
        InputStream in = new ByteArrayInputStream(gallary.getGallaryPictureBytes());
        IOUtils.copy(in, response.getOutputStream());

    }
    @GetMapping("/gallary/get")
    public String  getGalaryByRegion( Model model, @RequestParam("selected") String region){
        List<Gallary> gallaries;
        if(region.equals("ALL")){
                 gallaries = gallaryService.findAllGallary();
        }
        else{
            gallaries = gallaryService.findByRegion(region);
        }
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
        List<String> regions = Arrays.asList("ALL","TIGRAY","DEBUB","OROMIYA","AFAR","AMHARA","BENSHANGUL","SOMMALIA","HARERI");
        model.addAttribute("gallaries", gallaries);
        model.addAttribute("regions", regions);
        return "gallary";
    }
    
    
}
