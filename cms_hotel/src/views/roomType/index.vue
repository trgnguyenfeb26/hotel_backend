<template>
  <el-card class="box-card" shadow="always">
    <div slot="header">
      <span>Quản lý loại phòng</span>
      <el-button type="primary" style="float: right; padding: 6px 12px" icon="el-icon-search">Tìm kiếm</el-button>
    </div>
    <el-table
      ref="multipleTable"
      :loading="listLoading"
      :data="list"
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="handleSelectionChange">
      <el-table-column
        type="selection"
        width="55"/>
      <el-table-column
        prop="typeId"
        label="Mã"/>
      <el-table-column
        label="Loại phòng">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>Mô tả chi tiết: </p>
            <p>{{ scope.row.remark }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag>{{ scope.row.roomType }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        prop="price"
        label="Giá đặt phòng"/>
      <el-table-column
        prop="discount"
        label="Giảm giá đặt phòng"/>
      <el-table-column
        prop="area"
        label="Diện tích phòng">
        <template slot-scope="scope">
          <span>{{ scope.row.area }} m²</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="windows"
        label="Có cửa sổ">
        <template slot-scope="scope">
          <span>{{ scope.row.windows | hasWindow }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="Số giường">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top-end">
            <p>Kích thước giường: {{ scope.row.bedSize }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag>{{ scope.row.bedNum }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        label="Thời gian sửa đổi|Thời gian tạo">
        <template slot-scope="scope">
          <i class="el-icon-time"/>
          <span style="margin-left: 10px">{{ scope.row.updateTime | formatDate }}</span>
          <br>
          <i class="el-icon-time"/>
          <span style="margin-left: 10px">{{ scope.row.createTime | formatDate }}</span>
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
              <el-button type="danger" size="mini" round @click="handleDel(scope.row)">Xóa</el-button>
            </div>
            <el-button slot="reference" :loading="scope.row.loading" size="mini" type="danger" @click="scope.row.visible2 = true">Xóa</el-button>
          </el-popover>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 14px;">
      <div class="bottom">
        <el-button type="primary" @click="navigateTo('add')">Thêm loại phòng</el-button>
        <el-button type="danger" @click="massDeletion">Xóa hàng loạt</el-button>
      </div>
    </div>
  </el-card>
</template>

<script>
import { getAllRoomType, delRoomType } from '@/api/roomType'

export default {
  filters: {
    hasWindow: function(value) {
      if (value > 0) return 'Có'
      else return 'Không'
    }
  },
  data() {
    return {
      visible2: false,
      multipleSelection: [],
      listLoading: true,
      list: null,
      loading: false
    }
  },
  created: function() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      getAllRoomType().then(response => {
        this.list = response.data;
        this.list.reverse();
        this.listLoading = false;
      })
    },
    navigateTo(val) {
      this.$router.push({ path: '/roomType/' + val })
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
    handleEdit(index, row) {
      this.$router.push({
        path: '/roomType',
        name: 'EditType',
        params: {
          id: row.typeId
        }
      })
    },
    handleDel(row) {
      row.visible2 = false;
      row.loading = true;
      delRoomType(row.typeId).then(response => {
          const res =response;
        if (res.code === 1000) {
          this.$message({
            message: 'Xóa thành công!',
            type: 'success'
          });
            this.list.push();
            this.fetchData()
        } else {
          this.$message({
            message: 'Xóa thất bại!',
            type: 'error'
          })
        }
      })

      row.loading = false;

    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    massDeletion() {
      this.multipleSelection.forEach(function(value, index) {
        console.log(value.typeId)
      })
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
