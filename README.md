# Source

This repository started as a fork from [ionutbalosin/jvm-performance-benchmarks](https://github.com/ionutbalosin/jvm-performance-benchmarks).
I'm redoing all benchmarks for Scala, but the scripts have almost no change (in particular the R scripts). 

The main branch here diverges from the original repository at commit `b98b081bb8894f5c7a038715800ded99d46ccf6f`.
Any issues from this point forward are mine and not the original authors'.


# Overview

This repository contains various Java Virtual Machine (JVM) benchmarks with a primary focus on top-tier Just-In-Time (JIT) Compilers, such as C2 JIT, Graal JIT, and the Falcon JIT.

All benchmarks are implemented using the [Java Microbenchmark Harness (JMH)](https://github.com/openjdk/jmh) library and the [JMH sbt plugin](https://github.com/sbt/sbt-jmh).


# Authors

## Scala benchmarks

**Gaël Renoux**
- Website: [DataDome](https://datadome.co)
- GitHub: [@gaelrenoux](https://github.com/gaelrenoux-datadome) and [@gaelrenoux-datadome](https://github.com/gaelrenoux-datadome)
- Bluesky: [@gaelrenoux.bsky.social](https://bsky.app/profile/gaelrenoux.bsky.social)
- Mastodon: [@gaelrenoux@piaille.fr](https://piaille.fr/@gaelrenoux)

## Original project and scripts

**Ionut Balosin**
- Website: [www.ionutbalosin.com](https://www.ionutbalosin.com)
- X: [@ionutbalosin](https://twitter.com/ionutbalosin)
- GitHub: [@ionutbalosin](https://github.com/ionutbalosin)
- Mastodon: [@ionutbalosin](https://mastodon.social/@ionutbalosin)

**Florin Blanaru**
- X: [@gigiblender](https://twitter.com/gigiblender)
- GitHub: [@gigiblender](https://github.com/gigiblender)
- Mastodon: [@gigiblender](https://mastodon.online/@gigiblender)


# How to run

You'll need a bunch of JVM installed (you can use SDKMAN for this, except for Azul Prime which must be installed separately).
Fill in the correct JVM addresses in settings/config.properties.

You will also need Scala and sbt installations, which you can also get through SDKMAN.

In order to plot the graphs, you'll need to install R. On Ubuntu:
```
sudo apt install r-recommended
sudo apt install r-base
# Then a bunch of libraries required by the R libraries:
sudo apt install libfontconfig1-dev libharfbuzz-dev libfribidi-dev
```

Finally, you have to install the R libraries:
```
chown $(whoami) /usr/lib/R/library # To avoid permission issues when installing R packages
sudo R
install.packages("ggplot2")
install.packages("svglite")
quit()
```
