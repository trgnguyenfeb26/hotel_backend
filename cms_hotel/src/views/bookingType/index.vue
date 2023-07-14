<template>
  <el-card class="box-card" shadow="always">
    <div slot="header">
      <span>Quản lý khách sạn</span>
      <el-button type="primary" style="float: right; padding: 6px 12px" icon="el-icon-search">Tìm kiếm</el-button>
    </div>
    <el-table
      ref="multipleTable"
      :data="list"
      :loading="listLoading"
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="handleSelectionChange">
      <el-table-column
        type="selection"
        width="55"/>
      <el-table-column
        prop="typeId"
        label="Mã số"/>
      <el-table-column
        prop="type"
        label="Tên khách sạn"/>
      <el-table-column
        prop="remark"
        label="Địa chỉ"/>
      <el-table-column
        label="Thời gian Tạo|Sửa đổi">
        <template slot-scope="scope">
          <i class="el-icon-time"/>
          <span style="margin-left: 10px">{{ scope.row.createTime | formatDate }}</span>
          <br>
          <i class="el-icon-time"/>
          <span style="margin-left: 10px">{{ scope.row.updateTime | formatDate }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Thao tác">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">Chỉnh sửa</el-button>
          <el-popover
            v-model="scope.row.visible2"
            placement="top"
            width="160">
            <p>Bạn có chắc muốn xóa không?</p>
            <div style="text-align: right; margin: 0">
              <el-button size="mini" round @click="scope.row.visible2 = false">Hủy</el-button>
              <el-button type="danger" size="mini" round @click="handleDel(scope.row)">Xác nhận</el-button>
            </div>
            <el-button slot="reference" :loading="scope.row.loading" size="mini" type="danger" @click="scope.row.visible2 = true">Xóa</el-button>
          </el-popover>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 14px;">
      <div class="bottom">
        <el-button type="primary" @click="handleAdd()">Thêm khách sạn</el-button>
        <el-dialog :visible.sync="dialogFormVisible" title="Thêm khách sạn">
          <el-form :model="form">
            <el-form-item :label-width="formLabelWidth" label="Khách sạn">
              <el-input v-model="form.type" />
            </el-form-item>
            <el-form-item :label-width="formLabelWidth" label="Địa chỉ">
              <el-input v-model="form.remark" />
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">Hủy</el-button>
            <el-button v-if="isEdit" :loading="addBtnLoading" type="primary" @click="edit()">Xác nhận</el-button>
            <el-button v-if="!isEdit" :loading="addBtnLoading" type="primary" @click="add()">Thêm</el-button>
          </div>
        </el-dialog>
        <el-button type="danger">Xóa hàng loạt</el-button>
      </div>
    </div>
  </el-card>
</template>

<script>
import { getAllOrderType, delOrderType, addOrderType, getOrderTypeById, updateOrderType } from "../../api/orderType";

export default {
  data() {
    return {
      visible2: false,
      isEdit: false,
      list: null,
      listLoading: false,
      multipleSelection: [],
      form: {
        type: null,
        remark: null
      },
      formLabelWidth: '120px',
      addBtnLoading: false,
      dialogFormVisible: false
    }
  },
  created: function () {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.list = null
      this.listLoading = true
      getAllOrderType().then(res => {
        this.list = res.data;
        this.listLoading = false
      })
    },
    add() {
      this.addBtnLoading = true
      addOrderType(this.form).then(response => {
        const res = response;
        if (res.code === 1000) {
          this.$message({
            message: 'Thêm thành công!',
            type: 'success'
          })
          this.fetchData()
        } else {
          this.$message({
            message: res.message,
            type: 'error'
          })
        }
      }).catch(err => {
        console.log(err)
        this.$message({
          message: 'Thêm thất bại!',
          type: 'error'
        })
        this.addBtnLoading = false
        this.dialogFormVisible = false
      })
      this.addBtnLoading = false
      this.dialogFormVisible = false
    },
    edit() {
      this.addBtnLoading = true
      updateOrderType(this.form).then(response => {
        const res = response;
        if (res.code === 1000) {
          this.$message({
            message: 'Chỉnh sửa thành công!',
            type: 'success'
          })
          this.fetchData()
        } else {
          this.$message({
            message: res.message,
            type: 'error'
          })
        }
      }).catch(err => {
        console.log(err)
        this.$message({
          message: 'Chỉnh sửa thất bại!',
          type: 'error'
        })
        this.addBtnLoading = false
        this.dialogFormVisible = false
      })
      this.addBtnLoading = false
      this.dialogFormVisible = false
    },
    handleAdd() {
      this.form.type = null
      this.form.remark = null
      this.isEdit = false
      this.dialogFormVisible = true
    },
    handleEdit(index, row) {
      this.isEdit = true
      this.dialogFormVisible = true
      getOrderTypeById(row.typeId).then(res => {
        this.form = res.data;
      })
    },
    handleDel(row) {
      row.visible2 = false
      row.loading = true
      delOrderType(row.typeId).then(response => {
        const res = response;
        if (res.code === 1000) {
          this.$message({
            message: 'Xóa thành công!',
            type: 'success'
          })
          this.fetchData()
        } else {
          this.$message({
            message: res.message,
            type: 'error'
          })
        }
      })
      row.loading = false
      this.fetchData()
    },
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row)
        })
      } else {
        this.$refs.multipleTable.clearSelection()
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    }
  }
}
</script>

<style>
  .red {
    color: red;
  }
  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }
</style>
