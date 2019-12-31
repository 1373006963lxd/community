/**
 * Created by codedrinker on 2019/12/1.
 *
 * 以前html操作某一个标签用documen.getElementByClassName("main")
 * js 则 $(".main")
 */

/**
 * 提交回复jquery通过id获取到值
 * 回复内容type=1--一级评论
 */
function post() {
    var questionId = $("#question_id").val();
    // console.log(questionId); 测试打印数据
    var content = $("#comment_content").val();
    // console.log(content)
    comment2target(questionId, 1, content);
}

function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容~~~");
        return;
    }
    //jquery异步加载提交数据到服务端
    $.ajax({
        type: "POST",
        url: "/comment",
        /*确定类型*/
        contentType: 'application/json',
        /*json通常用于与服务器端交换数据
        * 在向服务器端发送数据时一般是字符串
        * 我们可以使用JSON.stringfy()方法将javaScript对象转换为字符串*/
        data: JSON.stringify({
            "parentId": targetId,
            "cotent": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                /*回复成功以后关闭回复框*/
                // $("#comment_section ").hide();
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    /*确认框--没有登录的时候弹出来*/
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        /*跳转到首页*/
                        window.open("https://github.com/login/oauth/authorize?client_id=047abba9242fa3ff3faa&redirect_uri=" + document.location.origin + "/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}
/*回复评论--二级评论type=2
* */
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);
}

/**
 * 展开二级评论
 */
function collapseComments(e) {
    /*jquery获取data中的id*/
    var id = e.getAttribute("data-id");
    /*获取到这个二级评论div标签*/
    var comments = $("#comment-" + id);

    // 获取一下二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        // 折叠二级评论--高亮状态消失
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        /*最外成标签1*/
        var subCommentContainer = $("#comment-" + id);
        /*如果二级评论不等于1（没有子元素）则展开二级评论，因为最后一个子评论是评论框*/
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            /*高亮状态一直显示*/
            e.classList.add("active");
        } else {
            /*有子元素*/
            /*下面的拼接就是根据一级评论内容拼接的二级评论*/
            /*当点击评论按钮时，先获取数据*/
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    /*4借用data返回值进行拼接二级评论 */
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));
                    /*5*/
                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        /*标签里面的内容一般在这里html*/
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.cotent
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreator).format('YYYY-MM-DD')
                    })));

                    /*3标签，样式*/
                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    /*2*/
                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论--在class样式里面加上in就是展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}

function showSelectTag() {
    $("#select-tag").show();
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}