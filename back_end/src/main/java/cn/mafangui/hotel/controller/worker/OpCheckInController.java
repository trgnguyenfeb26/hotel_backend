package cn.mafangui.hotel.controller.worker;

import cn.mafangui.hotel.entity.CheckIn;
import cn.mafangui.hotel.response.AjaxResult;
import cn.mafangui.hotel.response.ResponseTool;
import cn.mafangui.hotel.service.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/op/check-in")
public class OpCheckInController {

    @Autowired
    private CheckInService checkInService;

    @RequestMapping(value = "/in")
    public AjaxResult addCheckIn(int orderId, int peoCount, String persons, String ids){
        CheckIn checkIn = new CheckIn();
        checkIn.setOrderId(orderId);
        checkIn.setPeoCount(peoCount);
        checkIn.setPersons(persons);
        checkIn.setIds(ids);
        return ResponseTool.success(checkInService.checkIn(checkIn));
    }

    @RequestMapping(value = "/out")
    public AjaxResult checkOut(String roomNumber) {
        if(checkInService.checkOut(roomNumber)<0)
            return ResponseTool.failed("Check-out thất bại");
        return ResponseTool.success("Check-out thành công");
    }

    @RequestMapping(value = "/delete")
    public AjaxResult deleteCheckIn(int checkId){
        if(checkInService.delete(checkId)!=1)
            return ResponseTool.failed("Xóa thất bại");
        return ResponseTool.success("Xóa thành công");
    }

    @RequestMapping(value = "/update")
    public AjaxResult update(int checkId,String roomNumber){
        CheckIn checkIn = new CheckIn();
        checkIn.setCheckInId(checkId);
        checkIn.setRoomNumber(roomNumber);
        if(checkInService.update(checkIn)!=1)
            return ResponseTool.failed("Cập nhật thất bại");
        return ResponseTool.success("Cập nhật thành công");
    }

    @RequestMapping(value = "/{checkId}")
    public AjaxResult getById(@PathVariable int checkId){
        return ResponseTool.success(checkInService.selectById(checkId));
    }

    @RequestMapping(value = "")
    public AjaxResult getAll(){
        return ResponseTool.success(checkInService.selectAll());
    }

}
