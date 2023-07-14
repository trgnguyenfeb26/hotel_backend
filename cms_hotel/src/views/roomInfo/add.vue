<template>
  <el-card class="app-container"  shadow="always">
    <div slot="header">
      <span>Thêm phòng</span>
    </div>
    <el-form ref="form1" :model="form1" >
      <el-form-item
        :rules="[
          { required: true, message: 'Không được bỏ trống'}
        ]"
        label="Số phòng"
        prop="roomNumber"
      >
        <el-input v-model.number="form1.roomNumber" placeholder="Nhập số phòng"/>
      </el-form-item>
      <el-form-item
        :rules="[
          { required: true, message: 'Không được bỏ trống'}
        ]"
        label="Loại phòng">
        <el-select v-model="form1.typeId" :change="idToType(form1.typeId)" placeholder="Chọn loại phòng">
          <el-option v-for="t in typeList" :label="t.roomType" :value="t.typeId" :key="t.typeId">
            {{ t.typeId + ' ' + t.roomType + '(' + t.price + ' đồng)' }}
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item
        :rules="[
          { required: true, message: 'Không được bỏ trống'},
          { type: 'number', message: 'Phải là giá trị số'}
        ]"
        label="Giá"
        prop="roomPrice"
      >
        <el-input v-model.number="form1.roomPrice" type="number"/>
      </el-form-item>
      <el-form-item
        :rules="[
          { required: true, message: 'Không được bỏ trống'},
          { type: 'number', message: 'Phải là giá trị số'}
        ]"
        label="Giảm giá"
        prop="roomDiscount"
      >
        <el-input v-model.number="form1.roomDiscount" type="number"/>
      </el-form-item>
      <el-form-item
        :rules="[
          { required: true, message: 'Không được bỏ trống'},
        ]"
        label="Trạng thái phòng"
      >
        <el-radio-group v-model="form1.roomStatus">
          <el-radio :label="statusList.unavailable">Không khả dụng</el-radio>
          <el-radio :label="statusList.available">Trống</el-radio>
          <el-radio :label="statusList.occupied">Đã đặt</el-radio>
          <el-radio :label="statusList.inUse">Đã sử dụng</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="Mô tả chi tiết">
        <el-input v-model="form1.remark" type="textarea"/>
      </el-form-item>
      <el-form-item>
        <el-button :loading="loading" type="primary" @click="onSubmit()">Gửi</el-button>
        <el-button @click="onCancel">Hủy</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import { getAllRoomType } from '@/api/roomType'
import { addRoom } from '@/api/roomInfo'
import ElCard from "element-ui/packages/card/src/main";
export default {
  components: {ElCard},
  data() {
    return {
     form1: {
        roomId: null,
        roomNumber: null,
        roomType: null,
        typeId: null,
        roomPrice: null,
        roomDiscount: null,
        roomStatus: null,
        remark: null
      },
      loading: false,
      typeList: null,
      statusList: {
        unavailable: 0,
        available: 1,
        occupied: 2,
        inUse: 3
      }
    }
  },
  created: function() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      getAllRoomType().then(res => {
        this.typeList = res.data;
      })
    },
    idToType(val) {
      if (val == null) return
      this.typeList.forEach(type => {
        if (val === type.typeId) {
          this.form1.roomType = type.roomType;
            this.form1.roomPrice = type.price;
          this.form1.roomDiscount = type.discount;
        }
      })
    },
    onSubmit() {
      this.$refs.form1.validate((valid) => {
        if (valid) {
          this.loading = true
          addRoom(this.form1).then(response => {
              const res = response;
            if (res.code === 1000) {
              this.$message({
                message: 'Gửi thành công!',
                type: 'success'
              })
              this.loading = false;
              setTimeout(this.onCancel(), 20000)
            } else {
              this.showError(res.message);
              this.loading = false;
            }
          })
        } else {
          this.loading = false
          return false
        }
      })
    },
    showError(msg) {
      this.$message({
        message: msg,
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
