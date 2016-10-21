/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

var win;

$(function () {
    //初始化分页
    var pageinate = new hot.paging(".pagination", pageIndex, totalPage, 7);

    pageinate.init(function (p) {
        window.location.href = rootURL + p;
    });
});

var userHandler = {
    selectUser: function () {
        $("#frame_user").attr("src", "/top/user/index?selectType=multi");
        $("#btnUser").attr("data-toggle", "modal");
        $("#btnUser").attr("data-target", "#modal_user");
    },
    submitSelect: function () {
        var userArray = $("#frame_user")[0].contentWindow.page.selectMany();
        $("#submitSelect").attr("data-dismiss", "modal");
        var newArray = [];
        $.each(userArray, function (n, value) {
            if ($.inArray(parseInt(value), idList) < 0) {
                newArray.push(value);
            }
        });
        if (newArray.length == 0) {
            layer.msg("您未推荐用户或者推荐用户已重复");
            return;
        }
        var ld = layer.load(5, {shade: false});
        $.ajax({
            url: '/top/user/suggested',
            type: 'POST',
            traditional: true,
            data: {ids: newArray},
            success: function (result) {
                layer.close(ld);
                if (result.success) {
                    layer.msg("推荐成功");
                    window.location.reload();
                }
            }, error: function () {
                layer.close(ld);
                layer.msg("系统繁忙");
            }
        })
    },
    deleteSuggested: function (obj) {
        var userId = $(obj).attr("userId");
        $.ajax({
            url: '/top/user/deleteSuggested',
            type: 'POST',
            data: {id: userId},
            success: function (result) {
                if (result.success) {
                    layer.msg("删除成功");
                    window.location.reload();
                }
            }, error: function () {
                layer.msg("系统繁忙");
            }
        })
    }
};