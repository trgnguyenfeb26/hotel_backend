<template>
  <el-card class="box-card" shadow="always">
    <div slot="header">
      <span>Quản lý thông tin phòng</span>
      <el-button type="primary" style="float: right; padding: 6px 12px" icon="el-icon-search">Tìm kiếm</el-button>
    </div>
    <el-table
      ref="multipleTable"
      :data="list"
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="handleSelectionChange">
      <el-table-column
        type="selection"/>
      <el-table-column
        prop="roomId"
        label="Mã"
        width="50"/>
      <el-table-column
        label="Số phòng">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>Mô tả chi tiết: </p>
            <p>{{ scope.row.remark }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag>{{ scope.row.roomNumber }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        label="Loại phòng">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <!--<p>Mô tả chi tiết: </p>-->
            <!--<p>{{ scope.row.remark }}</p>-->
            <div slot="reference" class="name-wrapper">
              <el-tag>{{ scope.row.roomType }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        prop="roomPrice"
        label="Giá"/>
      <el-table-column
        label="Giảm giá">
        <template slot-scope="scope">
          <el-tag type="primary">{{ scope.row.roomDiscount + '%' }}</el-tag>
        </template>
      </el-table-column>
      <!--<el-table-column-->
      <!--prop="desc"-->
      <!--label="Mô tả chi tiết"/>-->
      <el-table-column
        prop="roomStatus"
        label="Trạng thái">
        <template slot-scope="scope">
          <!--<el-tag :type="showStatus(scope.row.roomStatus)">{{ scope.row.roomStatus }}</el-tag>-->
          <el-button :type="showStatus(scope.row.roomStatus)" size="mini">{{ scope.row.roomStatus | formatStatus }}</el-button>
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
            <p>Bạn có chắc chắn muốn xóa không?</p>
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
        <el-button type="primary" @click="navigateTo('add')">Thêm thông tin phòng</el-button>
        <el-button type="danger">Xóa hàng loạt</el-button>
      </div>
    </div>
  </el-card>
</template>

<script>

import { getAllRoom, delRoom} from "../../api/roomInfo";

export default {
  filters: {
    formatStatus: function(value) {
      switch (value) {
        case 0: return 'Không khả dụng'
        case 1: return 'Trống'
        case 2: return 'Đã đặt'
        case 3: return 'Đã ở'
      }
    }
  },
  data() {
    return {
      visible2: false,
      multipleSelection: [],
      list: null,
      listLoading: true
    }
  },
  created: function() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      getAllRoom().then(response => {
        this.list = response.data;
        this.listLoading = false;
      })
    },
    navigateTo(val) {
      this.$router.push({ path: '/roomInfo/' + val })
    },
    handleEdit(index, row) {
      this.$router.push({
        path: '/roomInfo',
        name: 'EditRoom',
        params: {
          id: row.roomId
        }
      })
    },
    handleDel(row) {
      row.visible2 = false
      row.loading = true
      delRoom(row.roomId).then(response => {
          const res = response;
        if (res.code === 1000) {
          this.$message({
            message: 'Xóa thành công!',
            type: 'success'
          })
          this.list.push();
          row.loading = false
          this.fetchData()
        } else {
          this.$message({
            message: 'Xóa thất bại!',
            type: 'error'
          })
        }
      }).catch(error => {
        row.loading = false
        console.log(error)
      })
      row.loading = false
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
    },
    showStatus(val) {
      switch (val) {
        case 0: return 'info'
        case 1: return 'success'
        case 2: return 'warning'
        case 3: return 'danger'
      }
    }
  }
}
</script>

<style>
  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }
</style>
