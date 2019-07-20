package com.qf.controller;

import com.qf.pojo.Item;
import com.qf.pojo.PageInfo;
import com.qf.service.ItemService;
import com.qf.vo.ResultVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.qf.constant.SsmConstant.ITEM_ADD_UI;
import static com.qf.constant.SsmConstant.REDIRECT;

@Controller
@RequestMapping("/item")//映射请求路径
public class ItemController {
    @Autowired
    private ItemService itemService;
    @GetMapping("/list")
    public String list(String name,
                       @RequestParam(value ="page",defaultValue = "1")Integer page,
                       @RequestParam(value ="size",defaultValue = "5")Integer size,
                       Model model){
        PageInfo<Item> pageInfo = itemService.findItemAndLinmit(name,page,size);
        model.addAttribute("pageInfo", pageInfo);
         model.addAttribute("name", name);
        return "item/item_list";
    }
    @GetMapping("/add-ui")
    public String addUI(String addInfo,Model model){
        model.addAttribute("addInfo", addInfo);
        return "item/item_add";
    }
    @Value("${pic_types}")
    public String picType;
    @PostMapping("/add")
    public String add(RedirectAttributes redirectAttributes, @Valid Item item, BindingResult bindingResult, MultipartFile picFile, HttpServletRequest request) throws IOException {
        if(bindingResult.hasErrors()){
            String msg = bindingResult.getFieldError().getDefaultMessage();
//            参数不合法
            redirectAttributes.addAttribute("addInfo", msg);
            return REDIRECT+ITEM_ADD_UI;
        }
//        =============================
        //1对图片的大小做校验 1024*1024*5
        long size = picFile.getSize();
        if(size>5242880L){
            //图片过大
            redirectAttributes.addAttribute("addInfo", "图片过大");
            return REDIRECT+ITEM_ADD_UI;

        }
        boolean flag = false;
        //2.对图片的类型做校验 jpg,png,gif
        String[] types = picType.split(",");
        for (String type : types) {
            if(StringUtils.endsWithIgnoreCase(picFile.getOriginalFilename(), type)){
                //图片类型正确

                flag = true;
                break;
            }
        }
        if(!flag){
            //图片类型不正确
            redirectAttributes.addAttribute("addInfo", "图片类型不正确");
            return REDIRECT+ITEM_ADD_UI;

        }
        //3.校验图片是否损坏

        BufferedImage image = ImageIO.read(picFile.getInputStream());
        if(image==null){
            //图片已经损坏
            redirectAttributes.addAttribute("addInfo", "图片已损坏");
            return REDIRECT+ITEM_ADD_UI;
        }
//        =======================将图片保存到本地=========================
        String prefix = UUID.randomUUID().toString();
        String suffix = StringUtils.substringAfterLast(picFile.getOriginalFilename(), ".");
        String newName = prefix+"."+suffix;
        //2将图片保存到本地
        String path =request.getServletContext().getRealPath("")+"\\static\\img\\"+newName;
        File file = new File(path);
        picFile.transferTo(file);
        String pic = "http://localhost:8888/static/img/" +newName;
        item.setPic(pic);
        Integer count = itemService.save(item);
        if(count==1){
            return REDIRECT+"/item/list";
        }else{
            //TODO 添加商品信息失败
            redirectAttributes.addAttribute("addInfo", "添加商品失败");
            return REDIRECT+ITEM_ADD_UI;
        }



    }
    //根据id删除商品信息
//    /item/del/14
//    DELETE
    @DeleteMapping("/del/{id}")
    @ResponseBody
    public ResultVo del(@PathVariable Long id){
        //1.调用service删除商品
        Integer count = itemService.del(id);
         if(count==1){
            //删除成功
            return new ResultVo(0, "成功", null);
        }else{
            return new ResultVo(1, "删除商品失败", null);

        }
    }
}
