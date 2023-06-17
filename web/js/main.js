let myFriends = [];
let div
let img
let p
// 初始化cvs,cvs记录了消息页面和别人聊天的最后一条信息，类似QQ
let cvs = []
// newFriendApply用来记录在线状态下有多少个新的好友申请
let newFriendApply = [];
// 后端传回的用户信息
let userFriend = [];
// 所有用户
let allUsers = []
// 当前登录用户
let user
//要发送的好友的id
let toId
//好友是否在线
let friendState
//聊天记录
let messages = []
let websocket = null;
let username;

// 登陆后获取好友信息
getUserFriend()
//获取所有用户
getAllUsers()
// 初始化操作类似于dealHaveMessageBox()，即给每个好友框添加点击事件
makeFriendEvent();

// pages分别保存了消息页面和联系人页面，实现两个页面之间切换
let pages = [];
let page1 = document.getElementsByClassName("middle_chat")[0];
pages.push(page1);
// 禁用网页选取内容
document.onselectstart = function () {
    return false;
}
// 禁用网页copy
document.oncopy = function () {
    return false;
}
// 禁用网页右击(最后添加上，不然不能调试)
document.oncontextmenu = function(){
    return false;
}


function getUserFriend(){
    let xml = new XMLHttpRequest();
    xml.open("get", "handle?")
    xml.send()
    xml.onreadystatechange = function () {
        if (xml.readyState === 4) {
            // response 响应属性根据请求的responseType属性的值，以ArrayBuffer、Blob、Document、JavaScript Object或DOMString的形式返回响应体内容
            let text = xml.response
            // parse 解析一个JSON字符串，构造由该字符串描述的JavaScript值或对象
            userFriend = JSON.parse(text)
            user = userFriend.at(0)
            username = user["userName"]
            document.getElementsByClassName("myName")[0].innerHTML = user["userName"]
            for (let i = 1 ; i < userFriend.length; i++){
                dealServiceEnsure("../img/demo-1-bg.jpg", userFriend.at(i)["userName"], userFriend.at(i)["id"], userFriend.at(i)["userNumber"], userFriend.at(i)["state"])
            }
            //获取好友申请
            getApply(user["id"])
        }
    }
}

function getAllUsers(){
    let xml = new XMLHttpRequest();
    xml.open("get", "user")
    xml.send()
    xml.onreadystatechange = function () {
        if (xml.readyState === 4){
            let text = xml.response
            allUsers = JSON.parse(text)
        }
    }
}

setTimeout(function (){
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://" + document.location.host + "/websocket/" + username);
    } else {
        alert('当前浏览器 Not support websocket')
    }
    //连接发生错误的回调方法
    websocket.onerror = function() {
        console.log("WebSocket连接发生错误");
    };

//连接成功建立的回调方法
    websocket.onopen = function() {
        console.log("WebSocket连接成功");
    }

//接收到消息的回调方法
    websocket.onmessage = function(event) {
        console.log(event)
        console.log(event.data)
        if ("string" == typeof event.data){
            if (event.data.slice(0, 18) === "$ServiceImageFrom:"){

            }
            else {
                let msg = event.data.split(" ")
                appendMessage(msg[1],"left","../img/demo-1-bg.jpg")
            }
        }
        else {
            let reader = new FileReader();
            reader.readAsDataURL(event.data)
            reader.onload = function (e) {
                if (e.target.readyState === FileReader.DONE) {
                    let result = e.target.result
                    appendImage(result,"left","../img/demo-1-bg.jpg")
                }
            }
        }
    }

//连接关闭的回调方法
    websocket.onclose = function() {
        console.log("WebSocket连接关闭");
    }

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function() {
        closeWebSocket();
    }

//关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }
}, 6000)


// 处理同意好友
function dealServiceEnsure(img_path, userName, id, userNumber, state) {
    // 给好友列表页面添加新的好友
    let friends = document.getElementsByClassName("friends")[0]
    let hidden = document.createElement("div")
    let div = document.createElement("div")
    let img = document.createElement("img")
    let div_right = document.createElement("div")
    let p = document.createElement("p")
    let p_userNUmber = document.createElement("p")
    friends.appendChild(div)
    div.appendChild(img)
    div.appendChild(div_right)
    div.appendChild(hidden)
    hidden.className = "hidden"
    hidden.innerHTML = id
    div_right.appendChild(p)
    div_right.appendChild(p_userNUmber)
    div.className = "friend"
    img.className = "left"
    img.setAttribute("src", img_path)
    div_right.className = "right"
    p.className = "name"
    p.innerHTML = userName
    p_userNUmber.className = "userNumber"
    p_userNUmber.innerHTML = userNumber
    myFriends.push({
        "id": id,
        "img_path": img_path,
        "userName": userName,
        "userNumber": userNumber,
        "state":state
    })
    // 重新给每个好友添加点击事件，因为有新的好友进来，所以需要刷新
    makeFriendEvent();
}

// 切换消息和联系人两个页面
let boxes = [];
let button1 = document.getElementsByClassName("peopleContact")[0];
boxes.push(button1);
for (let i = 0; i < boxes.length; ++i) {
    boxes[i].addEventListener("click", function () {
        if (this.children[1].innerHTML === "联系人") {
            let temp = document.getElementsByClassName("messageNumAlert")[0]
            temp.innerHTML = "" + newFriendApply.length + ""
            let temp1 = document.getElementsByClassName("messageAlert")[0]
            if (temp1.style.display === "block")
                temp.style.display = "block"
            pages[0].style.display = "block";
            boxes[0].style.color = "#0099ff";
        }
    })
}
// 实现添加好友功能
let findNewFriends = document.getElementsByClassName("findNewFriend")[0]
let makeNewFriends = document.getElementsByClassName("makeNewFriends")[0]
let hidden = document.getElementsByClassName("hidden")[0]
findNewFriends.addEventListener("click", function () {
    hidden.style.display = "block"
    makeNewFriends.style.display = "block"
})
let cancel = document.getElementsByClassName("cancel")[0]
let ensure = document.getElementsByClassName("ensure")[0]
cancel.addEventListener("click", function () {
    let input = document.getElementsByClassName("friendID")[0]
    input.value = "请输入账号"
    hidden.style.display = "none"
    makeNewFriends.style.display = "none"
})
ensure.addEventListener(("click"), function () {
    let input = document.getElementsByClassName("friendID")[0]
    let input_values = input.value.trim();
    let count = 0
    for (let i = 0; i < allUsers.length; i++){
        if (input_values === allUsers.at(i)["userNumber"]){
            sendApply(user["id"], allUsers.at(i)["id"])
            hidden.style.display = "none"
            makeNewFriends.style.display = "none"
            input.value = "请输入账号"
        }
        else {
            count++;
        }
    }
    if (count === allUsers.length){
        alert("账号不正确");
    }
})

//发送好友申请
function sendApply(mid, fid){
    let xml = new XMLHttpRequest();
    xml.open("post", "handle?mid=" + mid + "&fid=" + fid)
    xml.send()
    xml.onreadystatechange = function () {
        if (xml.readyState === 4){
            if (xml.responseText === "true"){
                alert("消息发送成功")
            }
            else {
                alert("消息发送失败")
            }
        }
    }
}

// 实现同意好友功能
let agreeNewFriendApply = document.getElementsByClassName("newFriend")[0]
let friendApplyList = document.getElementsByClassName("friendApplyList")[0]
agreeNewFriendApply.addEventListener("click", function () {
    hidden.style.display = "block"
    friendApplyList.style.display = "block"
    let temp1 = document.getElementsByClassName("messageAlert")[0]
    temp1.style.display = "none"
    let temp2 = document.getElementsByClassName("messageNumAlert")[0]
    temp2.innerHTML = ""
    temp2.style.display = "none"
})
let friendApply_button = document.getElementsByClassName("friendApplyList_button")[0]
friendApply_button.addEventListener("click", function () {
    hidden.style.display = "none"
    friendApplyList.style.display = "none"
})

//获得好友申请
function getApply(mid){
    let xml = new XMLHttpRequest();
    xml.open("get", "friend?mid=" + mid)
    xml.send()
    xml.onreadystatechange = function () {
        if (xml.readyState === 4){
            let text = xml.response
            newFriendApply = JSON.parse(text)
            if (newFriendApply != null){
                addNew(newFriendApply)
            }
            else {
                alert("输入账号错误")
            }
        }
    }
}

//每过一分钟调用
setInterval(function (){
    //获取好友申请
    getApply(user["id"])
}, 60000)

// 如果有新的好友申请，则要在申请页面显示出来
function addNew(users) {
    let list = document.getElementsByClassName("list")[0];
    while (list.children.length > 0)
        list.children[0].remove()
    for (let i = 0; i < users.length; ++i) {
        let div = document.createElement("div");
        div.setAttribute("index", i);
        div.setAttribute("img_path", "")
        let img = document.createElement("img")
        img.setAttribute("src","../img/demo-1-bg.jpg")
        let middle = document.createElement("div")
        let p_top = document.createElement("p")
        let p_bottom = document.createElement("p")
        let right = document.createElement("div")
        let button_top = document.createElement("button")
        let button_bottom = document.createElement("button")
        list.appendChild(div)
        div.className = "OneUser"
        if (i % 2 === 0)
            div.style.background = "#a09494"
        else
            div.style.background = "#eed1d1"
        div.appendChild(img)
        img.className = "img"
        img.setAttribute("src", "../img/demo-1-bg.jpg")
        div.appendChild(middle)
        middle.className = "middle"
        div.appendChild(right)
        right.className = "right"
        middle.appendChild(p_top)
        middle.appendChild(p_bottom)
        p_top.className = "p_top"
        p_bottom.className = "p_bottom"
        p_top.innerHTML = users[i]["userName"]
        p_bottom.innerHTML = ""
        right.appendChild(button_top)
        button_top.className = "button_top"
        button_top.innerHTML = "同意"
        right.appendChild(button_bottom)
        button_bottom.className = "button_bottom"
        button_bottom.innerHTML = "拒绝"
    }
    let button_bottom = document.getElementsByClassName("button_bottom")
    for (let i = 0; i < button_bottom.length; ++i) {
        button_bottom[i].addEventListener("click", function () {
            let list = document.getElementsByClassName("list")[0];
            let index = this.parentNode.parentNode.getAttribute("index");
            let id_temp = users[index].id
            for (let a = 0; a < newFriendApply.length; ++a) {
                if (newFriendApply[a] === id_temp) {
                    newFriendApply.splice(a, 1)
                    break
                }
            }
            manageApply(user["id"], users[index]["id"], 1)
            users.splice(index, 1)
            list.removeChild(this.parentNode.parentNode)
        })
    }
    let button_top = document.getElementsByClassName("button_top")
    for (let i = 0; i < button_top.length; ++i) {
        button_top[i].addEventListener("click", function () {
            let img_path = this.parentNode.parentNode.getAttribute("img_path")
            let userName = this.parentNode.parentNode.children[1].children[0].innerHTML
            let id = this.parentNode.parentNode.children[1].children[1].innerHTML
            let node = this.parentNode.parentNode;
            let data = node.children[1].children[1]
            let index = node.getAttribute("index")
            manageApply(user["id"], users[index]["id"], 0)
            // 添加好友
            let friends = document.getElementsByClassName("friends")[0]
            let hidden = document.createElement("div")
            let div = document.createElement("div")
            let img = document.createElement("img")
            let div_right = document.createElement("div")
            let p = document.createElement("p")
            let p_userNUmber = document.createElement("p")
            friends.appendChild(div)
            div.appendChild(img)
            div.appendChild(div_right)
            div.appendChild(hidden)
            hidden.className = "hidden"
            hidden.innerHTML = id
            div_right.appendChild(p)
            div_right.appendChild(p_userNUmber)
            div.className = "friend"
            img.className = "left"
            img.setAttribute("src","../img/demo-1-bg.jpg")
            div_right.className = "right"
            p.className = "name"
            p.innerHTML = users[index].userName
            p_userNUmber.className = "userNumber"
            p_userNUmber.innerHTML= users[index].userNumber
            let id_temp = users[index].id
            for (let a = 0; a < newFriendApply.length; ++a) {
                if (newFriendApply[a] === id_temp) {
                    newFriendApply.splice(a, 1)
                    break
                }
            }
            myFriends.push({
                "id": id,
                "img_path": img_path,
                "userName": userName,
                "userNumber": users[i]["userNumber"],
                "state": users[i]["state"]
            })
            users.splice(index, 1)
            let list = document.getElementsByClassName("list")[0];
            list.removeChild(node)
            makeFriendEvent();
        })
    }
}

//处理好友请求
//mid为当前登录用户id，fid为发送申请的id
//num为0为接受，num为1为拒绝
function manageApply(mid, fid, num){
    let xml = new XMLHttpRequest();
    xml.open("post", "friend?num=" + num + "&mid=" + mid + "&fid=" + fid)
    xml.send()
    xml.onreadystatechange = function () {
        if (xml.readyState === 4){
            if (xml.responseText === "true"){
                console.log(1)
            }
            else {
                console.log(2)
            }
        }
    }
}

//在聊天框的左边或者右边显示文字消息
function appendMessage(str, position, img_path) {
    let middle = document.getElementsByClassName("middle_chatIndex")[0]
    let OneMessage = document.createElement("div")
    let img = document.createElement("img")
    let max = document.createElement("div")
    let detail = document.createElement("div")
    max.appendChild(detail)
    middle.appendChild(OneMessage)
    detail.innerHTML = str
    img.setAttribute("src", img_path)
    if (position === "left") {
        OneMessage.appendChild(img)
        OneMessage.appendChild(max)
        OneMessage.className = "OneMessage1"
        max.className = "max1"
        img.className = "img1"
        detail.className = "detail1"
    } else {
        OneMessage.appendChild(img)
        OneMessage.appendChild(max)
        OneMessage.className = "OneMessage2"
        max.className = "max2"
        detail.className = "detail2"
        img.className = "img2"
    }
    let temp = document.getElementsByClassName("middle_chatIndex")[0]
    temp.scrollTop = temp.scrollHeight
}

// 在聊天框的左边 或者右边显示图片消息
function appendImage(str, position, img_path) {
    let middle = document.getElementsByClassName("middle_chatIndex")[0]
    let OneMessage = document.createElement("div")
    let img = document.createElement("img")
    let img_msg = document.createElement("img")
    middle.appendChild(OneMessage)
    img.setAttribute("src", img_path)
    let reg = /^(\d|-)+.(jpg|png)$/
    if (reg.test(str))
        img_msg.setAttribute("src", "../img/" + str)
    else
        img_msg.setAttribute("src", "" + str)
    if (position === "left") {
        OneMessage.appendChild(img)
        OneMessage.appendChild(img_msg)
        OneMessage.className = "OneMessage1"
        img.className = "img1"
        img_msg.className = "img_msg1"
    } else {
        OneMessage.appendChild(img)
        OneMessage.appendChild(img_msg)
        OneMessage.className = "OneMessage2"
        img.className = "img2"
        img_msg.className = "img_msg2"
    }
    let temp = document.getElementsByClassName("middle_chatIndex")[0]
    temp.scrollTop = temp.scrollHeight
}


// 实现聊天记录在左边或者右边显示文字消息
function logMessage(str, position, img_path) {
    let middle = document.getElementsByClassName("bottom_logIndex")[0]
    let OneMessage = document.createElement("div")
    let img = document.createElement("img")
    let max = document.createElement("div")
    let detail = document.createElement("div")
    max.appendChild(detail)
    middle.appendChild(OneMessage)
    detail.innerHTML = str
    img.setAttribute("src", img_path)
    if (position === "left") {
        OneMessage.appendChild(img)
        OneMessage.appendChild(max)
        OneMessage.className = "OneMessage1"
        max.className = "max1"
        img.className = "img1"
        detail.className = "detail1"
    } else {
        OneMessage.appendChild(img)
        OneMessage.appendChild(max)
        OneMessage.className = "OneMessage2"
        max.className = "max2"
        detail.className = "detail2"
        img.className = "img2"
    }
    let temp = document.getElementsByClassName("bottom_logIndex")[0]
    temp.scrollTop = temp.scrollHeight
}

// 实现聊天记录在左边或者右边显示图片功能
function longImage(str, position, img_path) {
    let middle = document.getElementsByClassName("bottom_logIndex")[0]
    let OneMessage = document.createElement("div")
    let img = document.createElement("img")
    let img_msg = document.createElement("img")
    middle.appendChild(OneMessage)
    img.setAttribute("src", img_path)
    console.log(str)
    img_msg.setAttribute("src", "../img/" + str)
    if (position === "left") {
        OneMessage.appendChild(img)
        OneMessage.appendChild(img_msg)
        OneMessage.className = "OneMessage1"
        img.className = "img1"
        img_msg.className = "img_msg1"
    } else {
        OneMessage.appendChild(img)
        OneMessage.appendChild(img_msg)
        OneMessage.className = "OneMessage2"
        img.className = "img2"
        img_msg.className = "img_msg2"
    }
    let temp = document.getElementsByClassName("middle_chatIndex")[0]
    temp.scrollTop = temp.scrollHeight
}

// 给发送按钮添加点击事件，
let fasong = document.getElementsByClassName("fasong")[0]
let input_value = document.getElementsByClassName("str")[0]
fasong.addEventListener("click", function () {
    let files = document.getElementsByClassName("file")[0].files
    let str = input_value.value.trim()
    if (str.length > 0) {
        let temp = document.getElementsByClassName("middleName")[0]
        appendMessage(str, "right", "../img/demo-1-bg.jpg")
        input_value.value = ""
        // 发给后端
        sendMessage(user["id"], toId, str, friendState)
        websocket.send(temp.innerHTML + " " + str)
    } else if (files.length > 0) {
        let file = files[0];
        let reg = /^(\w|[\u4E00-\u9FA5]|_|\)|\(|}|{|@|\[|]|~)+.(jpg|png)$/
        console.log(reg.test(file.name))
        if (reg.test(file.name)) {
            let reader = new FileReader();
            reader.readAsArrayBuffer(file)
            reader.onload = function (e) {
                let temp1 = new FileReader()
                temp1.readAsDataURL(file)
                temp1.onload = function (a) {
                    console.log(a.target.result)
                    appendImage(a.target.result, "right", "../img/demo-1-bg.jpg")
                }
                websocket.send(e.target.result)
                let temp = document.getElementsByClassName("middleName")[0]
                websocket.send("文件 " + user["id"] + " " + toId + " " + temp.innerHTML);
            }
            document.getElementsByClassName("file")[0].value = ""
        }
    } else {
        input_value.value = ""
    }

})

//发送消息
function sendMessage(fromId, toId, message, state){
    let xml = new XMLHttpRequest();
    xml.open("post", "message?fromId=" + fromId + "&toId=" + toId + "&message=" + message + "&state=" + state)
    xml.send()
    xml.onreadystatechange = function () {
        if (xml.readyState === 4) {
            if (xml.responseText === "true"){
                console.log("消息发送成功")
            }
            else {
                console.log("消息发送失败")
            }
        }
    }
}


// 给每个好友框添加点击事件
function makeFriendEvent() {
    let friend = document.getElementsByClassName("friend")
    for (let i = 0; i < friend.length; ++i) {
        friend[i].addEventListener("click", function () {
            let mainIndex = document.getElementsByClassName("mainIndex")[0]
            let chatIndex = document.getElementsByClassName("chatIndex")[0]
            let name = document.getElementsByClassName("middleName")[0]
            mainIndex.style.display = "none"
            chatIndex.style.display = "block"
            let temp = document.getElementsByClassName("hidden_chatIndex")[0]
            toId = myFriends[i].id
            friendState = myFriends[i].state
            temp.innerHTML = this.children[2].innerHTML;
            name.innerHTML = this.children[1].children[0].innerHTML
            let receive = document.getElementsByClassName("hidden_chatIndex")[0]
            let receive_id = receive.innerHTML
            let middle_chatIndex = document.getElementsByClassName("middle_chatIndex")[0]
            getMessage(user["id"], receive_id)
            setTimeout(function (){
                showMessage()
            }, 20)
        })
    }
}

// 实现返回到mainIndex功能
let back = document.getElementsByClassName("left_chatIndex")[0]
back.addEventListener("click", function () {
    toId = null
    let mainIndex = document.getElementsByClassName("mainIndex")[0]
    let chatIndex = document.getElementsByClassName("chatIndex")[0]
    let temp = document.getElementsByClassName("hidden_chatIndex")[0]
    let middle = document.getElementsByClassName("middle_chatIndex")[0]
    let msg_last
    if (middle.children.length === 0)
        msg_last = ""
    else {
        console.log(middle.children[middle.children.length - 1].children[1].children.length + "1")
        if (middle.children[middle.children.length - 1].children[1].children.length === 0)
            msg_last = ""
        else
            msg_last = middle.children[middle.children.length - 1].children[1].children[0].innerHTML
    }
    mainIndex.style.display = "block"
    chatIndex.style.display = "none"
    pages[0].style.display = "block";
    pages[1].style.display = "none";
    boxes[0].style.color = "#0099ff";
    boxes[1].style.color = "black";
    for (let i = 0; i < cvs.length; ++i) {
        if (cvs[i] === temp.innerHTML) {
            let chat = document.getElementsByClassName("haveMessageBox")
            chat[i].children[2].children[1].innerHTML = msg_last;
            let id = document.getElementsByClassName("hidden_chatIndex")[0].innerHTML
            let lastMsg = msg_last
            if (lastMsg.length === 0)
                return
            return;
        }
    }
    if (msg_last === "")
        return;
    cvs.push(temp.innerHTML)
    let middle_msg = document.getElementsByClassName("middle_msg")[0]
    let div = document.createElement("div")
    let hidden_ = document.createElement("div")
    let numAlert = document.createElement("div")
    hidden_.innerHTML = temp.innerHTML
    let img = document.createElement("img")
    let div_in = document.createElement("div")
    let div_in_top = document.createElement("div")
    let p = document.createElement("p")
    let div_in_top_name = document.createElement("div")
    p.innerHTML = msg_last;
    let user_temp
    console.log(temp + "3")
    for (let i = 0; i < myFriends.length; ++i) {
        console.log(myFriends[i].id + "4")
        if (myFriends[i].id === temp.innerHTML)
            user_temp = myFriends[i]
    }
    div_in_top_name.innerHTML = user_temp["userName"]
    img.setAttribute("src", "")
    middle_msg.appendChild(div)
    div.className = "haveMessageBox"
    div.appendChild(hidden_)
    div.appendChild(img)
    hidden_.className = "hidden_messageBox"
    img.className = "left"
    div.appendChild(div_in)
    div.appendChild(numAlert)
    numAlert.className = "messageNumAlert"
    div_in.className = "right"
    div_in.appendChild(div_in_top)
    div_in_top.className = "rightTop"
    div_in.appendChild(p)
    p.className = "rightBottom"
    div_in_top.appendChild(div_in_top_name)
    div_in_top_name.className = "name"
    let noMessage_temp = document.getElementsByClassName("noMessageBoxes")[0]
    noMessage_temp.style.display = "none"
})
// 实现聊天记录功能
let logChat = document.getElementsByClassName("right_chatIndex")[0]
logChat.addEventListener("click", function () {
    getMessage(user["id"],toId)
    setTimeout(function (){
        document.getElementsByClassName("bottom_logIndex")[0].innerHTML = ""
        let chatIndex = document.getElementsByClassName("chatIndex")[0]
        let logIndex = document.getElementsByClassName("logIndex")[0]
        let receive = document.getElementsByClassName("hidden_chatIndex")[0]
        chatIndex.style.disply = "none"
        logIndex.style.display = "block"
        let bottom_logIndex = document.getElementsByClassName("bottom_logIndex")[0]
        for (let i = 0; i < messages.length; i++){
            let position
            if (messages[i]["fromId"] == user["id"]){
                position = "right"
            }
            else {
                position = "left"
            }
            let reg = /^(\d|-)+.(jpg|png)$/
            if (reg.test(messages[i].message))
                longImage(messages[i].message, position, "../img/demo-1-bg.jpg")
            else
                logMessage(messages[i].message, position, "../img/demo-1-bg.jpg")
        }
    }, 20)
})

//获得聊天记录
function getMessage(fromId, toId){
    let xml = new XMLHttpRequest();
    xml.open("get", "message?&fromId=" + fromId + "&toId=" + toId)
    xml.send()
    xml.onreadystatechange = function () {
        if (xml.readyState === 4) {
            let text = xml.response
            messages = JSON.parse(text)
        }
    }
}

//实现聊天界面显示聊天记录
function showMessage(){
    document.getElementsByClassName("middle_chatIndex")[0].innerHTML = ""
    let logIndex = document.getElementsByClassName("logIndex")[0]
    let chatIndex = document.getElementsByClassName("chatIndex")[0]
    let middle = document.getElementsByClassName("middle_chatIndex")[0]
    logIndex.style.display = "none"
    chatIndex.style.display = "block"
    while (middle.children.length > 0) {
        middle.removeChild(middle.children[0])
    }
    for (let i = 0; i < messages.length; ++i) {
        let position
        if (messages[i]["fromId"] == user["id"])
            position = "right"
        else
            position = "left"
        let reg = /^(\d|-)+.(jpg|png)$/
        if (reg.test(messages[i].message))
            appendImage(messages[i].message, position, "../img/demo-1-bg.jpg")
        else
            appendMessage(messages[i].message, position, "../img/demo-1-bg.jpg")
    }
}


// 实现返回到对话功能
let backToChat_index = document.getElementsByClassName("backToChatIndex")[0]
backToChat_index.addEventListener("click", function () {
    showMessage()
})