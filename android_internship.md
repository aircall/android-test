aircall.io - Android Internship technical test
This test is part of our hiring process at Aircall for Android Intern positions.

#Summary
The goal of this test is to make you code a small Android app from scratch. 
You are free to use the libraries you need and the architecture you find the most appropriate.

This application will use this Marvel Open API (https://developer.marvel.com/documentation/getting_started).

#Steps
Your app must implement the following scenario:

1. Get an API Token from the website
2. Display a list of characters. Each item must display the name, the description and the picture of the character
3. When you click on a character name, a detail page will be displayed. It will contains the information that was already displayed
on the list and also a list of the comics where this character appear.
4. Bonus: 
	a. Searching for a characters with his name.
	b. Infinite scroll.

#Tips
When you get your API Key, you need to hash it to be able to use it. Here an example in Kotlin: 

```kotlin
val timestamp: String = System.currentTimeMillis().toString()
val stringToHash = timestamp + privateKey + publicKey
return marvelWebService.getCharactersList(
            publickey,
            timestamp,
            stringToHash.md5()
        )

fun (String).md5(): String {
    val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
    return bytes.toHex()
}

fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }
```

Don't hesitate to ask, if you have questions.

#What we need from you
1. A link to a github/gitlab or another git repo that host your code base. (The code most be compatible with latest Android studio versions and most be in Kotlin)
2. A document that explain briefly your architecture, libraries choice and what was the most challenging part.
3. A document that explain how you'd improve this application architecture, design &UI/UX and what you wanted to do if you had more time

#Submission
At the end, send an email to android@aircall.io that contains all the required information.