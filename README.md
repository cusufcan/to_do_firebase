# ToDoFirebase

+ Kotlin öğrenirken yaptığım bir başka uygulamamı paylaşmak istedim. ToDoFirebase, basit bir todo not tutma uygulamasıdır. Önceki yaptığım not tutma uygulamasından farklı olarak verileri yerelde değil, Firebase veritabanında tutmaktadır. Kullanıcı, içeriğini girip Firebase veritabanına kaydettiği yapılacaklar listesini istediği zaman görüntüleyebilir ve silebilir.

## Temel Özellikler:

+ İçeriği girilen bir todo Firebase veritabanında kayıt altına alınabilir.
+ Oluşturulan todo'lar ana ekranda görüntülenebilir.
+ İstenilen todo uygulama silinse dahi geri yüklemede e-posta ve şifre ile giriş yapıldığı sürece görülebilir.
+ İstenilmeyen todo silinebilir.

## Kurulum:

    git clone https://github.com/cusufcan/to_do_firebase

## Ekran Görüntüleri:

<table>
    <tr>
        <td><img src="app/src/main/res/drawable/ss1.png" alt="1"></td>
        <td><img src="app/src/main/res/drawable/ss2.png" alt="2"></td>
        <td><img src="app/src/main/res/drawable/ss3.png" alt="3"></td>
    </tr>
    <tr>
        <td><img src="app/src/main/res/drawable/ss4.png" alt="4"></td>
        <td><img src="app/src/main/res/drawable/ss5.png" alt="5"></td>
        <td><img src="app/src/main/res/drawable/ss6.png" alt="6"></td>
    </tr>
</table>

## Kullanılan Teknolojiler:

+ Kotlin
+ ViewBinding
+ Runnable / Handler
+ Firebase Auth
+ Firebase Realtime Database
+ Navigation

## Lisans:

    MIT

