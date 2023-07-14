<template>
  <el-card class="app-container" shadow="always">
    <div slot="header">
      <span>Chỉnh sửa loại phòng</span>
    </div>
    <el-form ref="form1" :model="form1" label-width="120px">
      <el-form-item
        :rules="[
          { required: true, message: 'Không được để trống'}
        ]"
        prop="roomType"
        label="Tên loại phòng">
        <el-input v-model="form1.roomType" placeholder="Nhập tên loại phòng"/>
      </el-form-item>
      <el-form-item
        :rules="[
          { required: true, message: 'Không được để trống'},
          { type: 'number', message: 'Phải là giá trị số'}
        ]"
        label="Giá đặt phòng"
        prop="price"
      >
        <el-input v-model.number="form1.price" type="number" placeholder="Đơn vị: VNĐ"/>
      </el-form-item>
      <el-form-item
        :rules="[
          { required: true, message: 'Không được để trống'},
          { type: 'number', message: 'Phải là giá trị số'}
        ]"
        label="Giảm giá đặt phòng"
        prop="discount"
      >
        <el-input v-model.number="form1.discount" type="number" placeholder="0-100%"/>
      </el-form-item>
      <el-form-item
        :rules="[
          { required: true, message: 'Không được để trống'},
          { type: 'number', message: 'Phải là giá trị số'}
        ]"
        label="Diện tích phòng"
        prop="area"
      >
        <el-input v-model.number="form1.area" type="number" placeholder="Đơn vị: m²"/>
      </el-form-item>
      <el-form-item
        :rules="[
          { required: true, message: 'Không được để trống'},
        ]"
        label="Có cửa sổ không">
        <el-radio v-model="form1.windows" :label="windowSwitch.has">Có cửa sổ</el-radio>
        <el-radio v-model="form1.windows" :label="windowSwitch.none">Không có cửa sổ</el-radio>
      </el-form-item>
      <el-form-item
        :rules="[
          { required: true, message: 'Không được để trống'},
          { type: 'number', message: 'Phải là giá trị số'}
        ]"
        label="Số lượng giường"
        prop="bedNum"
      >
        <el-input-number v-model="form1.bedNum" :min="1" :max="10" label="Số lượng giường"/>
      </el-form-item>
      <el-form-item
        :rules="[
          { required: true, message: 'Không được để trống'},
        ]"
        label="Kích thước giường"
        prop="bedSize"
      >
        <el-input v-model.number="form1.bedSize" placeholder="Ví dụ: 1.2m*1.5m"/>
      </el-form-item>
      <el-form-item label="Mô tả chi tiết">
        <el-input v-model="form1.remark" type="textarea"/>
      </el-form-item>
      <el-form-item>
        <el-button :loading="loading" type="primary" @click="onSubmit()">Submit</el-button>
        <el-button @click="onCancel">Cancel</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
import { editRoomType, getRoomTypeById } from '@/api/roomType'

export default {
  data() {
    return {
      form1: {
        typeId: null,
        roomType: null,
        area: null,
        windows: null,
        bedNum: null,
        bedSize: null,
        price: null,
        discount: null,
        remark: null
      },
      loading: false,
      typeId: null,
      windowSwitch: {
        has: 1,
        none: 0
      }
    }
  },
  created: function() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.typeId = this.$route.params.id
      if (this.typeId == null) {
        this.onCancel()
      }
      getRoomTypeById(this.typeId).then(res => {
        this.form1 = res.data;
      })
    },
    onSubmit() {
      this.$refs.form1.validate((valid) => {
        if (valid) {
          this.loading = true
          editRoomType(this.form1).then(response => {
              const res = response;
            if (res.code === 1000) {
              this.$message({
                message: 'Submit thành công!',
                type: 'success'
              })
              this.loading = false;
              setTimeout(this.onCancel(), 20000)
            } else {
              this.showError();
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
        message: 'Submit thất bại!',
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
  .line{
    text-align: center;
  }
</style>
