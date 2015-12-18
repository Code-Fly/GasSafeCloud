# GasSafeCloud
GasSafeCloud


###	Server API
##### core
http://www.mydomain.com/api/keystone/core<br/>
##### Refresh access token
http://www.mydomain.com/api/keystone/token/refresh<br/>
##### Get access token
http://www.mydomain.com/api/keystone/token/query<br/>
##### Get JS API ticket
http://www.mydomain.com/api/keystone/jsapi/ticket/query<br/>
##### Get image location on server
http://www.mydomain.com/api/keystone/file/image/product<br/>
method: POST<br/>
param: "url", required = true<br/>
param: "pid", required = true<br/>

###	Menu API
##### create
http://www.mydomain.com/api/keystone/menu/create<br/>
##### get
http://www.mydomain.com/api/keystone/menu/get<br/>
##### delete
http://www.mydomain.com/api/keystone/menu/delete<br/>

###	User API
##### Get SNS user
http://www.mydomain.com/api/keystone/user/query/sns/{openId}/{accessToken}<br/>
method: GET<br/>
##### SNS user OAuth
http://www.mydomain.com/api/keystone/user/sns/oauth<br/>
method: GET<br/>
##### Get WeChat user
http://www.mydomain.com/api/keystone/user/list<br/>
method: GET<br/>
##### Get WeChat user
http://www.mydomain.com/api/keystone/user/query/{openId}<br/>
method: GET<br/>
##### Get WeChat user group list
http://www.mydomain.com/api/keystone/user/group/list<br/>
method: GET<br/>
##### Get WeChat user group by openid
http://www.mydomain.com/api/keystone/user/group/query/{openId}<br/>
method: GET<br/>

###	Material API
##### Get material
http://www.mydomain.com/api/keystone/material/query/{mediaId}<br/>
method: GET<br/>
##### Get material list
http://www.mydomain.com/api/keystone/material/list<br/>
method: GET<br/>

###	Shop API
##### Get shop
http://www.mydomain.com/api/keystone/shop/query/{poiId}<br/>
method: GET<br/>
##### Get shop list
http://www.mydomain.com/api/keystone/shop/list<br/>
method: GET<br/>
param: "begin", required = false<br/>
param: "limit", required = false<br/>

###	Product API
##### Get product
http://www.mydomain.com/api/keystone/product/query/{productId}<br/>
method: GET<br/>
##### Get product list
http://www.mydomain.com/api/keystone/product/list/{status}<br/>
method: GET<br/>
param: "groupId", required = false<br/>
param: "orderBy", required = false<br/>
param: "sort", required = false<br/>
param: "minPrice", required = false<br/>
param: "maxPrice", required = false<br/>
##### Get product group list
http://www.mydomain.com/api/keystone/product/group/list<br/>
method: GET<br/>
##### Get product group detail by groupId
http://www.mydomain.com/api/keystone/product/group/query/{groupId}<br/>
method: GET<br/>

###	Order API
##### Get order
http://www.mydomain.com/api/keystone/order/query/{orderId}<br/>
method: GET<br/>
##### Get order list
http://www.mydomain.com/api/keystone/order/list/{status}<br/>
method: GET<br/>
param: "beginTime", required = false<br/>
param: "endTime", required = false<br/>

###	Merchant API
##### Send redpack
http://www.mydomain.com/api/keystone/merchant/redpack/send/{openId}<br/>
##### Send coupon (doesn't work)
http://www.mydomain.com/api/keystone/merchant/coupon/send/{couponStockId}/{openId}<br/>
##### Pay refund (doesn't work)
http://www.mydomain.com/api/keystone/merchant/pay/refund/{tradeId}<br/>

###	CustomerService API
##### Send coupon
http://www.mydomain.com/api/keystone/customerservice/coupon/send<br/>
method: POST<br/>
param: "touser", required = true<br/>
param: "cardId", required = true<br/>
##### Create account
http://www.mydomain.com/api/keystone/customerservice/account/create<br/>
method: POST<br/>
param: "kf_account", required = true<br/>
param: "nickname", required = true<br/>
param: "password", required = true<br/>
##### Update account
http://www.mydomain.com/api/keystone/customerservice/account/update<br/>
method: POST<br/>
param: "kf_account", required = true<br/>
param: "nickname", required = true<br/>
param: "password", required = true<br/>
##### Delete account
http://www.mydomain.com/api/keystone/customerservice/account/delete<br/>
method: POST<br/>
param: "kf_account", required = true<br/>
##### List all account
http://www.mydomain.com/api/keystone/customerservice/account/list<br/>
method: GET<br/>
##### List online account
http://www.mydomain.com/api/keystone/customerservice/account/online<br/>
method: GET<br/>




