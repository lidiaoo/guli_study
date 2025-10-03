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
var bodyObj = JSON.parse($response.body);

// 2. 精准定位到 membership_info 里的两个目标字段，进行修改
// （只改会员相关的状态，不影响其他用户信息，避免误改）
if (bodyObj.data && bodyObj.data.items && bodyObj.data.items[0]) {
  // 确保会员信息字段存在，防止报错
  var memberInfo = bodyObj.data.items[0].membership_info;
  if (memberInfo) {
    memberInfo.status = 0; // 把会员状态从 9（失效）改成 0（正常）
    memberInfo.expire_timestamp = 4102415999000; // 过期时间改成 2100年12月31日
  }
}

// 3. 把修改后的 JS 对象，再转成 JSON 字符串，发回给 App
var modifiedBody = JSON.stringify(bodyObj);

// 4. 完成修改，返回新的响应体
$done({ body: modifiedBody });
