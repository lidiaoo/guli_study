/*
丁香医生vip
*************************** 
[rewrite_local]
^https:\//dxy.com\/app\/i\/user\/ask\/account url script-response-body https://raw.githubusercontent.com/lidiaoo/guli_study/refs/heads/master/dxys.js

[mitm]
hostname = dxy.com
**************************/  
var body = $response.body;
if (body) {
    try {
        // 尝试JSON解析方式修改（推荐）
        let json = JSON.parse(body);
        if (json.status === 9) json.status = 0;
        if (json.expire_timestamp === 0) json.expire_timestamp = 4102415999000;
        body = JSON.stringify(json);
    } catch (e) {
        // 字符串替换方式（注意正则表达式添加 g 标志）
        body = body.replaceAll(/\"status\":9/g, "\"status\":0")
                   .replaceAll(/\"expire_timestamp\":0/g, "\"expire_timestamp\":4102415999000");
    }
}
$done({body});  
