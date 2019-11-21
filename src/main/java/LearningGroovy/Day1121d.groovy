package LearningGroovy

import groovy.transform.Immutable


/**
 * Created by QingHuan on 2019/11/21 22:56
 * Day112d Day112d1 对比
 */



class Day112d {
    String cardNumber
    int cardLimit
}
def a = new Day112d(cardNumber:'123',cardLimit:2) //指定传参
println(a.cardNumber)
a.cardNumber = 5
println(a.cardNumber)


@Immutable
class Day112d1 {
    String cardNumber
    int cardLimit
}
def a1 = new Day112d1('123',2) //支持按顺序传参
println(a1.cardNumber)
//a1.cardNumber = 5 //报错
println(a1.cardNumber)
