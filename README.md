# [aircall.io](https://aircall.io) - Android technical test

## Architecture

I have chosen to use MVP architecture, with some concepts of clean architecture. There is 5 models representing a call:
- one for the network
- one for the cache
- one for the domain, the repository handle network and cache models, and return the domain model
- one for the feed presentation
- one for the details presentation
It's maybe a bit too much for a small project like that, but I wanted to separate concerns, as if it was a bigger project.
Each feature is splitted in 3 packages : data (network + cache + repository), domain (domain models, can also contain
use cases when necessary) and presentation (views & presenters)

The most important here is to separate the logic from the view, and split this logic into several layers. Domain layer shouldn't depend on the view,
 neither on the data. If I choose to remove the cache or to fetch my data from Firebase from example, my presenters shouldn't change.
 Everything can be unit tested in this way.

## Choice of libraries

### Retrofit

Used for network layer. This is the most popular and maintained library for this.

### Dagger

I made the choice of Dagger for dependency injection because it's the one I know the most.
Koin and Kodein would have been other candidates.

### RxJava

I like to work with the observer pattern. I have hesitated between RxJava and LiveData.
Even if I have already worked a bit with LiveData, I'm more used to RxJava so it made sense to choose it for this test.

### Room

Room is a library from architecture components which ease the work with SQLite. It's for me the obvious choice to work
with a cache.

## What was the most difficult part of the challenge?

The most difficult part was for me to ensure that all the logic is separated from the view in a clean way, and that each
layer is separated (UI, presenter, repositories, data sources) so that it can be easily and completely unit tested.

## Estimate your percentage of completion
I would say 85%. I would have focus more on UI and on dependency injection.

For now, every dependencies are provided in one component and they are all singletons. On a bigger project and with
more time, I would have done subcomponents in order to scope them.

Also, I didn't had the time to handle configuration changes like rotation. Everything is still displayed if you rotate the
screen, but if you combine rotation + archive a call, the app will crash because callList is not saved in FeedActivity.