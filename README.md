# Flight Ticketing

## Tools

1.firebase  
2.android studio  
3.[crawler](https://github.com/WuIFan/TicketCrawler_With_FireBase)  

## Libraries

### Add in build.gradle(app)

```gradle
dependencies {
    implementation 'com.google.firebase:firebase-core:16.0.9'
    implementation 'com.google.firebase:firebase-database:17.0.0'
    implementation 'com.google.firebase:firebase-firestore:19.0.2'
    implementation 'com.squareup.picasso:picasso:2.5.2'
}
apply plugin: 'com.google.gms.google-services'
```

### Add in build.gradle

```gradle
classpath 'com.google.gms:google-services:4.2.0'
```

## Platform

### API

Android API 28

### Virtual device

Nexus 5X API 28 9.0

## Team member

1. P76977086 [李儒錦](https://github.com/kiam123) : 50%  
2. P76071179 [吳一凡](https://github.com/WuIFan)  : 50%  

## For demo

### Content

1.[PPT](https://docs.google.com/presentation/d/1_7Rxz29IJenLgbTiVHL8YfTBDpoQJY7gbl31a3Db0nc/edit?usp=sharing)  
2.[APK](./Ticketing.apk)  

### Description  

1.初始頁面可以用來搜尋機票，但是機票的database是用爬蟲建出來的，如果查詢還沒爬過的機票會顯示  
"send query to server, turn back after after few minute"  
但是目前並沒有讓server固定跑爬蟲，而是手動執行，如果要更新可能要通知我們  
2.第二頁(愛心)是已訂閱的機票，如果database有更新，且價錢有符合，會送通知給使用者，一樣在demo時必須手動更新database，才能控制價錢的更新  
3.目前可以查詢到的機票  
```
TPEGUM2019-07-01
TPEGUM2019-07-02
TPEGUM2019-07-03
TPEGUM2019-07-04
TPEGUM2019-07-06
TPEGUM2019-07-07
TPEKUL2019-07-01
TPEKUL2019-07-02
TPEKUL2019-07-03
TPEKUL2019-07-04
```
