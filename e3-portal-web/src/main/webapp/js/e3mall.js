var E3MALL = {
    checkLogin : function(){
        var _ticket = $.cookie("token");
        if(!_ticket){
            return ;
        }
        $.ajax({
            url : "http://localhost:1028/user/token/" + _ticket,
        	// js是有限制的不允许跨域，例如下面：端口1026加载过来的js不能请求端口1028下的数据【跨域限制】,服务端响应了，但浏览器有限制
        	// 跨域的条件：【域名不同，IP地址不同，IP域名相同端口不同】
			// 设置请求为jsonp
            dataType : "jsonp",
            type : "GET",
			// 下边的data就是我们从服务端拿到的数据
            success : function(data){
                if(data.status == 200){
                    var username = data.data.username;
                    var html = username + "，欢迎来到宜立方购物网！<a href=\"http://localhost:1028/page/login\" class=\"link-logout\">[退出]</a>";
                    // 这里更新id为loginbar，并且在shortcut.jsp的span标签中的内容
                    $("#loginbar").html(html);
                }
            }
        });
    }
}

$(function(){
    // 查看是否已经登录，如果已经登录查询登录信息
    E3MALL.checkLogin();
});