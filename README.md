# AndroidWeatherForecast
具体步骤：
1. 通过LitePal建立City数据库
2. 通过OkHttp建立网络连接，获取API内容
3. 通过GSON解析数据，并存入City表格中
4. 使用ListView，将数据库内容按照三级筛选显示出来，省级（pid==0）;根据选择的省级（id)来筛选市级（既Pid== 省级id),显示出来；通过对ListVIew设置监听事件，获取按钮的位置，通过intent传递id跳转到新的页面中。
5. 点击该市，获取city_code，向“http://t.weather.sojson.com/api/weather/city/+city_code”请求数据
6. 通过GSON解析数据
