/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

var requestData = {
    nickName: "", authenticationId: "", levelId: "",
    sortName: "createDate", sortType: "desc", page: 1, pageSize: 20
};

$(function () {
    page.init();

    $.ajax({
        url: '../../level/findAll',
        type: 'GET',
        success: function (result) {
            for (var i = 0; i < result.length; i++) {

            }
        }, error: function () {
            layer.msg("服务器繁忙");
        }
    })
});

var page = {};

page.init = function () {
    page.list();
};

page.search = function () {
    requestData.nickName = $("#nickName").val();
    requestData.authenticationId = $("#authentication option:selected").val();
    requestData.levelId = $("#level option:selected").val();
    requestData.sortName = $("#sortName option:selected").val();
    requestData.sortType = $("input[name='sortType']:checked").val();
    page.list();
};

page.list = function () {
    $.get("list", requestData, function (data) {
        if (data) {
            var html = "";
            var list = data.list;
            for (var i = 0; i < list.length; i++) {
                var row = data.list[i];
                html += '<tr><td>';
                if (row.imgURL == "" || null == row.imgURL) {
                    html += '<img src="../../img/user.png" style="width: 30px;height: 30px;"/>';
                } else {
                    html += '<img src="' + row.imgURL + '" style="width: 30px;height: 30px;"/>';
                }
                html += '</td><td>' + row.nickName + '</td><td>' + row.experience + '</td><td>'
                    + moment(row.createDate).format("YYYY-MM-DD, hh:mm:ss a")
                    + '</td><td>' + row.articleAmount + '</td><td>';
                var power = row.power;
                if (power.indexOf("1") >= 0 && power.indexOf("0") < 0) {
                    html += '启用';
                } else if (power.indexOf("1") >= 0 && power.indexOf("0") >= 0) {
                    html += '部分启用';
                } else if (power.indexOf("1") < 0 && power.indexOf("0") >= 0) {
                    html += '禁用';
                }
                html += '</td><td>' + row.authentication.name + '</td><td>' + row.level.name;
                html += '</td><td><button class="btn btn-success btn-sm" th:attr="levelId=${level.id}"'
                    + 'onclick="page.edit(' + row.id + ')">'
                    + '<i class="fa fa-paste"></i>&nbsp;编辑'
                    + '</button>&nbsp;&nbsp;';
                html += '<button class="btn btn-success btn-sm"'
                    + 'onclick="page.open(this)">'
                    + '<i class="fa fa-toggle-up"></i>&nbsp;升级'
                    + '</button></td></tr>';
            }
            $("#userList").html(html);

            $("#total").text(data.total);
            $("#page").text(data.page);
            $("#pageCount").text(data.pageCount);
            $("#pageSize").text(data.pageSize);
            page.paging(data.pageSize);
        }
    });
};

page.paging = function (totalPage) {
    var pageinate = new hot.paging(".pagination", requestData.page, totalPage, 7);
    pageinate.init(function (pageNo) {
        requestData.page = pageNo;
        page.list();
    });
};

page.edit = function (id) {
    window.location.href = 'edit?id=' + id;
};

function format(obj) {
    var d = new Date(obj);    //根据时间戳生成的时间对象
    var date = (d.getFullYear()) + "-" +
        (d.getMonth() + 1) + "-" +
        (d.getDate()) + " " +
        (d.getHours()) + ":" +
        (d.getMinutes()) + ":" +
        (d.getSeconds());
    return date;
}