package LearningGroovy

/**
 * Created by QingHuan on 2019/11/20 23:44
 */
class Day112 {
    def real,imaginary
    def  plus(other){
        new Day112(real:real + other.real,imaginary:imaginary+other.imaginary)
    }
    String toString(){
        "$real ${imaginary>0?"+":""} ${imaginary}i"
    }

}
c1 = new Day112(real:1,imaginary:2)
c2 = new Day112(real:5,imaginary:8)

println(c1+c2)
//等价于
//plus返回一个新对象toString取到新对象的 real,imaginary
println(c1.plus(c2).toString())



