function login() {
    console.log(1)
    let username = document.getElementById("username").value
    let password = document.getElementById("password").value
    if (correctLogin(username, password)){
        let xml = new XMLHttpRequest();
        let data = "username=" + username + "&" + "password=" + password
        console.log(data);
        xml.open("get", "login?" + data)
        xml.send()
        xml.onreadystatechange = function () {
            if (xml.readyState === 4) {
                if (xml.responseText !== "true") {
                    alert("账号或密码错误")
                    document.getElementById("password").value = ""
                }else {
                    window.location.href = "../html/main.html"
                    document.getElementById("password").value = ""
                }
            }
        }
    }
    else {
        alert("登录失败")
    }
}

let editInfo=document.getElementsByClassName("edit-info");

// 注册用户
function registered(){
    let usernumber = editInfo[0].value;
    let password = editInfo[1].value;
    let username = editInfo[2].value;
    let xml = new XMLHttpRequest();
    let data = "userNumber=" + usernumber + "&password=" + password + "&username=" + username
    console.log(data)
    xml.open("post", "login?" + data)
    xml.send()
    xml.onreadystatechange = function () {
        if (xml.readyState === 4) {
            if (xml.responseText !== "true") {
                alert("该用户已注册")
                document.getElementById("password").value = ""
            }else {
                alert("注册成功")
                hidden();
                clearEdit();
            }
        }
    }
}

// 添加用户
function add(){
    show();
    changeHeader();
    changeBut();
}

//显示
function show(){
    document.querySelector(".hidden").style.display = "block";
    document.querySelector(".edit").style.display = "block";
}

//隐藏
function hidden(){
    document.querySelector(".hidden").style.display = "none";
    document.querySelector(".edit").style.display = "none";
}

//更改弹出框文字
function changeHeader(){
    let headTitle=document.querySelector(".head-title");
    headTitle.innerHTML = "用户注册";
}

//更改弹出框底部按钮
function changeBut(){
    let leftBut=document.querySelector(".edit-submit");
    leftBut.innerHTML="注册";
    leftBut.onclick=submit;
}

//清空表
function clearEdit(){
    for(let i = 0; i < editInfo.length; i++){
        editInfo[i].value = "";
    }
}

//注册按钮提交方法
function submit() {

    if (correct()) {

        registered();

        //隐藏
        hidden();
    }
    else {
        alert("注册失败");
    }

    //清空
    clearEdit();
}

//取消按钮
function cancel(){
    hidden();
    clearEdit();
    for(let i=0;i<editInfo.length;i++){
        editInfo[i].disabled=false;
    }
}

// 注册表单验证
function correct(){
    // 验证空
    for (let i = 0; i < editInfo.length; i++){
        if (editInfo[i].value == ""){
            alert("不能为空")
            return false;
        }
        // 验证用户名
        let usernameCheck = /^[0-9a-zA-Z]{6,11}$/
        if (!usernameCheck.test(editInfo[0].value)){
            alert("用户名输入错误")
            return false;
        }
        // 验证密码
        let passwordCheck = /^[0-9a-zA-Z]{6,11}$/
        if (!passwordCheck.test(editInfo[1].value)){
            alert("密码输入错误")
            return false;
        }
    }
    return true;
}

// 登录表单验证
function correctLogin(username, password){
    // 验证空
    if (username == "" || password == ""){
        alert("不能为空")
        return false;
    }
    // 验证用户名
    let usernameCheck = /^[0-9a-zA-Z]{6,11}$/
    if (!usernameCheck.test(username)){
        alert("用户名输入错误")
        return false;
    }
    // 验证密码
    let passwordCheck = /^[0-9a-zA-Z]{6,11}$/
    if (!passwordCheck.test(password)){
        alert("密码输入错误")
        return false;
    }
    return true;
}
