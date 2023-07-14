package cn.mafangui.hotel.controller.worker;

import cn.mafangui.hotel.entity.Order;
import cn.mafangui.hotel.enums.OrderStatus;
import cn.mafangui.hotel.response.AjaxResult;
import cn.mafangui.hotel.response.MsgType;
import cn.mafangui.hotel.response.ResponseTool;
import cn.mafangui.hotel.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Giao diện đơn hàng
 */
@RestController
@RequestMapping(value = "/op/order")
public class OpOrderController {
    @Autowired
    private OrderService orderService;

    /**
     * Thêm đơn đặt phòng
     * Trạng thái đơn hàng mặc định là chưa thanh toán
     */
    @RequestMapping(value = "/add")
    public AjaxResult addOrder(int orderTypeId, String orderType, int userId, String name, String phone, int roomTypeId, String roomType,
                               @DateTimeFormat(pattern = "yyyy-MM-dd") Date orderDate, Integer orderDays, Double orderCost) {
        Order order = new Order(orderTypeId, orderType, userId, name, phone, roomTypeId,
                roomType, orderDate, orderDays, OrderStatus.UNPAID.getCode(), orderCost);
        int re = orderService.addOrder(order);
        if (re != 1) return ResponseTool.failed(MsgType.FAILED);
        return ResponseTool.success("Thêm thành công.");
    }


    @RequestMapping(value = "/delete")
    public AjaxResult deleteOrder(int orderId) {
        int re = orderService.delete(orderId);
        if (re != 1) return ResponseTool.failed(MsgType.FAILED);
        return ResponseTool.success("Xóa thành công.");
    }


    @RequestMapping(value = "/update")
    public AjaxResult updateOrder(int orderId, int orderTypeId, String orderType, int userId, String name, String phone, int roomTypeId, String roomType,
                                  @DateTimeFormat(pattern = "yyyy-MM-dd") Date orderDate, Integer orderDays, Double orderCost) {
        Order order = new Order(orderTypeId, orderType, userId, name, phone, roomTypeId,
                roomType, orderDate, orderDays, OrderStatus.UNPAID.getCode(), orderCost);
        int re = orderService.update(order);
        if (re != 1) return ResponseTool.failed(MsgType.FAILED);
        return ResponseTool.success("Sửa thành công.");
    }

    /**
     * Thanh toán đơn hàng
     * @param orderId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/pay")
    public AjaxResult payOrder(int orderId) {
        int re = orderService.payOrder(orderId);
        if (re != 1) return ResponseTool.failed(MsgType.FAILED);
        return ResponseTool.success("Thanh toán thành công.");
    }

    /**
     * Hủy đơn hàng
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/cancel")
    public AjaxResult cancelOrder(int orderId) {
        int re = orderService.cancelOrder(orderId);
        if (re != 1) return ResponseTool.failed(MsgType.FAILED);
        return ResponseTool.success("Hủy thành công.");
    }

    /**
     * Đơn hàng quá thời gian
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/overtime")
    public int orderOver(int orderId) {
        Order order = new Order(orderId, OrderStatus.OVERTIME.getCode());
        return orderService.update(order);
    }


    @RequestMapping(value = "")
    public AjaxResult getAllOrder() {
        return ResponseTool.success(orderService.AllOrders());
    }

    @RequestMapping(value = "/count")
    public AjaxResult getOrderCount() {
        return ResponseTool.success(orderService.getOrderCount());
    }

    /**
     * Tìm tất cả đơn hàng theo userID
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/{userId}")
    public AjaxResult getByUser(@PathVariable int userId) {
        return ResponseTool.success(orderService.selectByUserId(userId));
    }

    /**
     * Tìm đơn hàng theo số đơn hàng
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/{orderId}")
    public AjaxResult getById(@PathVariable Integer orderId) {
        return ResponseTool.success(orderService.selectById(orderId));
    }

    /**
     * Tìm đơn hàng theo tên và số điện thoại đặt trước
     * Chủ yếu được sử dụng cho khácsh hàng nhận phòng
     * @param name
     * @param phone
     * @return
     */
    @RequestMapping(value = "/withNameAndPhone")
    public AjaxResult getByNameAndPhone(String name, String phone) {

        return ResponseTool.success(orderService.selectByNameAndPhone(name, phone));
    }
}
