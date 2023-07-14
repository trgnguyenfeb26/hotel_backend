package cn.mafangui.hotel.controller.worker;

import cn.mafangui.hotel.entity.RoomType;
import cn.mafangui.hotel.response.AjaxResult;
import cn.mafangui.hotel.response.ResponseTool;
import cn.mafangui.hotel.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/op/room-type")
public class OpRoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;

    /**
     * Lấy tất cả loại phòng
     *
     * @return
     */
    @RequestMapping(value = "")
    public AjaxResult getAllRoomType() {
        return ResponseTool.success(roomTypeService.findAllType());
    }

    /**
     * Lấy tất cả loại phòng có số lượng phòng còn trống
     *
     * @return
     */
    @RequestMapping(value = "/rest")
    public AjaxResult findAllRestRoomType() {
        return ResponseTool.success(roomTypeService.findAllRestType());
    }

    /**
     * Lấy thông tin loại phòng theo ID
     *
     * @param typeId
     * @return
     */
    @RequestMapping(value = "/{typeId}")
    public AjaxResult getById(@PathVariable int typeId) {
        return ResponseTool.success(roomTypeService.selectById(typeId));
    }

    /**
     * Thêm loại phòng mới
     *
     * @param roomType
     * @param price
     * @param discount
     * @param area
     * @param bedNum
     * @param bedSize
     * @param windows
     * @param remark
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public AjaxResult addRoomType(String roomType, Double price, Double discount, Integer area,
                                  Integer bedNum, String bedSize, Integer windows, String remark, Integer rest) {
        RoomType rt = new RoomType(roomType, remark, price, discount, area, bedNum, bedSize, windows);
        rt.setRest(rest);
        int result = roomTypeService.insert(rt);
        if (result != 1)
            return ResponseTool.failed("Thêm không thành công");
        return ResponseTool.success("Thêm thành công");
    }

    /**
     * Cập nhật thông tin loại phòng
     *
     * @param typeId
     * @param roomType
     * @param price
     * @param discount
     * @param area
     * @param bedNum
     * @param bedSize
     * @param windows
     * @param rest
     * @param remark
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public AjaxResult updateRoomType(Integer typeId, String roomType, Double price, Double discount, Integer area,
                                     Integer bedNum, String bedSize, Integer windows, Integer rest, String remark) {
        RoomType rt = new RoomType(roomType, remark, price, discount, area, bedNum, bedSize, windows);
        rt.setTypeId(typeId);
        rt.setRest(rest);
        int result = roomTypeService.update(rt);
        if (result != 1)
            return ResponseTool.failed("Cập nhật không thành công");
        return ResponseTool.success("Cập nhật thành công");
    }

    /**
     * Xóa loại phòng
     *
     * @param typeId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/delete/{typeId}")
    public AjaxResult deleteRoomType(@PathVariable Integer typeId) {
        int result = roomTypeService.delete(typeId);
        if (result != 1)
            return ResponseTool.failed("Xóa không thành công");
        return ResponseTool.success("Xóa thành công");
    }

}
