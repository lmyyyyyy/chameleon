/**
 * Created by Intellij IDEA.
 * @Author LUOLIANG
 * @Date 2016/8/2
 * @Comment js文件，用于页面发送ajax请求
 */

//定义一个avalonjs的控制器
var viewmodel = avalon.define({
    //id必须和页面上定义的ms-controller名字相同，否则无法控制页面
    $id: "register",
    datalist: {},
    text: "获取验证码",

    getCode: function () {
        $.ajax({
            type:"POST",  //请求方式
            url:"http://localhost:8090/index/code?email=" + this.email.value,  //请求路径：页面/方法名字
            data: null,     //参数
            dataType:"text",
            contentType:"application/json; charset=utf-8",
            beforeSend:function(XMLHttpRequest){
                $("#code").text("120");
            },
            success:function(data){  //成功
                var result = jQuery.parseJSON(data);
                alert("result : " + result);
                alert("status : " + result.status)
            },
            error:function(obj, msg, e){   //异常
                alert("OH,NO");
            }
        });
        /*$.ajax({
            type: "post",
            url: "/hello/data",    //向springboot请求数据的url
            data: {},
            success: function (data) {
                $('button').removeClass("btn-primary").addClass("btn-success").attr('disabled', true);

                viewmodel.datalist = data;

                viewmodel.text = "数据请求成功，已渲染";
            }
        });*/
    }
});