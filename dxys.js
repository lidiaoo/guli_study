/*
丁香医生vip
***************************
QuantumultX:
[rewrite_local]
^https:\//dxy.com\/app\/i\/user\/ask\/account\?ac url script-response-body https://raw.githubusercontent.com/litieyin/AD_VIP/main/Script/dxys.js

[mitm]
hostname = dxy.com
**************************/
// 1. 先把服务器返回的 JSON 字符串（$response.body）转成 JS 对象，方便精准修改字段
var bodyObj = "";


// 4. 完成修改，返回新的响应体
$done({ body: modifiedBody });
