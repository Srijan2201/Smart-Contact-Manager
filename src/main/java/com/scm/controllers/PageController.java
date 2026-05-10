package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PageController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model) {  // sends dynamic data to html
        System.out.println("Home page handler");
        model.addAttribute("Company", "Tech Mahindra");
        model.addAttribute("name", "Srijan");
        model.addAttribute("linkedin", "https://www.linkedin.com/in/srijan-shadangi-8bab32221/");
        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage(Model model){
        model.addAttribute("isLogin", true);
        System.out.println("About page loading");
        return "about";
    }

    @RequestMapping("/services")
    public String servicesPage(){
        System.out.println("About page services");
        return "services";
    }

    @RequestMapping("/contact")
    public String contact(){
        System.out.println("Contact page services");
        return "contact";
    }

    // this is showing login page - view
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    // registration page
    @GetMapping("/register")
    public String register(Model model){
        
        UserForm userForm = new UserForm();
        userForm.setName("Srijan");
        model.addAttribute("userForm", userForm);

        return "register";
    }

    //Processing register

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult rBindingResult,HttpSession session){
        System.out.println("Processing registration");

        System.out.println(userForm);

        if(rBindingResult.hasErrors()){
            return "register";
        }

        // User user =User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .about(userForm.getAbout())
        // .phoneNumber(userForm.getPhoneNumber())
        // .profilePic("")
        // .build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("https://instagram.fidr4-1.fna.fbcdn.net/v/t51.2885-19/624879395_18420734737141199_6363909720479629720_n.jpg?efg=eyJ2ZW5jb2RlX3RhZyI6InByb2ZpbGVfcGljLmRqYW5nby4xMDgwLmMyIn0&_nc_ht=instagram.fidr4-1.fna.fbcdn.net&_nc_cat=111&_nc_oc=Q6cZ2QFjB6HFZ3jOqvJAn8X8PCHy0mKvdqfswWvR9E8M7EYRtZdjg62s2TZ0Kt2jaOB_udL9vtZkyu0OqCRoqyrgFowr&_nc_ohc=6gsVwsIcwvEQ7kNvwEH17ds&_nc_gid=tPjU5gCB2hOxHuoSof9h4g&edm=AP4sbd4BAAAA&ccb=7-5&oh=00_AfusMauqgwPZ3fcQi7XoAIAUaF8D2e3CafOZ9KHoDeoOkA&oe=698F457B&_nc_sid=7a9f4b");
        User savedUser = userService.saveUser(user);
        System.out.println("user saved :");

        Message message = Message.builder().content("Registration Successful").type(MessageType.blue).build();
        session.setAttribute("message",message);

        return "redirect:/register";
    }
}
