<template>
  <el-card class="app-container" shadow="always">
    <div slot="header">
      <span>Thêm đơn đặt phòng</span>
    </div>
    <el-form ref="form1" :model="form1">
      <el-form-item
        :rules="[
          { required: true, message: 'Không được để trống' }
        ]"
        label="Người đặt"
        prop="name"
      >
        <el-input v-model="form1.name" placeholder="Nhập tên người đặt" />
      </el-form-item>
      <el-form-item
        :rules="[
          { required: true, message: 'Không được để trống' }
        ]"
        label="Số điện thoại"
        prop="phone"
      >
        <el-input v-model="form1.phone" placeholder="Nhập số điện thoại" />
      </el-form-item>
      <el-form-item
        :rules="[
          { required: true, message: 'Không được để trống' }
        ]"
        label="Loại phòng"
      >
        <el-select v-model="form1.roomTypeId" :change="idToType(form1.roomTypeId, null)" placeholder="Chọn loại phòng">
          <el-option
            v-for="rt in roomTypeList"
            v-show="rt.rest > 0"
            :label="rt.roomType"
            :value="rt.typeId"
            :key="rt.typeId"
          >
            {{ rt.typeId + ' ' + rt.roomType + '(' + rt.price + ' đồng)' }}
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item
        :rules="[
          { required: true, message: 'Không được để trống' }
        ]"
        label="Ngày đặt"
      >
        <el-date-picker
          v-model="orderDateRange"
          type="daterange"
          @change="calcDays"
          range-separator=" đến "
          start-placeholder="Ngày bắt đầu"
          end-placeholder="Ngày kết thúc"
          :picker-options="expireTimeOption"
        ></el-date-picker>
      </el-form-item>
      <el-form-item
        :rules="[
          { required: true, message: 'Không được để trống' }
        ]"
        label="Số ngày ở"
        prop="orderDays"
      >
        {{ form1.orderDays + ' đêm' }}
      </el-form-item>
      <el-form-item
        :rules="[
          { required: true, message: 'Không được để trống' }
        ]"
        label="Hình thức đặt"
      >
        <el-select v-model="form1.orderTypeId" :change="idToType(null, form1.orderTypeId)" placeholder="Chọn hình thức đặt">
          <el-option
            v-for="t in orderTypeList"
            :label="t.type"
            :value="t.typeId"
            :key="t.typeId"
          >
            {{ t.typeId + ' ' + t.type }}
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item
        :rules="[
          { required: true, message: 'Không được để trống' },
          { type: 'number', message: 'Phải là giá trị số' }
        ]"
          label="Chi phí đặt phòng"
          prop="orderCost"
        >
          {{ '₫' + form1.orderCost.toFixed(2) }}
        </el-form-item>
        <el-form-item>
          <el-button :loading="loading" type="primary" @click="onSubmit">Gửi</el-button>
          <el-button @click="onCancel">Hủy</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </template>
  

<script>
  import { addOrder } from "../../api/order";
  import {getAllOrderType } from "../../api/orderType";
  import { getAllRoomType } from "../../api/roomType";

  export default {
        name: "add",
      data() {
        return {
          form1: {
            orderTypeId:null,
            orderType: null,
            roomTypeId: null,
            userId:0,
            name: '',
            phone:'',
            orderDate: null,
            orderDays: 1,
            orderCost: 0
          },
          orderDateRange:null,
          loading: false,
          roomTypeList: [],
          orderTypeList: [],
            expireTimeOption:{
              disabledDate: time =>{
                  return time.getTime() < Date.now()-24*60*60*1000;
              },
            },
        }
      },
      created: function() {
        this.fetchData()
      },
      methods: {
        fetchData() {
          getAllOrderType().then(res => {
            this.orderTypeList = res.data;
          })
          getAllRoomType().then(res => {
            this.roomTypeList = res.data;
          })
        },
        idToType(rtId,otId) {
          if (rtId == null){
            this.orderTypeList.forEach(t => {
              if (otId === t.typeId) {
                this.form1.orderType = t.type
              }
            })
          } else {
            this.roomTypeList.forEach(type => {
              if (rtId === type.typeId) {
                this.form1.roomType = type.roomType
                this.form1.orderCost = type.price*this.form1.orderDays
              }
            })
          }

        },
        calcDays(){
          this.form1.orderDate = this.orderDateRange[0]
            var days = this.orderDateRange[1].getTime() - this.orderDateRange[0].getTime();
          this.form1.orderDays = days / (24*60*60*1000);
        },
        onSubmit() {
          this.$refs.form1.validate((valid) => {
            if (valid) {
              this.loading = true
              addOrder(this.form1).then(response => {
                  const res = response;
                if(res.code === 1000){
                  this.$message({
                    message: 'Gửi thành công!',
                    type: 'success'
                  })
                  this.loading = false
                  setTimeout(this.onCancel(), 20000)
                }else {
                  this.showError()
                  this.loading = false
                }
              })
            } else {
              this.loading = false
              return false
            }
          })
        },
        showError() {
          this.$message({
            message: 'Gửi thất bại!',
            type: 'error'
          })
        },
        onCancel() {
          this.$router.back()
        }
      }
    }
</script>

<style scoped>

</style>
