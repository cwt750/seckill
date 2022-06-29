package com.example.kill.controller;


import com.example.kill.pojo.GoodsDetail;
import com.example.kill.pojo.GoodsVo;
import com.example.kill.pojo.User;
import com.example.kill.redis.GoodsKey;
import com.example.kill.redis.RedisService;
import com.example.kill.result.Result;
import com.example.kill.service.GoodsService;
import com.example.kill.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;


    @Resource
    ThymeleafViewResolver thymeleafViewResolver;



    @RequestMapping("/list")
    @ResponseBody
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {

        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if(!StringUtils.isEmpty(html)) {
            return html;
        }

        List<GoodsVo> goodsVos = goodsService.listGoodVo();
        model.addAttribute("goodsVos", goodsVos);
        IWebContext ctx = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());


        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if(!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html , 60);
        }
        return html;
    }

    @RequestMapping("/detail/{goodsId}")
    @ResponseBody
    public String goodsDetail(HttpServletRequest request, HttpServletResponse response, Model model, User user, @PathVariable("goodsId")long goodsId) {
        model.addAttribute("user", user);

        String html = redisService.get(GoodsKey.getGoodsDetail, ""+goodsId, String.class);
        if(!StringUtils.isEmpty(html)) {
            return html;
        }

        GoodsVo goods = goodsService.getGoodsVoById(goodsId);
        model.addAttribute("goods",goods);

        long start = goods.getStartDate().getTime();
        long end = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int seckillStatus = 0;
        int reSeconds = 0;

        if(now < start) {
            seckillStatus = 0;
            reSeconds = (int)((start-now)/1000);
        } else if(now > end) {
            seckillStatus = 2;
            reSeconds = -1;
        } else {
            seckillStatus = 1;
            reSeconds = 0;
        }
        model.addAttribute("reSeconds",reSeconds);
        model.addAttribute("seckillStatus",seckillStatus);

        IWebContext ctx = new WebContext(request,response,
                request.getServletContext(),request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        if(!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsDetail, ""+goodsId, html , 60);
        }
        return html;
    }
}
