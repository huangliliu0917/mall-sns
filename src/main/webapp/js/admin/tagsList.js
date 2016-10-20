var requestData = {name: "", pageNo: 1, pageSize: 10};
var page = {};

page.init = function () {
    page.list();
}

page.search = function () {
    requestData.name = $("#name").val();
    page.list();
}

page.list = function () {
    $.get("/top/tags/list", requestData, function (data) {
        if (data) {
//                alert(data.list.length);
            var html = "";
            for (var i = 0; data.list.length > i; i++) {
                var row = data.list[i];
                html += '<tr>' +
                    ((extend == extendType.checkbox) ? '<td><input type="checkbox" name="checkbox" value="' + row.id + '" extend="' + row.name + '" /></td>' : '' ) +
                    '<td>' + row.id + '</td>' +
                    '<td>' + row.name + '</td>' +
                    '<td><button style="padding: 0px 5px;" data-placement="top" data-toggle="tooltip" class="btn btn-default tooltips" type="button" data-original-title="编辑" onclick="page.edit(' + row.id + ')">编辑</button></td></tr>';
            }

            $("#list").html(html);

            $("#recordCount").text(data.page.recordCount);
            $("#pageIndex").text(data.page.pageNo);
            $("#pageCount").text(data.page.totalPage);
            $("#pageSize").text(data.page.pageSize);
            page.paging(data.page.totalPage);
        }
    });
}

page.paging = function (totalPage) {
    var p = new hot.paging(".pagination", requestData.pageNo, totalPage, 9);
    p.init(function (pageNo) {
        requestData.pageNo = pageNo;
        page.list();
    });
}

page.add = function () {
    window.location.href = '/top/tags/tagsEdit/0?extend=' + extend;
}

page.edit = function (id) {
    window.location.href = '/top/tags/tagsEdit/' + id + "?extend=" + extend;
}

page.getQueryString = function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

page.select = function () {
    if (extend == extendType.checkbox) {
        var list = [];
        var checks = $('#list input[name="checkbox"]:checked');
        checks.each(function () {
            var item = {id: $(this).val(), name: $(this).attr("extend")};
            list.push(item);
        });

        return list;
    }
    return null;
}


//页面扩展
var extendType = {radio: "radio", checkbox: "checkbox"};
var extend = page.getQueryString("extend");

$(function () {
    page.init();
    if (extend == extendType.checkbox) {
        $("table thead td").show();
    }
})
