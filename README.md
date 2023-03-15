# boredapp

App which retrieves a list of tasks from [Bored API](https://www.boredapi.com/) and let's the user
save and update which tasks it chooses.

# setup

Just use Android Studio recommended version (as time of write, it's Eletric Eel) and build the
project in the `demoDebug` variant.

# preview

<img src="preview/boredapppreview.gif" width="200">

# architecture

| Module                 | Responsibility                                                                                                                                     |
|------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------|
| :app                   | Boots the app, sets theme and load feature modules                                                                                                 |
| :build-logic           | Sets build plugins, flavors to use in the app modules                                                                                              |
| :core:common           | Utility functions that sets up coroutine dispatches, converts flows to results                                                                     |
| :core:data             | Repository logic, which calls the network and/or database logic and emits flows of the relevant results                                            |
| :core:database         | Database containing logic to save and update the user saved activities, using Room                                                                 |
| :core:model            | Models used in the UI layer                                                                                                                        |
| :core:network          | Network logic reaching the API set in [secrets.default.properties], using Retrofit                                                                 |
| :core:testing          | Not yet used                                                                                                                                       |  
| :feature:activity-list | Feature logic, containing the UI layer. Reaches the `core:data` repositories, loads data and updates it using `ViewModels`, `Fragments` and `Flow` |  

Best practices based on `nowinandroid` project, which follows
the [official architecture guidance](https://developer.android.com/topic/architecture)

# suggested improvements

* improve layout
* add unit tests
* add integration tests
* search activity
* cleanup unused libs
* make design components from activity item
* add user login
* save user tasks in custom api
* save already browsed activities locally, to make an offline first app
* cache activity results better