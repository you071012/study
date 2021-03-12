package com.ukar.study.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ukar.study.entity.User;
import com.ukar.study.mapper.UserMapper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * @author jia.you
 * @date 2020/05/09
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private UserMapper userMapper;

    @Value("${server.port}")
    private String aa;

    @RequestMapping("/index")
    public String index(){
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(User::getName, "ukar");
        User user = userMapper.selectOne(queryWrapper);
        return user == null ? "NULL" : user.toString();
    }

    @RequestMapping("/page")
    public List<User> page(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByAsc(User::getId);
        Page<User> page = new Page<>(1,1);
        page = userMapper.selectPage(page, queryWrapper);
        List<User> records = page.getRecords();
        return records;
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String post(@RequestBody User user){
        return user.toString();
    }

    @RequestMapping("/img")
    public void getImg(@RequestParam("imgUrl") String imgUrl, HttpServletResponse response){

        ServletOutputStream out = null;
        FileInputStream ips = null;
        try{
            String imgPath = "/Users/youjia/Desktop/tools/ukar/doc/" + imgUrl;
            String type = "jpeg";
            if(type.equals("png")){
                response.setContentType("image/png");
            }

            if(type.equals("jpeg")){
                response.setContentType("image/jpeg");
            }

            out = response.getOutputStream();
            ips = new FileInputStream(new File(imgPath));

            int len;
            byte[] buffer = new byte[1024*10];
            while ((len = ips.read(buffer)) != -1){
                out.write(buffer, 0, len);
            }
            out.flush();


        }catch (Exception e){
            System.out.println(e);
        }
        IOUtils.closeQuietly(out);
        IOUtils.closeQuietly(ips);
    }
}
