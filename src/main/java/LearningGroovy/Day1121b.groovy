package LearningGroovy

import groovy.transform.*
/**
 * Created by QingHuan on 2019/11/21 22:36
 */
@Canonical(excludes = "lastName,age")
class Day112b {
    String firstName
    String lastName
    int age
}

def sara = new Day112b(firstName:"123",lastName:"walker",age:18)
print(sara)
