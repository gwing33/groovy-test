#!/usr/bin/env groovy
@Grab( 'com.bloidonia:groovy-stream:0.8.1' )
import groovy.stream.Stream
import groovy.transform.CompileStatic
import java.text.NumberFormat

class LTFDemo {

  NumberFormat formatter = NumberFormat.numberInstance

  Closure convertToMiles = { ((double)it / (double)1609.344).round(2) }
  Closure isQuarterMile = { ((int)it * (int)100 % (int)25) == 0 }
  Closure formatDistance = { println formatter.format(it) + " Mi" }

  @CompileStatic
  List nonStreaming() {
    (0..10000).collect( convertToMiles )
              .unique()
              .findAll( isQuarterMile )
              .collect( formatDistance )
  }

  @CompileStatic
  List streaming() {
    Stream.from(0..10000)
          .map( convertToMiles )
          .unique()
          .findAll( isQuarterMile )
          .collect( formatDistance )
  }
}

def d = new LTFDemo()
bench(d, "nonStreaming", "Non-Streaming")
bench(d, "streaming", "Streaming")

def bench(clazz, method, name){
	def a=  System.currentTimeMillis()
	clazz."$method"()
	def a2=  System.currentTimeMillis()
	println "$name took " + (a2-a) + " ms"
}
