<template>
  <el-aside>
    <el-radio-group v-model="isCollapse" style="margin-bottom: 20px;">
      <el-radio-button :label="false">Mở rộng</el-radio-button>
      <el-radio-button :label="true">Thu gọn</el-radio-button>
    </el-radio-group>
    <el-menu default-active="1-4-1" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" :collapse="isCollapse">
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-location"></i>
          <span slot="title">Dẫn hướng một</span>
        </template>
        <el-menu-item-group>
          <span slot="title">Nhóm một</span>
          <el-menu-item index="1-1">Lựa chọn 1</el-menu-item>
          <el-menu-item index="1-2">Lựa chọn 2</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group title="Nhóm 2">
          <el-menu-item index="1-3">Lựa chọn 3</el-menu-item>
        </el-menu-item-group>
        <el-submenu index="1-4">
          <span slot="title">Lựa chọn 4</span>
          <el-menu-item index="1-4-1">Lựa chọn 1</el-menu-item>
        </el-submenu>
      </el-submenu>
      <el-menu-item index="2">
        <i class="el-icon-menu"></i>
        <span slot="title">Dẫn hướng hai</span>
      </el-menu-item>
      <el-menu-item index="3" disabled>
        <i class="el-icon-document"></i>
        <span slot="title">Dẫn hướng ba</span>
      </el-menu-item>
      <el-menu-item index="4">
        <i class="el-icon-setting"></i>
        <span slot="title">Dẫn hướng bốn</span>
      </el-menu-item>
    </el-menu>
  </el-aside>
</template>

<script>
export default {
  name: "navAside",
  data() {
    return {
      isCollapse: true
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    }
  }
}
</script>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
.el-aside {
  color: #333;
}
</style>
