package application.controller;

import application.controller.json_model.Success;
import application.controller.json_model.User;
import application.model.*;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController //@RestController注解相当于@ResponseBody ＋ @Controller合在一起的作用
                //使用@Controller 注解，在对应的方法上，视图解析器可以解析return 的jsp,html页面，并且跳转到相应页面
                //若返回json等内容到页面，则需要加@ResponseBody注解
@CrossOrigin //解决跨域问题
public class MainController {
    @Autowired //自动注入，springboot框架帮做的
    private UserService userService;

    @Autowired
    private application.service.MailService MailService;

    //请求映射
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Success login(@RequestBody User user) { //返回给前端一个Success对象，看是否成功登录

        Success s = new Success();
        s.setSuccess(false);

        String name = user.getUser_name();
        String password = user.getUser_pwd();
        String identity = user.getIdentity();

        //首先判断user_name是否已经存在
        if (identity.equals("teacher")) {
            Teacher tea = userService.findTeacherByName(name);
            //再判断密码是否一致
            if (tea != null) {
                if (tea.getPassword().equals(password)) {
                    s.setSuccess(true);
                    s.setId(tea.getId()); //获取老师的id，赋给Success类,返回给前端
                }
            }
        } else if (identity.equals("student")) {
            Student stu = userService.findStudentByName(name);
            //再判断密码是否一致
            if (stu != null) {
                if (stu.getPassword().equals(password)) {
                    s.setSuccess(true);
                    s.setId(stu.getId());
                }
            }
        }

        return s;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Success register(@RequestBody UserTemp user) {
        String name = user.getUser_name();
        String identity = user.getIdentity();
        String password = user.getUser_pwd();

        Success success = new Success();
        success.setSuccess(false);

        //首先判断user_name是否已经存在
        boolean if_exist = (userService.findUser(name));
        if (!if_exist) {
            if (identity.equalsIgnoreCase("teacher")) {
                Teacher teacher = new Teacher();
                teacher.setName(name);
                teacher.setPassword(password);
                teacher = userService.saveTeacher(teacher); //把teacher存入neo4j中
                System.out.println("      老师id:    "+ teacher.getId() + "!!!!!!!!!!!");
                success.setSuccess(true);
                success.setId(teacher.getId());

            } else {
                Student student = new Student();
                //student.setId(id);id自动生成了
                student.setName(name);
                student.setPassword(password);
                student = userService.saveStudent(student);
                System.out.println("      学生id:    "+ student.getId() + "!!!!!!!!!!!");
                success.setSuccess(true);
                success.setId(student.getId());
            }
            return success;
        }
        else {
            return success;
        }
    }

    @RequestMapping(value = "/modify_password", method = RequestMethod.POST) //该功能前端没有写相应接口
    public Success modifyPassword(@RequestBody UserTemp user) {

        String name = user.getUser_name();
        String password = user.getUser_pwd();

        //首先判断user_name是否已经存在
        boolean if_exist = userService.findUser(name);

        Student stu = userService.findStudentByName(name);
        Teacher tea = userService.findTeacherByName(name);

        if (stu != null) {
            stu.setPassword(password);
            userService.saveStudent(stu);
        }

        if (tea != null) {
            tea.setPassword(password);
            userService.saveTeacher(tea);
        }

        Success s = new Success();
        s.setSuccess(if_exist);
        return s;
    }
}
