package LearningGroovy

/**
 * Created by QingHuan on 2019/11/21 23:05
 */
class Day112e {

    def size = 10
    Day112e(){
        println("creating this with ${size}")
    }
}

class AsNeed{
    def value
    @Lazy
    Day112e day112e = new Day112e()
    @Lazy
    Day112e day112e1 = {new Day112e(size: value)}()
    AsNeed(){
        println("created asneed")
    }
}

def asNeed = new AsNeed(value:1000)
println(asNeed.day112e.size)
println(asNeed.day112e.size)
println(asNeed.day112e1.size)
