package cn.mafangui.hotel.controller.user;

import cn.mafangui.hotel.entity.User;
import cn.mafangui.hotel.response.AjaxResult;
import cn.mafangui.hotel.response.MsgType;
import cn.mafangui.hotel.response.ResponseTool;
import cn.mafangui.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Cập nhật thông tin người dùng
     * @param userId
     * @param name
     * @param gender
     * @param phone
     * @param email
     * @param address
     * @param idcard
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/update")
    public AjaxResult userUpdate(Integer userId, String name, String gender, String phone,
                                 String email, String address, String idcard, HttpServletRequest request){
        HttpSession session = request.getSession();
        if (!session.getAttribute("userId").equals(userId)){
            return ResponseTool.failed(MsgType.PERMISSION_DENIED);
        }
        User user = new User();
        user.setUserId(userId);
        user.setName(name);
        user.setGender(gender);
        user.setPhone(phone);
        user.setEmail(email);
        user.setAddress(address);
        user.setIdcard(idcard);
        if(userService.updateUser(user)==1)
            return ResponseTool.success("Cập nhật thành công");
        return ResponseTool.success("Cập nhật thất bại, vui lòng kiểm tra hoặc thử lại sau");
    }

    /**
     * Thay đổi mật khẩu
     * @param username
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/updatePassword")
    public AjaxResult updatePassword(String username,String oldPassword,String newPassword){
        User user = userService.selectByUsernameAndPassword(username,oldPassword);
        if (user == null){
            return ResponseTool.failed("Mật khẩu không đúng");
        }
        user.setPassword(newPassword);
        if(userService.updateUser(user)==1)
            return ResponseTool.success("Thay đổi mật khẩu thành công");
        return ResponseTool.failed("Thay đổi mật khẩu thất bại");
    }

    /**
     * Lấy thông tin cá nhân
     * @param request
     * @return
     */
    @RequestMapping(value = "/profile")
    public AjaxResult getProfile(HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        User user = userService.selectByUsername(username);
        if(user==null) return ResponseTool.failed("Lỗi không xác định");
        user.setPassword(null);
        StringBuilder sb = new StringBuilder(user.getIdcard());
        sb.replace(5,12,"********");
        user.setIdcard(sb.toString());
        return ResponseTool.success(user);
    }

    /**
     * Đăng xuất
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public AjaxResult logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("userId");
        session.removeAttribute("username");
        return ResponseTool.success("Đăng xuất thành công");
    }
}
