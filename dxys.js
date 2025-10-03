/*
丁香医生vip
***************************
QuantumultX:
[rewrite_local]
^https:\//dxy.com\/app\/i\/user\/ask\/account\?ac url script-response-body https://raw.githubusercontent.com/lidiaoo/guli_study/refs/heads/master/dxys.js

[mitm]
hostname = dxy.com
**************************/ 


// 4. 完成修改，返回新的响应体
$done({ body: {} });
