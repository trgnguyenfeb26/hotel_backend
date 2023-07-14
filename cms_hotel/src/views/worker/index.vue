<template>
  <el-card class="box-card" shadow="always">
    <div slot="header">
      <span>Operator Management</span>
      <el-button type="primary" style="float: right; padding: 6px 12px" icon="el-icon-search">Search</el-button>
    </div>
    <el-table
      ref="multipleTable"
      :data="list"
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="handleSelectionChange">
      <el-table-column
        type="selection"
        width="55"/>
      <el-table-column
        prop="workerId"
        label="number"/>
      <el-table-column
        prop="name"
        label="name"/>
      <el-table-column
        prop="username"
        label="username"/>
      <el-table-column
        prop="phone"
        label="mobile phone number"/>
      <el-table-column
        prop="email"
        label="Email"/>
      <el-table-column
        prop="address"
        label="address"/>
      <el-table-column
        label="modification time|creation time">
        <template slot-scope="scope">
          <i class="el-icon-time"/>
          <span style="margin-left: 10px">{{ scope. row. updateTime | formatDate }}</span>
          <br>
          <i class="el-icon-time"/>
          <span style="margin-left: 10px">{{ scope.row.createTime | formatDate }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Operation">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
          <el-popover
            v-model="scope.row.visible2"
            placement="top"
            width="160">
            <p>Are you sure you want to delete? </p>
            <div style="text-align: right; margin: 0">
              <el-button size="mini" round @click="scope.row.visible2 = false">Cancel</el-button>
              <el-button type="danger" size="mini" round @click="handleDel(scope.row)">OK</el-button>
            </div>
            <el-button slot="reference" :loading="scope.row.loading" size="mini" type="danger" @click="scope.row.visible2 = true">Delete</el-button>
          </el-popover>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 14px;">
      <div class="bottom">
        <el-button type="primary" @click="navigateTo('add')">Add operator</el-button>
        <el-button type="danger">Batch delete</el-button>
      </div>
    </div>
  </el-card>
</template>

<script>
import { getAll, del } from '@/api/worker'

export default {
  data() {
    return {
      visible2: false,
      multipleSelection: [],
      list: null,
      listLoading: true
    }
  },
  created: function() {
    this. fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      getAll().then(response => {
        this.list = response.data;
        this. listLoading = false;
      })
    },
    navigateTo(val) {
      this.$router.push({ path: '/worker/' + val })
    },
    handleEdit(index, row) {
      this.$router.push({
        path: '/worker',
        name: 'EditWorker',
        params: {
          id: row.workerId
        }
      })
    },
    handleDel(row) {
      row.visible2 = false
      row.loading = true
      del(row.workerId).then(response => {
          const res =response;
        if (res.code===1000) {
          this.list = null
          this.$message({
            message: 'Delete successfully! ',
            type: 'success'
          })
            this.fetchData()
        } else {
          this.$message({
            message: 'Delete failed! ',
            type: 'error'
          })
        }
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
