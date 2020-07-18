# Imitate-Image Generator
## How To Use
### Summary

the URI only support those pattern
- /{size}
- /{size}?{text}
- /{size}.{type}
- /{size}.{type}?{text}
- /{size}/{color}
- /{size}/{color}?{text}
- /{size}/{color}.{type}?{text}
### Imitate-Image {Size}
- /random
- /random.png
- /random.gif
generate random color image of random size
- /200
- /200.png
- /200.gif
generate random color image of 200x200
- /200x300
- /200x300.png
- /200x300.gif
generate random color image of 200x300
- /random(100)
- /random(100).png
- /random(100).gif
generate random color image of width and height less than 100px
/random(100,200)
/random(100,200).png
/random(100,200).gif
generate random color image of width and height between 100px and 200px
/random(100,200,300)
/random(100,200,300).png
/random(100,200,300).gif
generate random color image of width between 100px and 200px and height less than 300px
/random(100,200,300,400)
/random(100,200,300,400).png
/random(100,200,300,400).gif
generate random color image of width between 100px and 200px and height between 300px and 400px
- /abc
- /xxyy.png
- /zzzd.gif
generate random color image of 200x200
- /abc
- /xxyy.png
- /zzzd.gif
generate random color image of 200x200
### Imitate-Image {Color}
- /{size}/red
generate image of explicit color name
- /{size}/f00
- /{size}/ff00
- /{size}/ff00ff
- /{size}/55ff00ff
generate image of RGB/ARGB/RRGGBB/AARRGGBB
### Imitate-Image {Type}
- /{size}.png
- /{size}.gif
- /{size}/{color}.png
- /{size}/{color}.gif
you can always add the suffix on the request URI. only support png and gif.Default is PNG
### Imitate-Image {Text}
- /{size}.png?logo
- /{size}.gif?ad1
- /{size}/{color}.png?ad2
- /{size}/{color}.gif?avatar
you can always add the queryString at the end of request URI.
### Other
- /
show this help page
## Example:
- http:/127.0.0.1:4444/random
- http:/127.0.0.1:4444/random?item1
- http:/127.0.0.1:4444/200
- http:/127.0.0.1:4444/200.png
- http:/127.0.0.1:4444/200x300
- http:/127.0.0.1:4444/200x300/red?logo
- http:/127.0.0.1:4444/random(300)/f00?logo
- http:/127.0.0.1:4444/random(300,400)/ffff00?logo
- http:/127.0.0.1:4444/random(300,400,400)/33ffff00?logo
- http:/127.0.0.1:4444/random(300,400,400,400)/33ffff00.png?logo