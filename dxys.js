/*
丁香医生vip
*************************** 
[rewrite_local]
^https:\//dxy.com\/app\/i\/user\/ask\/account url script-response-body https://raw.githubusercontent.com/lidiaoo/guli_study/refs/heads/master/dxys.js

[mitm]
hostname = dxy.com
**************************/  
let obj = JSON.parse($response.body);
    obj.items[0].membership_info = {
                    "status": 0,
                    "expire_timestamp": 0,
                    "type": 0,
                    "trial_max": 0,
                    "trial_received": 0
  }
$done({body: JSON.stringify(obj)});
