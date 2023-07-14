const vFilter = {
  hasWindow: function (val) {
    if (val >= 1) return 'Có cửa sổ';
    else return 'Không có';
  },
  formatDate: function (value) {
    var d = new Date(value);
    var year = d.getFullYear();
    var month = d.getMonth() + 1;
    var day = d.getDate() < 10 ? '0' + d.getDate() : '' + d.getDate();
    return year + '-' + month + '-' + day;
  },
  formatDay: function (value) {
    const wd = ['Chủ nhật', 'Thứ hai', 'Thứ ba', 'Thứ tư', 'Thứ năm', 'Thứ sáu', 'Thứ bảy'];
    var d = new Date(value);
    var year = d.getFullYear();
    var month = d.getMonth() + 1;
    var day = d.getDate() < 10 ? '0' + d.getDate() : '' + d.getDate();
    var hour = d.getHours();
    var minutes = d.getMinutes();
    var seconds = d.getSeconds();
    var dow = d.getDay();
    return wd[dow] + ', ' + day + '/' + month + '/' + year + ' ' + hour + ':' + minutes + ':' + seconds;
  },
  addDate: function (date, days) {
    if (days == undefined || days == '') {
      days = 1;
    }
    var d = new Date(date);
    d.setDate(d.getDate() + days);
    var year = d.getFullYear();
    var month = d.getMonth() + 1;
    var day = d.getDate() < 10 ? '0' + d.getDate() : '' + d.getDate();
    return year + '-' + month + '-' + day;
  },
  getOrderStatus: function (val) {
    var status = '';
    switch (val) {
      case -1:
        status = 'Đã hủy';
        break;
      case 0:
        status = 'Chưa thanh toán';
        break;
      case 1:
        status = 'Đã thanh toán';
        break;
      case 2:
        status = 'Đã nhận phòng';
        break;
      case -2:
        status = 'Quá hạn';
        break;
      case -3:
        status = 'Đã xóa';
        break;
    }
    return status;
  },
  getStatusColor(val) {
    var status = '';
    switch (val) {
      case -2:
        status = 'info';
        break;
      case -1:
        status = '';
        break;
      case 0:
        status = 'error';
        break;
      case 1:
        status = 'success';
        break;
      case 2:
        status = 'primary';
        break;
      case -3:
        status = 'info';
        break;
    }
    return status;
  }
}

export default vFilter;