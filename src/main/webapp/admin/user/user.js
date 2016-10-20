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
    $("input[name='sortType']").change(function () {
        page.search();
    });
    $("#sortName").change(function () {
        page.search();
    });

    $("#saveLevel").click(function () {
        page.updateLevel();
    });

    $("#saveAuthentication").click(function () {
        page.updateAuthentication();
    });

    $("#savePower").click(function () {
        page.updatePower();
    });

    if (selectType == 'single') {
        $("#checkAll").click(function () {
            if ($(this).is(":checked")) {
                layer.msg("只支持单选");
                $(this).prop("checked", false);
            }
        });

    } else {
        $("#checkAll").change(function () {
            if ($("#checkAll").is(":checked")) {
                $("input[name='checkUser']").prop("checked", true);
            } else {
                $("input[name='checkUser']").prop("checked", false);
            }
        });
    }
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
                html += '<div class="checkbox checkbox-primary m-b-none m-t-none">' +
                    '<input type="checkbox" name="checkUser" value="' + row.id + '"' +
                    'id="check' + row.id + '"/>' +
                    '<label for="check' + row.id + '"></label>' +
                    '</div></td><td>';
                if (row.imgURL == "" || null == row.imgURL) {
                    html += '<img src="../../img/user.png" style="width: 30px;height: 30px;"/>';
                } else {
                    html += '<img src="' + row.imgURL + '" style="width: 30px;height: 30px;"/>';
                }
                html += '</td><td>' + row.nickName + '</td><td>' + row.experience + '</td><td>'
                    + moment(row.createDate).format("YYYY-MM-DD, hh:mm:ss a")
                    + '</td><td>';
                var power = row.power;
                if (power.indexOf("1") >= 0 && power.indexOf("0") < 0) {
                    html += '拥有';
                } else if (power.indexOf("1") >= 0 && power.indexOf("0") >= 0) {
                    html += '部分拥有';
                } else if (power.indexOf("1") < 0 && power.indexOf("0") >= 0) {
                    html += '禁用';
                }
                html += '</td><td>';
                if (row.authenticationType == 0) {
                    html += '普通用户';
                } else if (row.authenticationType == 1) {
                    html += '组长';
                } else if (row.authenticationType == 2) {
                    html += '管理员';
                } else {
                    html += '未知';
                }

                html += '</td><td>' + row.level.name;
                html += '</td><td><button class="btn btn-success btn-sm" th:attr="levelId=${level.id}"'
                    + 'onclick="page.edit(' + row.id + ')">'
                    + '<i class="fa fa-paste"></i>&nbsp;查看'
                    + '</button>&nbsp;&nbsp;';
                html += '<button class="btn btn-success btn-sm"'
                    + 'onclick="page.openLevel(' + row.id + ',' + row.level.id + ')">'
                    + '<i class="fa fa-toggle-up"></i>&nbsp;等级'
                    + '</button>&nbsp;&nbsp;';
                html += '<button class="btn btn-success btn-sm"'
                    + 'onclick="page.openPower(' + row.id + ',' + row.power + ')">'
                    + '<i class="fa fa-user-secret"></i>&nbsp;权限'
                    + '</button>&nbsp;&nbsp;';
                html += '<button class="btn btn-success btn-sm"'
                    + 'onclick="page.openAuthentication(' + row.id + ',' + row.authenticationType + ')">'
                    + '<i class="fa fa-user"></i>&nbsp;身份'
                    + '</button></td></tr>';
            }
            $("#userList").html(html);

            $("#total").text(data.total);
            $("#page").text(data.page);
            $("#pageCount").text(data.pageCount);
            $("#pageSize").text(data.pageSize);
            page.paging(data.pageSize);
            if (selectType == 'single') {
                $("input[name='checkUser']").click(function () {
                    if ($(this).is(":checked")) {
                        $("input[name='checkUser']").prop("checked", false);
                        $(this).prop("checked", true);
                    }
                });
            }
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
    window.location.href = 'edit?userId=' + id + "&selectType=" + selectType;
};
var levelWin;
var authenticationWin;
var powerWin;
page.openLevel = function (userId, levelId) {
    $("#userId").val(userId);
    $("#levelSelect option[value='" + levelId + "']").attr("selected", true);
    levelWin = layer.open({
        type: 1,
        title: "更改等级",
        shadeClose: true,
        shade: false,
        area: ['450px', '300px'],
        content: $("#levelList")
    });
};

page.updateLevel = function () {
    var userId = $("#userId").val();
    var levelId = $("#levelSelect option:selected").val();
    page.closeLevelWin();
    $.ajax({
        url: 'updateLevel',
        type: "POST",
        timeout: 5000,
        data: {
            userId: userId, levelId: levelId
        },
        success: function (result) {
            if (result.success) {
                layer.msg('保存成功');
                window.location.reload();
            }
        }, error: function () {
            layer.msg('连接超时~');
        }
    })
};

page.closeLevelWin = function () {
    layer.close(levelWin);
};

page.openAuthentication = function (userId, authenticationId) {
    $("#userId").val(userId);
    $("#authenticationSelect option[value='" + authenticationId + "']").attr("selected", true);
    authenticationWin = layer.open({
        type: 1,
        title: "更改身份",
        shadeClose: true,
        shade: false,
        area: ['450px', '300px'],
        content: $("#authenticationList")
    });
};

page.updateAuthentication = function () {
    var userId = $("#userId").val();
    var authenticationId = $("#authenticationSelect option:selected").val();
    page.closeAuthenticationWin();
    $.ajax({
        url: 'updateAuthentication',
        type: "POST",
        timeout: 5000,
        data: {
            userId: userId, authenticationId: authenticationId
        },
        success: function (result) {
            if (result.success) {
                layer.msg('保存成功');
                window.location.reload();
            }
        }, error: function () {
            layer.msg('连接超时~');
        }
    })
};

page.closeAuthenticationWin = function () {
    layer.close(authenticationWin);
};


page.openPower = function (userId, power) {
    $("#userId").val(userId);
    power += "";
    var one = power.substr(0, 1);
    var two = power.substr(1, 2);
    if ($("input[name='articlePower']").val() == one) {
        $("input[name='articlePower']").attr("checked", true);
    }
    if ($("input[name='commentPower']").val() == two) {
        $("input[name='commentPower']").attr("checked", true);
    }
    powerWin = layer.open({
        type: 1,
        title: "更改权限",
        shadeClose: true,
        shade: false,
        area: ['450px', '300px'],
        content: $("#powerList")
    });
};

page.updatePower = function () {
    var userId = $("#userId").val();
    var power = "";
    if ($("input[name='articlePower']").is(":checked")) {
        power += "1";
    } else {
        power += "0";
    }
    if ($("input[name='commentPower']").is(":checked")) {
        power += "1";
    } else {
        power += "0";
    }
    page.closePowerWin();
    $.ajax({
        url: 'updatePower',
        type: "POST",
        timeout: 5000,
        data: {
            userId: userId, power: power
        },
        success: function (result) {
            if (result.success) {
                layer.msg('保存成功');
                window.location.reload();
            }
        }, error: function () {
            layer.msg('连接超时~');
        }
    })
};

page.closePowerWin = function () {
    layer.close(powerWin);
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