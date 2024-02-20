package xyz.pplax.pplaxblog.admin.controller;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.pplax.pplaxblog.xo.service.feedback.FeedbackService;
import xyz.pplax.pplaxblog.xo.service.user.UserService;

/**
 * 反馈表 Controller
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private UserService userService;

    private static final Logger log = LogManager.getLogger(FeedbackController.class);

}

