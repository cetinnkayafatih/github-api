# github-api
Program apache organizasyonuna ait en çok indirilen
echarts, superset, dubbo, spark, airflow repositorylerinin her biri için
https://api.github.com/repos/apache/{repo}/contributors API'sinden
dönen ilk 10 -contributions alanına göre büyükten küçüğe sıralanan- kullanıcının 
username'i ve contributions alanını kaydeder. Sonrasında her bir kullanıcının 
username'i ile getUserInfo fonksiyonundan 
https://api.github.com/users/{username} API'sini çağırıp dönen json 
objesinden company ve location bilgilerini çeker. Her bir user için
tek satırda repo, username, contributions, company, location 
bilgilerini users.txt dosyasına yazdırır.
