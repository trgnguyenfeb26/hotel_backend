<template>
  <el-card class="app-container" shadow="always">
    <div slot="header">
      <span>Thêm thông tin Khách hàng</span>
    </div>
    <el-form ref="form1" :model="form1" :rules="rules2" label-width="120px">
      <el-form-item
        prop="username"
        label="Tên đăng nhập">
        <el-input v-model="form1.username" placeholder="Vui lòng nhập tên đăng nhập, không thể thay đổi"/>
      </el-form-item>
      <el-form-item
        label="Mật khẩu"
        prop="password"
      >
        <el-input v-model="form1.password" type="password" placeholder="Vui lòng nhập mật khẩu"/>
      </el-form-item>
      <el-form-item
        label="Xác nhận mật khẩu"
        prop="pass"
      >
        <el-input v-model="form1.pass" type="password" placeholder="Vui lòng nhập lại mật khẩu"/>
      </el-form-item>
      <el-form-item
        label="Họ tên"
        prop="name"
      >
        <el-input v-model.number="form1.name" placeholder="Vui lòng nhập họ tên thật"/>
      </el-form-item>
      <el-form-item
        label="Số điện thoại"
        prop="phone"
      >
        <el-input v-model.number="form1.phone" type="number" placeholder="Vui lòng nhập số điện thoại" style="width: 50%"/>
        <el-button type="primary" disabled="">Gửi mã xác nhận</el-button>
      </el-form-item>
      <el-form-item
        prop="gender"
        label="Giới tính">
        <el-radio v-model="form1.gender" label="Nam">Nam</el-radio>
        <el-radio v-model="form1.gender" label="Nữ">Nữ</el-radio>
      </el-form-item>
      <el-form-item
        label="Số CMND"
        prop="idcard"
      >
        <el-input v-model="form1.idcard" placeholder="Vui lòng nhập số CMND"/>
      </el-form-item>
      <el-form-item
        label="Địa chỉ email"
        prop="email"
      >
        <el-input v-model="form1.email" />
      </el-form-item>
      <el-form-item
        label="Địa chỉ"
        prop="address"
      >
        <el-input v-model="form1.address" placeholder="Vui lòng nhập địa chỉ"/>
      </el-form-item>
      <el-form-item>
        <el-button :loading="loading" type="primary" @click="onSubmit()">Submit</el-button>
        <el-button @click="onCancel">Cancel</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import { addUser } from "../../api/user";
  import ElCard from "element-ui/packages/card/src/main";

  export default {
    components: {ElCard},
    data() {
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('Vui lòng nhập lại mật khẩu'))
        } else if (value !== this.form1.password) {
          callback(new Error('Mật khẩu không khớp!'))
        } else {
          callback()
        }
      }
      return {
        form1: {
          username: null,
          password: null,
          pass: null,
          name: null,
          phone: null,
          email: null,
          address: null,
          gender: null,
          idcard: null,
        },
        loading: false,
        rules2: {
          username: [
            { required: true, message: 'Không được để trống' }
          ],
          password: [
            { required: true, message: 'Không được để trống' }
          ],
          pass: [
            { required: true, validator: validatePass, trigger: 'blur' }
          ],
          name: [
            { required: true, message: 'Không được để trống' }
          ],
          phone: [
            { required: true, message: 'Không được để trống' }
          ],
          gender: [
            { required: true, message: 'Không được để trống' }
          ]
        }
      }
    },
    methods: {
      onSubmit() {
        this.$refs.form1.validate((valid) => {
          if (valid) {
            this.loading = true
            addUser(this.form1).then(response => {
                const res = response;
              if (res.code===1000) {
                this.$message({
                  message: 'Submit thành công!',
                  type: 'success'
                })
                this.loading = false
                setTimeout(this.onCancel(), 20000)
              } else {
                this.showError()
                this.loading = false
              }
            }).catch(error => {
              this.loading = false
              this.showError()
              console.log(error)
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
