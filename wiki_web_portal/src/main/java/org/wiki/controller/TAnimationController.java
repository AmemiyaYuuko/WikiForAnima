package org.wiki.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wiki.pojo.Wiki_Animation;
import org.wiki.service.IAnimationService;

import java.util.List;

@Controller
@RequestMapping("/animPortal")
public class TAnimationController {
    @Reference
    private IAnimationService animationService;

    @RequestMapping("/test")
    public String Test(Model model){
        List<Wiki_Animation> animationList=animationService.getAnimList();
        model.addAttribute("animList",animationList);
        return "test";
    }
}
