<template>
  <div id="home">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card class="box-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span>Hoạt động nhanh</span>
          </div>
          <el-button type="primary"  @click="dialogFormVisible = true">Đăng ký nhận phòng</el-button>
          <el-button type="primary">Trả phòng và thanh toán</el-button>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="box-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span>Ngày hiện tại</span>
          </div>
          <p>{{ new Date() | formatDay }}</p>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="box-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span>Hiển thị dữ liệu</span>
          </div>
          <div>Số lượng người dùng đã đăng ký: <el-tag>{{userCount}}</el-tag></div>
          <div>Số lượng đơn đặt hàng đã được chấp nhận: <el-tag>{{orderCount}}</el-tag></div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card class="box-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span>Tỷ lệ lưu trú trong phòng</span>
          </div>
          <div id="liveRate" style="left:0;width: 100%;height: 250px"></div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="box-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span>Tỷ lệ loại phòng</span>
          </div>
          <div id="roomType" style="left:0;width: 100%;height: 250px"></div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card class="box-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span>Số lượng đơn đặt hàng</span>
          </div>
          <div id="order" style="left:0;width: 100%;height: 250px"></div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="box-card" shadow="hover">
          <div slot="header" class="clearfix">
            <span>Tỷ lệ đơn đặt hàng theo loại phòng</span>
          </div>
          <div id="orderType" style="left:0;width: 100%;height: 250px"></div>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog title="Check in" :visible.sync="dialogFormVisible">
      <el-form inline :model="form">
        <el-form-item label="Tên người đặt phòng" >
          <el-input v-model="form.name" autoComplete="on"></el-input>
        </el-form-item>
        <el-form-item label="Đặt trước một số điện thoại di động" >
          <el-input v-model="form.phone" autoComplete="on"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="findOrder()">Tìm kiếm</el-button>
        </el-form-item>
      </el-form>
      <div v-if="isOrderShown && order!=null">
        số thứ tự:{{ order.orderId }}<br>
        Làm thế nào để đặt: {{ order.orderType }}<br>
        Đặt loại phòng: {{ order.roomType }}<br>
        Ngày đặt phòng: {{ order.orderDate | formatDay }}<br>
        Số ngày đặt: {{ order.orderDays }} ngày<br>
        Phí đặt phòng: {{ order.orderCost }}
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelIn()">Hủy bỏ</el-button>
        <el-button type="primary" @click="checkIn()">Check in</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
    import {getOrderByNameAndPhone, getOrderCount} from "../../api/order";
    import {getAllUser ,getUserCount} from "../../api/user"
    import {checkIn} from "../../api/checkIn"
    export default {
    name: 'Home',
    data(){
        return{
            roomNumber: null,
            dialogFormVisible: false,
            userCount: 1203,
            orderCount: 12032,
            form: {
                name: '',
                phone: ''
            },
            isOrderShown: false,
            order: null,
        }
    },
    created: function () {
        this.getCount();
    },
    mounted() {
        this.rtChart()
        this.lrChart()
        this.orderChart()
        this.ortChart()
    },
    methods:{
        getCount(){
            getUserCount().then(response => {
                this.userCount = response.data;
            });
            getOrderCount().then(res => {
                this.orderCount = res.data;
            });
        },
        rtChart(){
            var rtChart =  this.$echarts.init(document.getElementById('roomType'),'light')
            rtChart.setOption({
                tooltip: {},
                series: [{
                    name: '比例',
                    type: 'pie',
                    radius: '55%',
                    data: [
                        {value: 35, name: 'Phòng đơn'},
                        {value: 55, name: 'Phòng giường đôi'},
                        {value: 45, name: 'Phòng hai giường'},
                        {value: 55, name: 'Phòng giường đôi sang trọng'},
                        {value: 45, name: 'Phòng hai giường sang trọng'},
                        {value: 35, name: 'Phòng giường đôi cao cấp'},
                    ]
                }]
            });
        },
        lrChart(){
            var myChart =  this.$echarts.init(document.getElementById('liveRate'),'light')
            myChart.setOption({

                xAxis: {
                    type: 'category',
                    data: [1,2,3,4,5,6,7,8,9,10,11,12],
                    axisLabel: {
                        formatter: '{value} tháng'
                    }
                },
                yAxis: {
                    type: 'value',
                    axisLabel: {
                        formatter: '{value} %'
                    }
                },
                series: [{
                    data: [50, 68, 55.4, 53.2, 76.6, 80.6, 47.6,45.7,67.2,58.3,78.4,94.2],
                    type: 'line'
                }]
            });
        },
        orderChart(){
            var myChart =  this.$echarts.init(document.getElementById('order'),'light')
            myChart.setOption({
                itemStyle:{
                    color: '#409EFF',
                },
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // Điểm chỉ trên trục tọa độ, mặc định là đường thẳng
                        type : 'line'        // Có thể chọn: 'line' hoặc 'shadow'
                    },
                },
                xAxis: {
                    type: 'category',
                    data: [1,2,3,4,5,6,7,8,9,10,11,12],
                    axisLabel: {
                        formatter: '{value} tháng'
                    }
                },
                yAxis: {
                    type: 'value',
                    axisLabel: {
                        formatter: '{value} đơn'
                    }
                },
                series: [{
                    name: 'Số lượng đơn hàng',
                    data: [432, 568, 469, 532, 766, 806, 476,457,672,583,784,942],
                    type: 'bar'
                }]
            });
        },
        ortChart(){
            var rtChart =  this.$echarts.init(document.getElementById('orderType'),'light')
            rtChart.setOption({
                tooltip: {},
                series: [{
                    name: '比例',
                    type: 'pie',
                    radius: '55%',
                    data: [
                        {value: 35, name: 'Phòng đơn'},
                        {value: 15, name: 'Phòng giường đôi'},
                        {value: 10, name: 'Phòng hai giường'},
                        {value: 15, name: 'Phòng giường đôi sang trọng'},
                        {value: 5, name: 'Phòng hai giường sang trọng'},
                        {value: 20, name: 'Phòng giường đôi cao cấp'},
                    ]
                }]
            });
        },
        findOrder(){
            getOrderByNameAndPhone(this.form).then(res => {
                this.order = res.data;
                console.log('eeeeeeeeeeee',this.form)
            })
            if (this.order == null) {
                this.$message.warning("Không tìm thấy thông tin đặt phòng liên quan! Vui lòng kiểm tra lại")
                return
            }else {
                this.isOrderShown = true;
            }
        },
        cancelIn(){
            this.dialogFormVisible = false
            this.order = null
            this.isOrderShown = false
        },
        checkIn(){
            this.dialogFormVisible = false
            checkIn
            this.order = null
            this.isOrderShown = false
        }
    },
}
</script>

<style scoped>

.el-row {
    margin: 10px 5px;
}
.el-card{
    text-align: center;
}
</style>
