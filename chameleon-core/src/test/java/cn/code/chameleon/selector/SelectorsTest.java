package cn.code.chameleon.selector;

import org.junit.Test;

/**
 * @author liumingyu
 * @create 2018-04-17 下午3:53
 */
public class SelectorsTest {

    String html = "<div><h1>test<a href=\"xxx\">aabbcc</a></h1><title>aabbcc</title></div>";

    String html2 = "<title>aabbcc</title>";

    @Test
    public void test_or_and() throws Exception {
        System.out.println(Selectors.and(Selectors.$("title"), Selectors.regex("aa(bb)cc")).select(html2));
        System.out.println(Selectors.or(Selectors.xpath("//title"), Selectors.$("div h1")).selectList(html));
    }

    @Test
    public void test_all() throws Exception {
        System.out.println(Selectors.$("div h1 a").select(html));
        System.out.println(Selectors.$("div h1 a", "href").select(html));
        System.out.println(Selectors.$("div h1", "innerHtml").select(html));
        System.out.println(Selectors.$("div h1", "text").select(html));
        System.out.println(Selectors.$("div h1", "allText").select(html));
        System.out.println(Selectors.xpath("//a/@href").select(html));
        System.out.println(Selectors.regex("a href=\"(.*)\"").select(html));
        System.out.println(Selectors.regex("(a href)=\"(.*)\"", 2).select(html));
        System.out.println(Selectors.smartContent().select("\n" +
                "\n" +
                "\n" +
                "\n" +
                "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>Single Post</title>\n" +
                "    <meta name=\"description\" content=\"\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width\">\n" +
                "\n" +
                "    <!-- Bootstrap styles -->\n" +
                "    <link rel=\"stylesheet\" href=\"../css/bootstrap.min.css\">\n" +
                "\n" +
                "    <!-- Font-Awesome -->\n" +
                "    <link rel=\"stylesheet\" href=\"../css/font-awesome/css/font-awesome.min.css\">\n" +
                "\n" +
                "    <!-- Google Webfonts -->\n" +
                "    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,600|PT+Serif:400,400italic' rel='stylesheet' type='text/css'>\n" +
                "\n" +
                "    <!-- Styles -->\n" +
                "   <link rel=\"stylesheet\" href=\"../css/style.css\" id=\"theme-styles\">\n" +
                "    <!--[if lt IE 9]>      \n" +
                "        <script src=\"js/vendor/google/html5-3.6-respond-1.1.0.min.js\"></script>\n" +
                "    <![endif]-->\n" +
                "<link rel=\"stylesheet\" href=\"../dist/css/share.min.css\">\n" +
                "<script src=\"/cropper/js/jquery.min.js\"></script>\n" +
                " <script src=\"../js/common.js\"></script>\n" +
                "  <script src=\"../js/single.js\"></script>\n" +
                "<link rel=\"stylesheet\" href=\"../css/common.css\">\n" +
                "<link rel=\"stylesheet\" href=\"../css/single.css\">\n" +
                "<!-- share.js -->\n" +
                "<script src=\"../dist/js/social-share.min.js\"></script>\n" +
                "    <script>\n" +
                "        $(function() {\n" +
                "  $(\".heart\").on(\"click\", function() {\n" +
                "\tvar id = $(\"#sId\").val();\n" +
                "    $.ajax({\n" +
                "        type:'POST',\n" +
                "        url:'/single/upvote/139',\n" +
                "        dataType:'json',\n" +
                "        contentType:'application/json;charset=UTF-8',\n" +
                "        success:function(data){\n" +
                "        \tvar obj = eval(data);\n" +
                "        \tif(obj.result == \"ok\") {\n" +
                "        \t\t$(\".heart\").addClass(\"is-active\");\n" +
                "        \t\t$(\".message-hidden\").html(\"谢谢~\");\n" +
                "        \t\tsetTimeout(\"location.reload()\",1000);\n" +
                "        \t} \n" +
                "        \tif(obj.result == \"not\") {\n" +
                "        \t\t$(\".message-hidden\").html(\"您已经赞过啦~\");\n" +
                "        \t}\n" +
                "        \tif(obj.result == \"error\") {\n" +
                "        \t\t$(\".message-hidden\").html(\"有点小故障~\");\n" +
                "        \t}\n" +
                "        }\n" +
                "    })\n" +
                "    $ ('.message-hidden').show().delay(1000).fadeOut();\n" +
                "  });\n" +
                "});\n" +
                "    </script>\n" +
                "\n" +
                "</head>\n" +
                "<body>\n" +
                "    <header>\n" +
                "        <div class=\"widewrapper masthead\">\n" +
                "            <div class=\"container\">\n" +
                "                <a href=\"/home\" id=\"logo\">\n" +
                "                    <img src=\"../img/logo.png\" alt=\"clean Blog\">\n" +
                "                </a>\n" +
                "\n" +
                "                <div id=\"mobile-nav-toggle\" class=\"pull-right\">\n" +
                "                    <a href=\"#\" data-toggle=\"collapse\" data-target=\".clean-nav .navbar-collapse\">\n" +
                "                        <i class=\"fa fa-bars\"></i>\n" +
                "                    </a>\n" +
                "                </div>\n" +
                "\n" +
                "                <nav class=\"pull-right clean-nav\">\n" +
                "                    <div class=\"collapse navbar-collapse\">\n" +
                "                        <ul class=\"nav nav-pills navbar-nav\">\n" +
                "                          \n" +
                "                             <li>\n" +
                "                                <a href=\"/home\">Home</a>\n" +
                "                            </li>\n" +
                "                            <li>\n" +
                "                                <a href=\"/index\">Index</a>\n" +
                "                            </li>\n" +
                "                            <li>\n" +
                "                                <a href=\"/about\">About</a>\n" +
                "                            </li>\n" +
                "                            <li>\n" +
                "                                <a href=\"/contact\">Contact</a>\n" +
                "                            </li>                       \n" +
                "                        </ul>\n" +
                "                    </div>\n" +
                "                </nav>        \n" +
                "\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"widewrapper subheader\">\n" +
                "            <div class=\"container\">\n" +
                "                <div class=\"clean-breadcrumb\">\n" +
                "                    <a href=\"/home\">Blog</a>\n" +
                "                    <span class=\"separator\">&#x2F;</span>\n" +
                "                    <a href=\"/home?blogType=项目\">项目</a>\n" +
                "                    <!-- <span class=\"separator\">&#x2F;</span>\n" +
                "                    <a href=\"#\">HTML Template</a> -->\n" +
                "                </div>\n" +
                "\n" +
                "              \n" +
                "                <div class=\"clean-searchbox\">\n" +
                "                    <form action=\"#\" method=\"get\" accept-charset=\"utf-8\">\n" +
                "                       \n" +
                "                        <input class=\"searchfield\" id=\"searchbox\" type=\"text\" placeholder=\"Search\">\n" +
                "                         <button class=\"searchbutton\" type=\"submit\">\n" +
                "                            <i class=\"fa fa-search\"></i>\n" +
                "                        </button>\n" +
                "                    </form>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </header>\n" +
                "\n" +
                "    <div class=\"widewrapper main\">\n" +
                "        <div class=\"container\">\n" +
                "            <div class=\"row\">\n" +
                "                <div class=\"col-md-8 blog-main\">\n" +
                "                    <article class=\"blog-post\">\n" +
                "                        <header>\n" +
                "\t\t\t\t\t\t\t<input type=\"hidden\" id=\"bId\" value=\"139\"/>                        \n" +
                "                            <div class=\"lead-image\">\n" +
                "                                <img src=\"/upload/image/151557286831459816_pic.jpg\" alt=\"\" class=\"img-responsive\">\n" +
                "                            </div>\n" +
                "                        </header>\n" +
                "                        <div class=\"body\">\n" +
                "                            <h1>跑商项目介绍</h1>\n" +
                "                            <div class=\"meta\">\n" +
                "                                <i class=\"fa fa-user\"></i> 刘明宇 <i class=\"fa fa-calendar\"></i>Wed Jan 10 16:27:52 CST 2018<i class=\"fa fa-eye\"></i>23Views<i class=\"fa fa-heart\"></i>2Likes<i class=\"fa fa-comments\"></i><span class=\"data\"><a href=\"#create-comment\">0 Comments</a></span>\n" +
                "                            </div>\n" +
                "                            <p>该项目是一个没有商家侧的双端跑腿系统，技术架构：SpringMVC，Mybatis，redis，mysql，后端测试：junit，swagger，前端技术：没前端！</p><p>双端为配送员+用户，双端的信息无非是很大众的信息不再赘述，需要注意的是配送员的信息很特别，为了保证用户的订单和人身安全，对用户和配送员负责，配送员需要身份证号和人+身份证照片，配送员注册完账号后，需要审核，通过后即可接单，信用度从100起，每被用户投诉，或订单时间过长，或有各种不道德的事情，减少信用额度，当额度小于某个值后，账号自动禁用，无法登录，配送员需要申诉，管理员可以使用禁用或启用功能。</p><p>为了配送员的抢单调度，需要涉及到配送员的位置+订单和目的地的位置，所以设计了一种更灵活的调度方式：配送员可自己配置可接单列表显示的订单距自己的距离，也可配置从源地址到目标地址的距离长度（配送员可选择最大配送长度），可配送距离范围（里或公里都可），让抢单配送更具蜂窝特性</p><p>用户端和配送员端可能会有在不同地点的情况，所以都单抽出来一张地址表，用来存储用户或配送员的所有地址，设为当前选择的地址更新到用户和配送员的信息表中。该表的地址字段：用户id，地址名称，经度，纬度，创建时间，更新时间，是否已删除。</p><p>该系统暂定四种订单类型，也称四条业务线：帮我买／帮我送／帮我取／代排队 四种业务线的订单共用同一个表结构：run_order，按业务线的code区分 四种业务类型所需的字段结构有略微的差别：以下只列出所需不同的字段：&nbsp;</p><p>帮我买：收货地址，备注地址，收货电话，购买地址，备注地址，购买要求&nbsp;</p><p>帮我送：发货地址，备注地址，发货电话，收货地址，备至地址，收货电话，备注留言，发货时间&nbsp;</p><p>帮我取：取货地址，备注地址，取货电话，收货地址，备注地址，收货电话，备注信息，取货时间&nbsp;</p><p>代排队：排队地址，备注地址，联系电话，备注信息，排队时间，排队时常&nbsp;</p><p>以下列出基本相同的字段：订单号，用户id，配送员id，订单类型，物品，订单距离，跑腿费，小费，两个地址的经纬度，订单状态，支付类型，支付金额（只支持在线付款），订单创建时间，更新时间，配送员接单时间，开始配送时间，订单完成时间 以上为订单所有需要的数据，四种业务线可能会有些字段用不上，置空。 为了以后业务线的扩展，设计了一种下单结构，即可配置枚举业务线，所有业务都走统一接口，根据前端传过来的业务线code分辨，根据code获取需要执行的类和方法，利用反射机制去执行该条业务线所对应的操作。 围绕着订单还会涉及到用户支付记录，有以下字段：用户id，订单号，订单金额，小费，支付金额（在线支付时），满减金额，实际支付，支付类型，支付时间 说到满减：涉及到了用户的优惠卷问题，该优惠卷邮管理员分批或统一发放，有以下数据：标题，内容，类型，满额，减额，状态，开始时间，结束时间。</p><p>既然有支付扣款／退款入款问题就会涉及到用户和配送员的余额问题（因暂且不能与支付平台对接，所以先用余额模拟），就会有用户的余额和余额交易记录：用户id／配送员id，老余额，交易金额，新余额，交易类型（入款还是出款），交易流水号，创建时间。用户支付订单的金额，在该订单完成后打入配送员的账户中，余额可提现。</p><p>如果订单出现了问题，比如双方的任意一方存在突发事件，可取消订单，支付的金额用户可以进行申请退款操作，申请退款字段：id，用户id，订单id，退款金额（扣除优惠卷），退款原因，申请状态，操作人，创建时间，更新时间。 。 其中申请状态分为已申请，通过退款，拒绝退款。 在退款成功后会插入退款记录：用户id，配送员id，订单号，退款金额，退款原因，责任方，来源（哪个支付平台），操作者，操作时间。</p><p>因这种线下交互的行为肯定会出现任意一方的问题，则增加投诉功能：用户id，配送员id，主动举报方，举报理由，举报等级，举报时间。 举报次数会涉及到双端的信用额度，和退款情况。也会涉及到配送员的配送等级，配送等级（待定具体规则）</p><p>除了以上的功能外，扩展了两个推送消息功能，两个推送消息类型是不同的，一个为文章类型推送，会包含标题，内容，图片，状态，推送时间等字段。 另一种推送消息为短小，实时推送类型的，比如在app页面的某一条位置上轮播滚动消息，包含某某在某某时间买了某某物品。因该消息只有一条内容字段，所以需要每完成一个订单就实时拼出个以上格式的消息插入数据库，并用前多少时间内的消息滚动轮播。</p><p>该系统的登录功能采用的是SSO单点登录，会根据token进行登录拦截，具体参照另一个wiki，不懂问我。 git命令不熟可以参考：<a href=\"http://lblogg.cn/single/126\">Git常用命令</a>&nbsp;开发流程不会，问我 以上所有的功能所需的字段可能不是最后版本，但已经是基本稳定的版本，如果改也是修改某个字段。</p><p>如果在某个功能或整体架构设计有更好建议，可以商量。有时间的时候少玩点游戏，都参与进来。</p>\n" +
                "                        </div>\n" +
                "                    </article>\n" +
                "   \n" +
                "\t<p><a href=\"javascript:void(0)\" onclick=\"dashangToggle()\" class=\"dashang\" title=\"打赏，支持一下\">打赏</a></p> \n" +
                "\t<div class=\"hide_box\"></div> \n" +
                "\t<div class=\"shang_box\"> \n" +
                "    <a class=\"shang_close\" href=\"javascript:void(0)\" onclick=\"dashangToggle()\" title=\"关闭\"><img src=\"../img/close.jpg\" alt=\"取消\" /></a> \n" +
                "   \t<div class=\"shang_tit\"> \n" +
                "        <p>感谢您的支持，我会继续努力的!</p> \n" +
                "    </div> \n" +
                "    <div class=\"shang_payimg\"> \n" +
                "        <img src=\"../img/alipayimg.png\" alt=\"扫码支持\" title=\"扫一扫\" /> \n" +
                "    </div> \n" +
                "    <div class=\"pay_explain\">扫码打赏，你说多少就多少</div> \n" +
                "    <div class=\"shang_payselect\"> \n" +
                "        <div class=\"pay_item checked\" data-id=\"alipay\"> \n" +
                "            <span class=\"radiobox\"></span> \n" +
                "            <span class=\"pay_logo\"><img src=\"../img/alipay.jpg\" alt=\"支付宝\" /></span> \n" +
                "        </div> \n" +
                "        <div class=\"pay_item\" data-id=\"weipay\"> \n" +
                "            <span class=\"radiobox\"></span> \n" +
                "            <span class=\"pay_logo\"><img src=\"../img/wechat.jpg\" alt=\"微信\" /></span> \n" +
                "        </div> \n" +
                "    </div> \n" +
                "    <div class=\"shang_info\"> \n" +
                "        <p>打开<span id=\"shang_pay_txt\">支付宝</span>扫一扫，即可进行扫码打赏哦</p> \n" +
                "    </div> \n" +
                "\t</div>\n" +
                "\n" +
                "                    <aside class=\"social-icons clearfix\">\n" +
                "                    \t<div class=\"stage\">\n" +
                "                    \t\n" +
                "\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t  <div class=\"heart\" style=\"color:red;font-weight:bold;\" title=\"Love this\"><span class=\"upvoteCount\">2</span> Like</div>\n" +
                "\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t  <div class=\"message-hidden\" style=\"color:red;\"></div>\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\n" +
                "                       <h3>Share on </h3> \n" +
                "                        <!-- <a href=\"#\"><i class=\"fa fa-facebook\"></i></a> <a href=\"#\"><i class=\"fa fa-twitter\"></i></a> <a href=\"#\"><i class=\"fa fa-google\"></i></a> -->\n" +
                "                        <div class=\"social-share\" data-initialized=\"true\">\n" +
                "                        <a href=\"#\" class=\"social-share-icon icon-qq\"></a>\n" +
                "                        <a href=\"#\" class=\"social-share-icon icon-wechat\"></a>\n" +
                "\t\t\t\t\t    <a href=\"#\" class=\"social-share-icon icon-qzone\"></a>\n" +
                "                        <a href=\"#\" class=\"social-share-icon icon-weibo\"></a>\n" +
                "                        </div>\n" +
                "                    </aside>\n" +
                "\t\t\n" +
                "                        <!-- <article class=\"comment reply\">\n" +
                "                            <header class=\"clearfix\">\n" +
                "                                <img src=\"../img/avatar.png\" alt=\"A Smart Guy\" class=\"avatar\">\n" +
                "                                <div class=\"meta\">\n" +
                "                                    <h3><a href=\"#\">John Doe</a></h3>\n" +
                "                                    <span class=\"date\">\n" +
                "                                        24 August 2015\n" +
                "                                    </span>\n" +
                "                                    <span class=\"separator\">\n" +
                "                                        -\n" +
                "                                    </span>\n" +
                "                                    \n" +
                "                                    <a href=\"#create-comment\" class=\"reply-link\">Reply</a>                \n" +
                "                                </div>\n" +
                "                            </header>\n" +
                "                             <div class=\"body\">\n" +
                "                               Lorem ipsum dolor sit amet, consectetur adipisicing elit. Facere sit perspiciatis debitis, vel ducimus praesentium expedita, assumenda ipsum cum corrupti dolorum modi. Rem ipsam similique sapiente obcaecati tenetur beatae voluptatibus.\n" +
                "                            </div>\n" +
                "                        </article>\n" +
                "\n" +
                "                        <article class=\"comment \">\n" +
                "                            <header class=\"clearfix\">\n" +
                "                                <img src=\"../img/avatar.png\" alt=\"A Smart Guy\" class=\"avatar\">\n" +
                "                                <div class=\"meta\">\n" +
                "                                    <h3><a href=\"#\">John Doe</a></h3>\n" +
                "                                    <span class=\"date\">\n" +
                "                                        24 August 2015\n" +
                "                                    </span>\n" +
                "                                    <span class=\"separator\">\n" +
                "                                        -\n" +
                "                                    </span>\n" +
                "                                    \n" +
                "                                    <a href=\"#create-comment\" class=\"reply-link\">Reply</a>                \n" +
                "                                </div>\n" +
                "                            </header>\n" +
                "                             <div class=\"body\">\n" +
                "                               Lorem ipsum dolor sit amet, consectetur adipisicing elit. Facere sit perspiciatis debitis, vel ducimus praesentium expedita, assumenda ipsum cum corrupti dolorum modi. Rem ipsam similique sapiente obcaecati tenetur beatae voluptatibus.\n" +
                "                            </div>\n" +
                "                        </article>\n" +
                "\n" +
                "                        <article class=\"comment\">\n" +
                "                            <header class=\"clearfix\">\n" +
                "                                <img src=\"../img/avatar.png\" alt=\"A Smart Guy\" class=\"avatar\">\n" +
                "                                <div class=\"meta\">\n" +
                "                                    <h3><a href=\"#\">John Doe</a></h3>\n" +
                "                                    <span class=\"date\">\n" +
                "                                        24 August 2015\n" +
                "                                    </span>\n" +
                "                                    <span class=\"separator\">\n" +
                "                                        -\n" +
                "                                    </span>\n" +
                "                                    \n" +
                "                                    <a href=\"#create-comment\" class=\"reply-link\">Reply</a>                \n" +
                "                                </div>\n" +
                "                            </header>\n" +
                "                             <div class=\"body\">\n" +
                "                               Lorem ipsum dolor sit amet, consectetur adipisicing elit. Facere sit perspiciatis debitis, vel ducimus praesentium expedita, assumenda ipsum cum corrupti dolorum modi. Rem ipsam similique sapiente obcaecati tenetur beatae voluptatibus.\n" +
                "                            </div>\n" +
                "                        </article>        \n" +
                "                    </aside> -->\n" +
                "\n" +
                "                    <aside class=\"create-comment\" id=\"create-comment\">\n" +
                "                        <hr>    \n" +
                "\n" +
                "                        <h2><i class=\"fa fa-pencil\"></i> Add Comment</h2>\n" +
                "\n" +
                "                        <form action=\"#\" method=\"get\" accept-charset=\"utf-8\">\n" +
                "                            <div class=\"row\">\n" +
                "                                <div class=\"col-md-6\">\n" +
                "                                    <input type=\"text\" name=\"name\" id=\"comment-name\" placeholder=\"Your Name\" class=\"form-control input-lg\">    \n" +
                "                                </div>\n" +
                "                                <div class=\"col-md-6\">\n" +
                "                                    <input type=\"email\" name=\"email\" id=\"comment-email\" placeholder=\"Email\" class=\"form-control input-lg\">    \n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "\n" +
                "                            <input type=\"url\" name=\"name\" id=\"comment-url\" placeholder=\"Website (选填)\" class=\"form-control input-lg\">\n" +
                "\n" +
                "                            <textarea rows=\"10\" name=\"message\" id=\"comment-body\" placeholder=\"Your Message\" class=\"form-control input-lg\"></textarea>\n" +
                "\n" +
                "                            <div class=\"buttons clearfix\">\n" +
                "                                <button type=\"button\" id=\"button\" class=\"btn btn-xlarge btn-clean-one\">Submit</button>\n" +
                "                            </div>\n" +
                "                        </form>\n" +
                "                    </aside>\n" +
                "                </div>\n" +
                "                <aside class=\"col-md-4 blog-aside\">\n" +
                "                     <div class=\"aside-widget\">\n" +
                "                        <header>\n" +
                "                            <h3>Tags</h3>\n" +
                "                        </header>\n" +
                "                        <div class=\"body clearfix\">\n" +
                "                            <ul class=\"tags\">\n" +
                "                            \n" +
                "                            \t<li><a href=\"/home?blogType=Java\">Java</a></li>\n" +
                "                            \n" +
                "                            \t<li><a href=\"/home?blogType=爬虫\">爬虫</a></li>\n" +
                "                            \n" +
                "                            \t<li><a href=\"/home?blogType=Spring\">Spring</a></li>\n" +
                "                            \n" +
                "                            \t<li><a href=\"/home?blogType=Mabtis\">Mabtis</a></li>\n" +
                "                            \n" +
                "                            \t<li><a href=\"/home?blogType=Hibernate\">Hibernate</a></li>\n" +
                "                            \n" +
                "                            \t<li><a href=\"/home?blogType=Struts2\">Struts2</a></li>\n" +
                "                            \n" +
                "                            \t<li><a href=\"/home?blogType=Exception\">Exception</a></li>\n" +
                "                            \n" +
                "                            \t<li><a href=\"/home?blogType=Offer\">Offer</a></li>\n" +
                "                            \n" +
                "                            \t<li><a href=\"/home?blogType=Ajax\">Ajax</a></li>\n" +
                "                            \n" +
                "                            \t<li><a href=\"/home?blogType=前端\">前端</a></li>\n" +
                "                            \n" +
                "                            \t<li><a href=\"/home?blogType=算法\">算法</a></li>\n" +
                "                            \n" +
                "                            \t<li><a href=\"/home?blogType=设计模式\">设计模式</a></li>\n" +
                "                            \n" +
                "                            \t<li><a href=\"/home?blogType=博客更新\">博客更新</a></li>\n" +
                "                            \n" +
                "                            \t<li><a href=\"/home?blogType=Git\">Git</a></li>\n" +
                "                            \n" +
                "                            \t<li><a href=\"/home?blogType=框架\">框架</a></li>\n" +
                "                            \n" +
                "                            \t<li><a href=\"/home?blogType=SSO\">SSO</a></li>\n" +
                "                            \n" +
                "                            \t<li><a href=\"/home?blogType=项目\">项目</a></li>\n" +
                "                            \n" +
                "                            </ul>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                    \n" +
                "                    <div class=\"aside-widget\">\n" +
                "                        <header>\n" +
                "                            <h3>Recommend Articles</h3>\n" +
                "                        </header>\n" +
                "                        <div class=\"body\">\n" +
                "                            <ul class=\"clean-list\">\n" +
                "                            \n" +
                "                                <li><a href=\"/single/124\">大名鼎鼎的二叉树神级遍历Morris</a></li>\n" +
                "                             \n" +
                "                                <li><a href=\"/single/121\">Java判断两个链表是否相交，如果相交返回第一个节点</a></li>\n" +
                "                             \n" +
                "                                <li><a href=\"/single/118\">Java字符串转整型</a></li>\n" +
                "                             \n" +
                "                                <li><a href=\"/single/83\">单例模式的几种方法</a></li>\n" +
                "                             \n" +
                "                                <li><a href=\"/single/117\">无序数组中第K大的数</a></li>\n" +
                "                             \n" +
                "                            </ul>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                \n" +
                "                    <div class=\"aside-widget\">\n" +
                "                        <header>\n" +
                "                            <h3>Ji Qing Links</h3>\n" +
                "                        </header>\n" +
                "                        <div class=\"body\">\n" +
                "                            <ul class=\"clean-list\">\n" +
                "                            \n" +
                "                            \t<li><a href=\"http://isanson.me\">http://isanson.me</a></li>\n" +
                "                            \n" +
                "                            \t<li><a href=\"https://www.nowcoder.com/profile/982925\">https://www.nowcoder.com/profile/982925</a></li>\n" +
                "                            \n" +
                "                            \t<li><a href=\"https://juejin.im/\">https://juejin.im/</a></li>\n" +
                "                            \n" +
                "                            \t<li><a href=\"https://www.cnblogs.com/\">https://www.cnblogs.com/</a></li>\n" +
                "                            \n" +
                "                            \t<li><a href=\"http://www.csdn.net/\">http://www.csdn.net/</a></li>\n" +
                "                            \n" +
                "                            </ul>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </aside>\n" +
                "                <a id=\"gotop\" href=\"javascript:;\"><i class=\"fa fa-arrow-up\"></i></a>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "     <footer>\n" +
                "        <div class=\"widewrapper footer\">\n" +
                "            <div class=\"container\">\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"col-md-4 footer-widget\">\n" +
                "                        <h3> <i class=\"fa fa-user\"></i><a style=\"color:#fff;\" href=\"/about\">About</a></h3>\n" +
                "\n" +
                "                       <p>My English is not very good, please read the reader not to mind. This is my first online blog.The whole blog is my own hand code(In addition to the front frame), If there is a bad place to do, hope to give me advice</p>\n" +
                "                       <p>Similarly, if the reader has any technical aspects of the problem, you can comment or contact me, I will try the first time to reply.</p>\n" +
                "                    </div>\n" +
                "\n" +
                "                    <div class=\"col-md-4 footer-widget\">\n" +
                "                        <h3> <i class=\"fa fa-pencil\"></i> Three lines of love letters</h3>\n" +
                "                        <ul class=\"clean-list\">\n" +
                "                            <li><a href=\"javascript:;\">int i = 10;</a></li>\n" +
                "                            <li><a href=\"javascript:;\">int you = 3;</a></li>\n" +
                "                            <li><a href=\"javascript:;\">you = i / you;</a></li>\n" +
                "                        </ul>\n" +
                "                    </div>\n" +
                "\n" +
                "                    <div class=\"col-md-4 footer-widget\">\n" +
                "                        <h3> <i class=\"fa fa-envelope\"></i><a style=\"color:#fff;\" href=\"/contact\">Contact Me</a></h3>\n" +
                "                        <p>Tips, if you want to contact me, be sure to write a real email address, or I may not receive your message.</p>\n" +
                "                        <p>If you think my blog has something useful to you, share it to other people, the hard link below the link.</p>\n" +
                "                         <div class=\"footer-widget-icon\">\n" +
                "                            <div class=\"social-share\" data-initialized=\"true\">\n" +
                "                        <a href=\"#\" class=\"social-share-icon icon-qq\"></a>\n" +
                "                        <a href=\"#\" class=\"social-share-icon icon-wechat\"></a>\n" +
                "\t\t\t\t\t    <a href=\"#\" class=\"social-share-icon icon-qzone\"></a>\n" +
                "                        <a href=\"#\" class=\"social-share-icon icon-weibo\"></a>\n" +
                "                        </div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                   \n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"widewrapper copyright\">\n" +
                "                Copyright 2017 . <a href=\"/about\">LIUMINGYU</a>\n" +
                "        </div>\n" +
                "    </footer>\n" +
                "\n" +
                "    <script src=\"//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js\"></script>\n" +
                "    <script src=\"../js/bootstrap.min.js\"></script>\n" +
                "    <script src=\"../js/modernizr.js\"></script>\n" +
                "\n" +
                "\t\n" +
                "</body>\n" +
                "</html>"));
    }
}
