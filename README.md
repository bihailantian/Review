# Review
Review

## 1、Theme.AppCompat.Light.NoActionBar
```xml
    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        <!-- 放在页面跳转时出现短暂的黑屏-->
        <item name="android:windowIsTranslucent">true</item>
    </style>
```
使用Theme.AppCompat.Light.NoActionBar 主题可以直接去掉ActionBar


## 2、Retrofit

1、创建Retrofit实例
```java
Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.sojson.com/open/api/")
                .build();
```
**注：**  baseUrl 中的路径(path)必须以 / 结束


2、形如json.shtml?city=北京的url链接不能用@PATH注解，使用@Query注解
```java

  @GET("weather/json.shtml")
  Call<ResponseBody> weatherOfCity(@Query("city") String city);

```



