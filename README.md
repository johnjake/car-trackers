# Car Track   ![alt text](https://github.com/johnjake/members-directory/blob/master/app/src/main/res/drawable/ic_github_logo.png)

This app is about listing of car owners including personal information of the owner and vehicles. The app mainly runs locally. 

For the CarTrack development the following IDE and patterns are used: 


    IDE: Android Studio 2020.3.1 Canary 13 
    Runtime Version: 11.8.0 
    VM: OpenJDK 64-Bit

    Gradle Version: 7.0.0-alpha13
    Kotlin Version: 1.4.31
    Gradle Type: DLS

    Language: Kotlin

    Architechture Pattern: MVVM
    
    3rd Party
    Dependency Injection: Koin-2.0.1
    Image Stream: Coil-0.13.0
    Http Client: OkHttp-4.9.0
    Http Client: Retrofit-2.9.0
    Concurrency Library: Coroutine-1.4.2
    Reactive Extensions: RxJava2 (2.2.10)
    RecyclerView Paging: Paging3 (3.0.0-beta03)
    Navigation Component: 2.3.5
    Room: 2.3.0-alpha03
    
    

USER API: The users list can be obtained at this endpoint: https://jsonplaceholder.typicode.com/users  in JSON format, and a total of 10 users. 


FEATURES: 
          
          * When you run the app for the first time it will access first the endpoint API then persist the user data to room (SQLite). 
          To login the cartrack app you may use the email as username and password: `password+n` sample username: `sincere@april.biz` password: `password1` 
          the n'th is the user id.  
          
          * You can also add user and sign-up can be found in login screen.
          
          * Paging was not coded due to time limit and endpoint does not have pagination. 
          * Search features are not yet integrated due to time limitation of the app development.

          
   
   User Interface:
   
  ![alt text](https://github.com/johnjake/car-trackers/blob/screen-shot/car_track_screen.png)



