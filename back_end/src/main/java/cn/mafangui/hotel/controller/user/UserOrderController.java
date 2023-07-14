package cn.mafangui.hotel.controller.user;

import cn.mafangui.hotel.entity.Order;
import cn.mafangui.hotel.entity.User;
import cn.mafangui.hotel.enums.OrderStatus;
import cn.mafangui.hotel.response.AjaxResult;
import cn.mafangui.hotel.response.MsgType;
import cn.mafangui.hotel.response.ResponseTool;
import cn.mafangui.hotel.service.OrderService;
import cn.mafangui.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * Giao diện đơn hàng
 */
@RestController
@RequestMapping(value = "/user/order")
public class UserOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    private static Logger logger = LoggerFactory.getLogger(UserOrderController.class);

    /**
     * Thêm đặt hàng
     * Trạng thái đơn hàng mặc định là chưa thanh toán
     * 
     * @param orderTypeId
     * @param orderType
     * @param userId
     * @param name
     * @param phone
     * @param roomTypeId
     * @param roomType
     * @param orderDate
     * @param orderDays
     * @param orderCost
     * @return
     */
    @RequestMapping(value = "/add")
    public AjaxResult addOrder(int orderTypeId, String orderType, int userId, String name, String phone, int roomTypeId, String roomType, @DateTimeFormat(pattern = "yyyy-MM-dd") Date orderDate, Integer orderDays, Double orderCost) {
       logger.info("orderTypeId: {}, orderType: {}, userId: {}, name: {}, phone: {}, roomTypeId: {}, roomType: {}, orderDate: {}, orderDays: {}, orderCost: {}", orderTypeId, orderType, userId, name, phone, roomTypeId, roomType, orderDate, orderDays, orderCost);
        
        Order order = new Order(orderTypeId, orderType, userId, name, phone, roomTypeId,
                roomType, orderDate, orderDays, OrderStatus.UNPAID.getCode(), orderCost);
        // log order
          logger.info("Order object: {}", order);
        int re = orderService.addOrder(order);
        if (re != 1)
            ResponseTool.failed(MsgType.FAILED);
        return ResponseTool.success(MsgType.SUCCESS);
    }

    /**
     * Xóa đơn hàng
     * 
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/delete")
    public AjaxResult deleteOrderByUser(int orderId) {
        Order order = new Order(orderId, OrderStatus.WAS_DELETED.getCode());
        int re = orderService.update(order);
        if (re != 1)
            ResponseTool.failed(MsgType.FAILED);
        return ResponseTool.success(MsgType.SUCCESS);
    }

    /**
     * Thanh toán đơn hàng
     * 
     * @param orderId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/pay")
    public AjaxResult payOrder(Integer orderId, String username, String password) {
        User user = userService.selectByUsernameAndPassword(username, password);
        if (user == null)
            return ResponseTool.failed("Mật khẩu không đúng");
        int re = orderService.payOrder(orderId);
        if (re != 1)
            ResponseTool.failed(MsgType.FAILED);
        return ResponseTool.success(MsgType.SUCCESS);
    }

    /**
     * Hủy đơn hàng
     * 
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/cancel")
    public AjaxResult cancelOrder(int orderId) {
        int re = orderService.cancelOrder(orderId);
        if (re != 1)
            ResponseTool.failed(MsgType.FAILED);
        return ResponseTool.success(MsgType.SUCCESS);
    }

    /**
     * Khách hàng truy vấn tất cả các đơn hàng cá nhân (không bao gồm những đơn hàng
     * bị xóa bởi khách hàng)
     * 
     * @param userId
     * @return
     */
    @RequestMapping(value = "")
    public AjaxResult getAllByUser(int userId) {
        return ResponseTool.success(orderService.UsersAllOrders(userId));
    }

    /**
     * Tìm đơn hàng theo số đơn hàng
     * 
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/{orderId}")
    public AjaxResult getById(@PathVariable int orderId) {
        return ResponseTool.success(orderService.selectById(orderId));
    }
}
