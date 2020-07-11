## Write a brief outline of the architecture of your app
This test case study is base on [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

There are 3 layers with their own responsibility:
- domain: contains all the business logic of the app (all the use cases required)
- data: provide interfaces to exchange data with API, OAuth provider and stored data (only in memory in this example)
- presentation: the UI of the app, based on MVVM pattern (Model(LiveData) / View(Fragment) / ViewModel)

## Explain your choice of libraries
[Dagger](https://dagger.dev/) is the dependency injection framework I used to decouples dependencies and implements inversion of control principle
[Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle) allows me to implement MVVM pattern with respect Android components lifecycle
[Apollo](https://github.com/apollographql/apollo-android) allows me to interact with GraphQL API easily
[Navigation](https://developer.android.com/guide/navigation) allows me to define the interaction between the different fragment by defining a graph
[AppAuth](https://github.com/openid/AppAuth-Android) allows me interact with an OAuth 2.0 provider (eg. GitHub) and implement [OpenId Authorization code flow](https://openid.net/specs/openid-connect-core-1_0.html#CodeFlowAuth)
[Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) are used to execute use cases asynchronously
[Shimmer](http://facebook.github.io/shimmer-android/) is used to display shimmer effect when loading data
[MockK](https://mockk.io/) is used to mock objects and verify functions calls when executing tests

## What was the most difficult part of the challenge?
The most difficult part of the challenge was to interact with GraphQL API since I had no experience with this type of API before

## Your testing strategy and how you can improve it
My testing strategy is based on 2 types of tests:
- Unit tests to cover the use case
- Instrumented tests to the MVVM pattern implementation

I can improve it by adding integration testing by using Robolectric.
There is a list of subjects that needs more testing:
- Navigation / UI Actions
- Authentication
- Mapper

## Estimate your percentage of completion and how much time you would need to finish
I think the test case is complete at 80% since some testing are missing (as explained above) and it would be better to store auth token (in DB or Shared Preferences) to retrieve it on new start

## Explain how you'd improve on this application design & UI/UX
I'd improve the design with using styles and adding some animations (shared transition between repositories list and detail, constraint set).
It could be useful to implement infinite scrolling in repositories list by using pagination.
It would be better store auth token (in DB or Shared Preferences) to retrieve it on new start.

## Explain how you'd improve on this application architecture
I'd improve the application architecture by separating the 3 layers of the clean architecture into 3 separated modules:
- a full Kotlin library (JAR) for the domain layer
- an Android library for the data layer
- an Android application for the presentation layer