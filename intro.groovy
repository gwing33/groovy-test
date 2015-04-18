#!/usr/bin/env groovy
import groovy.time.*

def formatter = java.text.NumberFormat.numberInstance
def timeStart = new Date()

(0..10000).collect({
  ((double) (it / 1609.344)).round(2)
}).unique().findAll({
  it * 100 % 25 == 0
}).each({
  println formatter.format(it) + " Mi"
})

def timeStop = new Date()
TimeDuration duration = TimeCategory.minus(timeStop, timeStart)
println duration
